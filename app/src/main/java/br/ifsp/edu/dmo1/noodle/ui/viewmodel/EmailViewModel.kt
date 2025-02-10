package br.ifsp.edu.dmo1.noodle.ui.viewmodel

import android.app.Application
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import java.util.UUID


class EmailViewModel(application : Application) : AndroidViewModel(application) {
    private val code = generateCode()

    private val _verified = MutableLiveData<Boolean>()
    val verified : LiveData<Boolean> = _verified

    private fun generateCode() : String {
        return UUID.randomUUID().toString().replace("-", "").take(5)
    }

    init {
        _verified.value = false;
    }

    fun verifyCode(code_input : String) {
        if (code_input.equals(code)) {
            _verified.value = true;
        } else {
            _verified.value = false;
        }
    }

    fun getEmailIntent(email : String) : Intent {
        val mailIntent = Intent(Intent.ACTION_SEND)
        mailIntent.putExtra(Intent.EXTRA_EMAIL, email)
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, "Noodle Activation Code")
        mailIntent.putExtra(Intent.EXTRA_TEXT, "Please verify your Noodle account by using the following code: " + code + " in the app.")

        mailIntent.type = "message/rfc822"
        return mailIntent
    }
}