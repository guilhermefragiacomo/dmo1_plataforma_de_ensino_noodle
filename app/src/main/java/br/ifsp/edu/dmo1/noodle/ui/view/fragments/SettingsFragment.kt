package br.ifsp.edu.dmo1.noodle.ui.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.databinding.FragmentSettingsBinding
import br.ifsp.edu.dmo1.noodle.ui.view.MainActivity
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.SettingsViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.SettingsViewModelFactory
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper

class SettingsFragment : Fragment() {
    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val preferencesHelper = PreferencesHelper(requireContext())
        val factory = SettingsViewModelFactory(requireActivity().application, preferencesHelper)
        viewModel = ViewModelProvider(this, factory).get(SettingsViewModel::class.java)

        setupListeners()
        setupObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun setupListeners() {
        binding.btnLogoff.setOnClickListener {
            viewModel.logoff()
        }
    }

    fun setupObservers() {
        viewModel.loggedOff.observe(viewLifecycleOwner, Observer {
            if (viewModel.loggedOff.value == true) {
                val mIntent = Intent(requireActivity(), MainActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                startActivity(mIntent)
            }
        })
    }
}