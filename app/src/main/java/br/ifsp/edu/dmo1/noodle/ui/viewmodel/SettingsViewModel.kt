package br.ifsp.edu.dmo1.noodle.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.ifsp.edu.dmo1.noodle.data.repository.SessionRepository
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper
import kotlinx.coroutines.launch

class SettingsViewModel(application : Application, private val preferencesHelper : PreferencesHelper) : AndroidViewModel(application)  {
    private val session_repository = SessionRepository(application);

    private val _loggedOff = MutableLiveData<Boolean>()
    val loggedOff : LiveData<Boolean> = _loggedOff

    fun logoff() {
        viewModelScope.launch {
            val sessionId = preferencesHelper.getSessionId()

            if (sessionId != null) {
                val session = session_repository.findById(sessionId)

                if (session != null) {
                    _loggedOff.value = session_repository.remove(session)
                }
            }
        }
    }
}