package br.ifsp.edu.dmo1.noodle.ui.view

import android.os.Bundle
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.HomeViewModel
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper
import br.ifsp.edu.dmo1.noodle.databinding.ActivityHomeBinding
import br.ifsp.edu.dmo1.noodle.ui.view.fragments.CoursesFragment
import br.ifsp.edu.dmo1.noodle.ui.view.fragments.SettingsFragment
import br.ifsp.edu.dmo1.noodle.ui.view.fragments.WorksFragment
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.HomeViewModelFactory

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val preferencesHelper = PreferencesHelper(this)
        val factory = HomeViewModelFactory(application, preferencesHelper)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)

        replaceFragment(WorksFragment())

        setupListeners()
    }

    private fun replaceFragment(fragment : Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.fragment_container, fragment)
        fragmentTransaction.commit()
    }

    private fun setupListeners() {
        binding.bottomNavigation.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.nav_works -> replaceFragment(WorksFragment())
                R.id.nav_courses -> replaceFragment(CoursesFragment())
                R.id.nav_settings -> replaceFragment(SettingsFragment())
                else -> {

                }
            }
            true
        }
    }
}