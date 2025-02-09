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
import br.ifsp.edu.dmo1.noodle.databinding.FragmentCourseUsersBinding
import br.ifsp.edu.dmo1.noodle.ui.adapter.AddUserAdapter
import br.ifsp.edu.dmo1.noodle.ui.adapter.UserAdapter
import br.ifsp.edu.dmo1.noodle.ui.listeners.CourseUserItemListener
import br.ifsp.edu.dmo1.noodle.ui.listeners.UserItemListener
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.CourseUserViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.CourseUserViewModelFactory
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper

class CourseUserFragment(private val course : Course) : Fragment(), UserItemListener, CourseUserItemListener {
    private var _binding: FragmentCourseUsersBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: CourseUserViewModel
    private lateinit var userAdapter: AddUserAdapter
    private lateinit var courseUserAdapter : UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding =  FragmentCourseUsersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userAdapter = AddUserAdapter(this)
        binding.recyclerViewCourseUsersDB.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = userAdapter
        }
        courseUserAdapter = UserAdapter(this)
        binding.recyclerViewCourseUsers.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = courseUserAdapter
        }

        val preferencesHelper = PreferencesHelper(requireContext())
        val factory = CourseUserViewModelFactory(requireActivity().application, preferencesHelper, course)
        viewModel = ViewModelProvider(this, factory).get(CourseUserViewModel::class.java)

        setupListeners()
        setupObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupListeners() {
        binding.btnAddUsers.setOnClickListener {
            binding.courseUserListLayout.visibility = View.GONE
            binding.courseUserAddLayout.visibility = View.VISIBLE
        }

        binding.btnBackFromUserAdd.setOnClickListener {
            binding.courseUserListLayout.visibility = View.VISIBLE
            binding.courseUserAddLayout.visibility = View.GONE
        }

        binding.btnAddCourseUser.setOnClickListener {
            val selectUsers = userAdapter.getSelectedUsers()

            Log.d("Noodle_test", selectUsers.toString());

            selectUsers.forEach { user ->
                try {
                    var role = "Student"
                    if (user.professor) {
                        role = "Professor"
                    }
                    viewModel.addCourseUser(user.record, role)
                    binding.courseUserListLayout.visibility = View.VISIBLE
                    binding.courseUserAddLayout.visibility = View.GONE
                } catch (e : Exception) {
                    Toast.makeText(requireContext(), getString(R.string.error_add_user), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun setupObservers() {
        viewModel.users.observe(viewLifecycleOwner) { users ->
            users?.let {
                userAdapter.submitDataset(it)
            }
        }
        viewModel.course_users.observe(viewLifecycleOwner) { course_users ->
            course_users?.let {
                courseUserAdapter.submitDataset(it)
            }
        }
    }
}