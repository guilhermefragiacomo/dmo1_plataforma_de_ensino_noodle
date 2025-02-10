package br.ifsp.edu.dmo1.noodle.ui.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.databinding.ActivitySigninBinding
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.SignInViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.SignInViewModelFactory

class SignInActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySigninBinding
    private lateinit var viewModel: SignInViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySigninBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = SignInViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory).get(SignInViewModel::class.java)

        setupListeners()
        setupObservers()
    }

    fun setupListeners() {
        binding.btnSignIn.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val password = binding.etPassword.text.toString().trim()
            val email = binding.etEmail.text.toString().trim()
            var birth = binding.etBirthDate.text.toString().trim()
            if (birth.length == 8) {
                birth = birth.substring(0, 2) + "/" + birth.substring(2, 4) + "/" + birth.substring(4,)

                try {
                    viewModel.signUser(name, password, email, birth)
                } catch (e : Exception) {
                    Toast.makeText(this, getString(R.string.error_sign), Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, getString(R.string.birth_input), Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setupObservers() {
        viewModel.userRecord.observe(this, Observer {
            if (viewModel.userRecord.value != null) {
                val mIntent = Intent(this, EmailActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                mIntent.putExtra("email", binding.etEmail.text.toString().trim())
                mIntent.putExtra("user_record", viewModel.userRecord.value)
                mIntent.putExtra("user_name", binding.etName.text.toString().trim())
                startActivity(mIntent)
            }
        })
    }
}