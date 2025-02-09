package br.ifsp.edu.dmo1.noodle.ui.view.fragments

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
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.WorksViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.CourseWorkViewModelFactory
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.WorksViewModelFactory
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper

class WorksFragment : Fragment(), WorkItemListener {
    private var _binding: FragmentWorksBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: WorksViewModel
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

        setupObservers()
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
    }
}