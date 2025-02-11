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
import br.ifsp.edu.dmo1.noodle.databinding.FragmentCourseLessonBinding
import br.ifsp.edu.dmo1.noodle.ui.adapter.LessonAdapter
import br.ifsp.edu.dmo1.noodle.ui.listeners.LessonItemListener
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.CourseLessonViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.CoursesViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.CourseLessonViewModelFactory
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper
import java.time.LocalDate

class CourseLessonFragment(private val course : Course) : Fragment(), LessonItemListener {
    private var _binding: FragmentCourseLessonBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CourseLessonViewModel
    private lateinit var courseViewModel : CoursesViewModel
    private lateinit var lessonAdapter: LessonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentCourseLessonBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lessonAdapter = LessonAdapter(this)
        binding.recyclerViewCourseLesson.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = lessonAdapter
        }

        binding.btnAddLesson.visibility = View.GONE

        val preferencesHelper = PreferencesHelper(requireContext())
        val factory = CourseLessonViewModelFactory(requireActivity().application, preferencesHelper, course)
        viewModel = ViewModelProvider(this, factory).get(CourseLessonViewModel::class.java)
        courseViewModel = ViewModelProvider(requireParentFragment()).get(CoursesViewModel::class.java)

        setupListeners()
        setupObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupListeners() {
        binding.btnAddLesson.setOnClickListener {
            binding.courseLessonListLayout.visibility = View.GONE
            binding.courseLessonAddLayout.visibility = View.VISIBLE
        }

        binding.btnBackFromLessonAdd.setOnClickListener {
            binding.courseLessonListLayout.visibility = View.VISIBLE
            binding.courseLessonAddLayout.visibility = View.GONE
        }

        binding.btnLessonSendFile.setOnClickListener {
            val intent = Intent()
                .setType("*/*")
                .setAction(Intent.ACTION_GET_CONTENT)

            startActivityForResult(Intent.createChooser(intent, "Escolha um arquivo"), 111)
        }

        binding.btnCreateWork.setOnClickListener {
            val name = binding.etLessonName.text.toString().trim()
            val desc = binding.etLessonDescription.text.toString().trim()
            try {
                viewModel.addLesson(name, desc)
                binding.courseLessonListLayout.visibility = View.VISIBLE
                binding.courseLessonAddLayout.visibility = View.GONE
            } catch (e : Exception) {
                Toast.makeText(requireContext(), getString(R.string.error_create_lesson), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 111 && resultCode == RESULT_OK) {
            val selectedFile = data?.data

            Log.d("Noodle_test", selectedFile.toString())

            viewModel.saveDocument(selectedFile.toString())
        }
    }

    fun setupObservers() {
        viewModel.lessons.observe(viewLifecycleOwner) { lessons ->
            lessons?.let {
                lessonAdapter.submitDataset(it)
            }
        }
        viewModel.allowed.observe(viewLifecycleOwner) { allowed ->
            allowed?.let {
                if (allowed == true) {
                    binding.btnAddLesson.visibility = View.VISIBLE
                }
            }
        }
    }

    override fun click(position: Int) {
        val lesson = lessonAdapter.getDatasetItem(position)
        courseViewModel.selectLesson(lesson);
    }
}