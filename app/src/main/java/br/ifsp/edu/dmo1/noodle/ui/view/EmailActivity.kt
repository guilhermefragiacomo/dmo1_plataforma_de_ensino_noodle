package br.ifsp.edu.dmo1.noodle.ui.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import br.ifsp.edu.dmo1.noodle.databinding.ActivityEmailBinding
import br.ifsp.edu.dmo1.noodle.databinding.ActivitySigninBinding
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.EmailViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.SignInViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.EmaillViewModelFactory
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.SignInViewModelFactory

class EmailActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEmailBinding
    private lateinit var viewModel: EmailViewModel;
    private lateinit var email_str : String;
    private lateinit var record_str : String;
    private lateinit var user_name_str : String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = EmaillViewModelFactory(application)
        viewModel = ViewModelProvider(this, factory).get(EmailViewModel::class.java)

        if (intent.hasExtra("email")) {
            email_str = intent.getStringExtra("email").toString()
        }
        if (intent.hasExtra("user_record")) {
            record_str = intent.getStringExtra("user_record").toString()
        }
        if (intent.hasExtra("user_name")) {
            record_str = intent.getStringExtra("user_name").toString()
        }

        sendEmail()

        setupListeners()
        setupObservers()
    }

    fun setupListeners() {
        binding.btnVerify.setOnClickListener {
            var code = binding.etCode.text.toString().trim()

            viewModel.verifyCode(code);
        }
        binding.btnBackToMain.setOnClickListener {
            val mIntent = Intent(this, UserCreatedActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            if (record_str != null) {
                mIntent.putExtra("user_record", record_str)
            }
            startActivity(mIntent)
        }
    }
    fun setupObservers() {
        viewModel.verified.observe(this, Observer {
            if (it == true) {
                Toast.makeText(this, "Email verificado", Toast.LENGTH_SHORT).show()

                val mIntent = Intent(this, UserCreatedActivity::class.java).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                }
                if (record_str != null) {
                    mIntent.putExtra("user_record", record_str)
                }
                if (user_name_str != null) {
                    mIntent.putExtra("user_record", user_name_str)
                }
                startActivity(mIntent)
            } else {
                Toast.makeText(this, "Não foi possível verificar seu email", Toast.LENGTH_SHORT).show()
            }
        })
    }

    protected fun sendEmail() {
        var emailIntnte = viewModel.getEmailIntent(email_str);
        startActivity(emailIntnte)
    }
}