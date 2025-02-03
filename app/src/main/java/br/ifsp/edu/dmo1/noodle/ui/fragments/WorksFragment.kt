package br.ifsp.edu.dmo1.noodle.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.databinding.FragmentWorksBinding

class WorksFragment : Fragment() {
    private var _binding: FragmentWorksBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_works, container, false)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}