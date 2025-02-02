package br.ifsp.edu.dmo1.noodle.ui.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.data.model.User
import br.ifsp.edu.dmo1.noodle.data.repository.UserRepository
import kotlinx.coroutines.launch
import java.nio.file.FileAlreadyExistsException

class SignInViewModel(application : Application) : AndroidViewModel(application) {
    private val repository = UserRepository(application);

    private val _saved = MutableLiveData<Boolean>()
    val saved : LiveData<Boolean> = _saved



    fun signUser(name : String, password : String, email : String, birth : String) {
        val user = User.createNewUser(name, password, birth , email)

        Log.d("Noodle", user.toString())

        viewModelScope.launch {
            if (repository.findByRecord(user.record) != null) {
                Toast.makeText(getApplication<Application>(), getApplication<Application>().resources.getString(R.string.error_sign) + ", " + getApplication<Application>().resources.getString(R.string.try_again), Toast.LENGTH_SHORT).show() // não consegui enviar os erros de dentro da viewModel.launch para a activity, então coloquei os toasts aqui mesmo
            }
            _saved.value = repository.insert(user)
        }
    }
}