package br.ifsp.edu.dmo1.noodle.ui.view.fragments

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.data.model.Course
import br.ifsp.edu.dmo1.noodle.databinding.FragmentCourseWorksBinding
import br.ifsp.edu.dmo1.noodle.ui.adapter.WorkAdapter
import br.ifsp.edu.dmo1.noodle.ui.listeners.WorkItemListener
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.CourseWorkViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.HomeViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.CourseWorkViewModelFactory
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper
import java.time.LocalDate

class CourseWorksFragment(private val course : Course) : Fragment(), WorkItemListener {
    private var _binding: FragmentCourseWorksBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CourseWorkViewModel
    private lateinit var homeViewModel : HomeViewModel
    private lateinit var workAdapter: WorkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentCourseWorksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workAdapter = WorkAdapter(this)
        binding.recyclerViewCourseWorks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = workAdapter
        }

        binding.btnAddWork.visibility = View.GONE

        val preferencesHelper = PreferencesHelper(requireContext())
        val factory = CourseWorkViewModelFactory(requireActivity().application, preferencesHelper, course)
        viewModel = ViewModelProvider(this, factory).get(CourseWorkViewModel::class.java)
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        setupListeners()
        setupObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupListeners() {
        binding.btnAddWork.setOnClickListener {
            binding.courseWorkListLayout.visibility = View.GONE
            binding.courseWorkAddLayout.visibility = View.VISIBLE
        }

        binding.btnBackFromWorkAdd.setOnClickListener {
            binding.courseWorkListLayout.visibility = View.VISIBLE
            binding.courseWorkAddLayout.visibility = View.GONE
        }

        binding.btnCreateWork.setOnClickListener {
            val name = binding.etWorkName.text.toString().trim()
            val desc = binding.etWorkDescription.text.toString().trim()
            var deadline = binding.etDeadLine.text.toString().trim()
            if (deadline.length == 8) {
                deadline = deadline.substring(4,) + "-" + deadline.substring(2, 4) + "-" + deadline.substring(0, 2)

                try {
                    viewModel.addWork(name, desc, dead_line = LocalDate.parse(deadline))
                    binding.courseWorkListLayout.visibility = View.VISIBLE
                    binding.courseWorkAddLayout.visibility = View.GONE
                } catch (e : Exception) {
                    Toast.makeText(requireContext(), getString(R.string.error_create_work), Toast.LENGTH_SHORT).show()
                }
            }
        }

        binding.btnWorkSendFile.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Escolha um arquivo"), 111)
        }
    }

    fun setupObservers() {
        viewModel.works.observe(viewLifecycleOwner) { works ->
            works?.let {
                workAdapter.submitDataset(it)
            }
        }
        viewModel.allowed.observe(viewLifecycleOwner) { allowed ->
            allowed?.let {
                if (allowed == true) {
                    binding.btnAddWork.visibility = View.VISIBLE
                }
            }
        }
    }
    override fun click(position: Int) {
        val work = workAdapter.getDatasetItem(position)
        homeViewModel.selectWork(work);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val selectedFile = data?.data

            Log.d("Noodle_test", selectedFile.toString())

            viewModel.saveDocument(selectedFile.toString())
        }
    }
}