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
import androidx.lifecycle.viewModelScope
import br.ifsp.edu.dmo1.noodle.data.model.User
import br.ifsp.edu.dmo1.noodle.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.util.UUID


class EmailViewModel(application : Application) : AndroidViewModel(application) {
    private val user_repository = UserRepository(application)

    private val code = generateCode()

    private val _verified = MutableLiveData<Boolean>()
    val verified : LiveData<Boolean> = _verified

    private fun generateCode() : String {
        return UUID.randomUUID().toString().replace("-", "").take(5)
    }

    init {
        _verified.value = false;
    }

    fun verifyCode(code_input : String, user_record : String) {
        viewModelScope.launch {
            if (code_input.equals(code)) {
                var user = user_repository.findByRecord(user_record)

                if (user != null) {
                    var newUser = User(user.record, user.name, user.birth, user.email, user.pass, true)

                    Log.d("Noodle_test", user.toString())
                    Log.d("Noodle_test", newUser.toString())
                    if (user_repository.update(newUser)) {
                        Log.d("Noodle_test", "adwad")
                        _verified.value = true;
                    }
                }
            }
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