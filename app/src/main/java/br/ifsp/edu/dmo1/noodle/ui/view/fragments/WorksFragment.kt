package br.ifsp.edu.dmo1.noodle.ui.view.fragments

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.databinding.FragmentSettingsBinding
import br.ifsp.edu.dmo1.noodle.databinding.FragmentWorksBinding
import br.ifsp.edu.dmo1.noodle.ui.adapter.WorkAdapter
import br.ifsp.edu.dmo1.noodle.ui.listeners.WorkItemListener
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.CourseWorkViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.HomeViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.WorksViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.CourseWorkViewModelFactory
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.WorksViewModelFactory
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper

class WorksFragment : Fragment(), WorkItemListener {
    private var _binding: FragmentWorksBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WorksViewModel
    private lateinit var homeViewModel : HomeViewModel
    private lateinit var workAdapter: WorkAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWorksBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        workAdapter = WorkAdapter(this)
        binding.recyclerViewTasks.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = workAdapter
        }

        val preferencesHelper = PreferencesHelper(requireContext())
        val factory = WorksViewModelFactory(requireActivity().application, preferencesHelper)
        viewModel = ViewModelProvider(this, factory).get(WorksViewModel::class.java)
        homeViewModel = ViewModelProvider(requireActivity()).get(HomeViewModel::class.java)

        setupObservers()
        setupListeners();
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupObservers() {
        viewModel.works.observe(viewLifecycleOwner) { works ->
            works?.let {
                workAdapter.submitDataset(it)
            }
        }

        homeViewModel.selected_work.observe(viewLifecycleOwner) { work ->
            work?.let {
                if (work != null) {
                    binding.workListLayout.visibility = View.GONE
                    binding.workDetailsLayout.visibility = View.VISIBLE

                    binding.tvWorkName.text = work.name
                    binding.tvWorkDescription.text = work.description
                    binding.tvWorkDeadline.text = work.deadLine
                }
            }
        }
    }

    fun setupListeners() {
        binding.btnWorkSendFile.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Escolha um arquivo"), 111)
        }

        binding.btnBackFromDetails.setOnClickListener {
            binding.workListLayout.visibility = View.VISIBLE
            binding.workDetailsLayout.visibility = View.GONE
            homeViewModel.selectWork(null);
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val selectedFile = data?.data

            Log.d("Noodle_test", selectedFile.toString())

            viewModel.saveDocument(selectedFile.toString());
        }
    }

    override fun click(position: Int) {
        val work = workAdapter.getDatasetItem(position)
        homeViewModel.selectWork(work);
    }
}