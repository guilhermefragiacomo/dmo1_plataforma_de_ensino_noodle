package br.ifsp.edu.dmo1.noodle.ui.view

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.databinding.ActivityEmailBinding
import br.ifsp.edu.dmo1.noodle.databinding.ActivityUserCreatedBinding
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.EmailViewModel
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory.EmaillViewModelFactory

class UserCreatedActivity  : AppCompatActivity() {
    private lateinit var binding : ActivityUserCreatedBinding
    private lateinit var user_record : String;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserCreatedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (intent.hasExtra("user_record")) {
            user_record = intent.getStringExtra("user_record").toString()

            binding.tvUserRecord.text = user_record;
        }
        if (intent.hasExtra("user_name")) {
            var user_name = intent.getStringExtra("user_name").toString();

            binding.tvUserName.text = user_name
        }

        setupListeners()
    }

    fun setupListeners() {
        binding.ibCopyRecord.setOnClickListener {
            val clipboard: ClipboardManager = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText(getString(R.string.user_record), user_record)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(this, getString(R.string.record_copyied), Toast.LENGTH_SHORT).show()
        }

        binding.btnBackToMain.setOnClickListener {
            val mIntent = Intent(this, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(mIntent)
        }
    }
}