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
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.data.model.Course
import br.ifsp.edu.dmo1.noodle.databinding.ActivityHomeBinding
import br.ifsp.edu.dmo1.noodle.databinding.FragmentCoursesBinding
import br.ifsp.edu.dmo1.noodle.ui.adapter.CourseAdapter
import br.ifsp.edu.dmo1.noodle.ui.listeners.CourseItemListener
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.CoursesViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.HomeViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.MainViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.CoursesViewModelFactory
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.HomeViewModelFactory
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.MainViewModelFactory
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper

class CoursesFragment : Fragment(), CourseItemListener {
    private var _binding: FragmentCoursesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CoursesViewModel
    private lateinit var courseAdapter: CourseAdapter

    private lateinit var course_detail : Course

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

        binding.btnBackFromDetails.setOnClickListener {
            binding.courseListLayout.visibility = View.VISIBLE
            binding.courseDetailsLayout.visibility = View.GONE
        }

        binding.btnCreateCourse.setOnClickListener {
            binding.courseListLayout.visibility = View.VISIBLE
            binding.courseAddLayout.visibility = View.GONE

            viewModel.addCourse(binding.etCourseName.text.toString().trim(), binding.etCourseDescription.text.toString().trim())
        }
        binding.courseNavMenu.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_lesson -> replaceFragment(CourseLessonFragment(course_detail))
                R.id.nav_course_works -> replaceFragment(CourseWorksFragment(course_detail))
                R.id.nav_users -> replaceFragment(CourseUserFragment(course_detail))
                else -> {

                }
            }
            true
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

    private fun replaceFragment(fragment : Fragment) {
        childFragmentManager.beginTransaction()
            .replace(R.id.fragment_course_container, fragment)
            .commit()
    }

    override fun onCourseClick(course: Course) {
        binding.courseListLayout.visibility = View.GONE
        binding.courseDetailsLayout.visibility = View.VISIBLE

        course_detail = course;

        replaceFragment(CourseLessonFragment(course_detail))

        binding.tvCourseName.text = course_detail.name;
        binding.tvDescription.text = course_detail.description
    }
}