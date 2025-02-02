package br.ifsp.edu.dmo1.noodle.ui.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.ifsp.edu.dmo1.noodle.databinding.ActivityMainBinding
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.MainViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.WorkHomeViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.MainViewModelFactory
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper
import br.ifsp.edu.dmo1.noodle.databinding.ActivityHomeWorksBinding
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.WorkHomeViewModelFactory

class WorkHomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeWorksBinding
    private lateinit var viewModel: WorkHomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeWorksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferencesHelper = PreferencesHelper(this)
        val factory = WorkHomeViewModelFactory(application, preferencesHelper)
        viewModel = ViewModelProvider(this, factory).get(WorkHomeViewModel::class.java)
    }
}