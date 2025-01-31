package br.ifsp.edu.dmo1.noodle.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.data.repository.UserRepository
import br.ifsp.edu.dmo1.noodle.databinding.ActivityMainBinding
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.MainViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.MainViewModelFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        setupListeners()
        setupObservers()
    }

    fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val record = binding.etSchoolRecord.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()

            try {
                viewModel.logUser(record, password)
            } catch (e : Exception) {
                Toast.makeText(this, getString(R.string.error_log), Toast.LENGTH_SHORT).show()
            }
        }
        binding.tvSignIn.setOnClickListener {
            val mIntent = Intent(this, SignInActivity::class.java)
            startActivity(mIntent)
        }
    }

    fun setupObservers() {
        viewModel.logd.observe(this, Observer {

        })
    }
}