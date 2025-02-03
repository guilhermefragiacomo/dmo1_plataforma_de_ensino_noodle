package br.ifsp.edu.dmo1.noodle.ui.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.data.model.Session
import br.ifsp.edu.dmo1.noodle.data.repository.SessionRepository
import br.ifsp.edu.dmo1.noodle.data.repository.UserRepository
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper
import br.ifsp.edu.dmo1.noodle.util.sha256
import kotlinx.coroutines.launch

class MainViewModel(application : Application, private val preferencesHelper : PreferencesHelper) : AndroidViewModel(application) {
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
                try {
                    val user = user_repository.findByRecord(record)

                    if (user != null) {
                        if (password.sha256() == user.pass) {
                            _logd.value = true
                            saveSession(user.record)
                        }  else {
                            Toast.makeText(getApplication<Application>(), getApplication<Application>().resources.getString(R.string.wrong_pass), Toast.LENGTH_SHORT).show() // não consegui enviar os erros de dentro da viewModel.launch para a activity, então coloquei os toasts aqui mesmo
                        }
                    } else {
                        Toast.makeText(getApplication<Application>(), getApplication<Application>().resources.getString(R.string.error_log), Toast.LENGTH_SHORT).show()
                    }
                } catch (e : Exception) {
                    Toast.makeText(getApplication<Application>(), getApplication<Application>().resources.getString(R.string.error_log), Toast.LENGTH_SHORT).show()
                    Log.d("Noodle", e.toString())
                }
            }
        } else {
            Toast.makeText(getApplication<Application>(), getApplication<Application>().resources.getString(R.string.fields_input), Toast.LENGTH_SHORT).show()
        }
    }

    suspend fun saveSession(userRecord : String) {
        val session = Session(userRecord = userRecord)
        if (session_repository.insert(session)) {
            val sessions = session_repository.findAll()

            preferencesHelper.saveSessionId(session.sessionId)
        }
    }

    fun getSession() {
        viewModelScope.launch {
            try {
                val sessionId = preferencesHelper.getSessionId()

                if (sessionId != null) {
                    val session = session_repository.findById(sessionId)

                    if (session != null) {
                        _logd.value = true
                    }
                }
            } catch (e : Exception) {
                Log.d("Noodle", e.toString())
            }
        }
    }
}