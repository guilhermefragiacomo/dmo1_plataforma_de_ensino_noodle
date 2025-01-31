package br.ifsp.edu.dmo1.noodle.ui.viewmodel

import android.app.Activity
import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.data.model.User
import br.ifsp.edu.dmo1.noodle.data.repository.SessionRepository
import br.ifsp.edu.dmo1.noodle.data.repository.UserRepository
import br.ifsp.edu.dmo1.noodle.ui.view.MainActivity
import br.ifsp.edu.dmo1.noodle.util.sha256
import kotlinx.coroutines.launch

class MainViewModel(application : Application) : AndroidViewModel(application) {
    private val user_repository = UserRepository(application);
    private val session_repository = SessionRepository(application)

    private val _logd = MutableLiveData<Boolean>()
    val logd : LiveData<Boolean> = _logd

    init {
        _logd.value = false
    }

    fun logUser(record : String, password : String) {
        if (record.isNotEmpty() && password.isNotEmpty()) {
            viewModelScope.launch {
                val user = user_repository.findByRecord(record)

                if (user != null) {
                    if (password.sha256() == user.pass) {
                        _logd.value = true
                    }  else {
                        Toast.makeText(getApplication<Application>(), getApplication<Application>().resources.getString(R.string.wrong_pass), Toast.LENGTH_SHORT).show() // não consegui enviar os erros de dentro da viewModel.launch para a activity, então coloquei os toasts aqui mesmo
                    }
                } else {
                    Toast.makeText(getApplication<Application>(), getApplication<Application>().resources.getString(R.string.error_log), Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            Toast.makeText(getApplication<Application>(), getApplication<Application>().resources.getString(R.string.fields_input), Toast.LENGTH_SHORT).show()
        }
    }
}