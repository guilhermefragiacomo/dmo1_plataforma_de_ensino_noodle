package br.ifsp.edu.dmo1.noodle.ui.view.fragments

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
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.CourseWorkViewModelFactory
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper
import java.time.LocalDate

class CourseWorksFragment(private val course : Course) : Fragment(), WorkItemListener {
    private var _binding: FragmentCourseWorksBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CourseWorkViewModel
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

        val preferencesHelper = PreferencesHelper(requireContext())
        val factory = CourseWorkViewModelFactory(requireActivity().application, preferencesHelper, course)
        viewModel = ViewModelProvider(this, factory).get(CourseWorkViewModel::class.java)

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

                Log.d("Noodle", deadline)

                try {
                    viewModel.addWork(name, desc, dead_line = LocalDate.parse(deadline))
                    binding.courseWorkListLayout.visibility = View.VISIBLE
                    binding.courseWorkAddLayout.visibility = View.GONE
                } catch (e : Exception) {
                    Toast.makeText(requireContext(), getString(R.string.error_create_work), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun setupObservers() {
        viewModel.works.observe(viewLifecycleOwner) { works ->
            works?.let {
                workAdapter.submitDataset(it)
            }
        }
    }
}