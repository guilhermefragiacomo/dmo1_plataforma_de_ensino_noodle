package br.ifsp.edu.dmo1.noodle.ui.view.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.ifsp.edu.dmo1.noodle.databinding.FragmentCoursesBinding
import br.ifsp.edu.dmo1.noodle.ui.adapter.CourseAdapter
import br.ifsp.edu.dmo1.noodle.ui.listeners.CourseItemListener
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.CoursesViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.MainViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.CoursesViewModelFactory
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.MainViewModelFactory
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper

class CoursesFragment : Fragment(), CourseItemListener {
    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CoursesViewModel
    private lateinit var courseAdapter: CourseAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentCoursesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        courseAdapter = CourseAdapter(this)
        binding.recyclerViewCourses.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = courseAdapter
        }

        val preferencesHelper = PreferencesHelper(requireContext())
        val factory = CoursesViewModelFactory(requireActivity().application, preferencesHelper)
        viewModel = ViewModelProvider(this, factory).get(CoursesViewModel::class.java)

        setupObservers()
        setupListeners()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupListeners() {
        binding.ibAddCourse.setOnClickListener {
            binding.courseListLayout.visibility = View.GONE
            binding.courseAddLayout.visibility = View.VISIBLE
        }

        binding.btnBackFromAdd.setOnClickListener {
            binding.courseListLayout.visibility = View.VISIBLE
            binding.courseAddLayout.visibility = View.GONE
        }

        binding.btnCreateCourse.setOnClickListener {
            binding.courseListLayout.visibility = View.VISIBLE
            binding.courseAddLayout.visibility = View.GONE

            viewModel.addCourse(binding.etCourseName.text.toString().trim(), binding.etCourseDescription.text.toString().trim())
        }
    }

    fun setupObservers() {
        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            courses?.let {
                courseAdapter.submitDataset(it)
            }
        }
        viewModel.saved.observe(viewLifecycleOwner, Observer {
            if (viewModel.saved.value == true) {
                binding.courseListLayout.visibility = View.VISIBLE
                binding.courseAddLayout.visibility = View.GONE
            }
        })
    }
}