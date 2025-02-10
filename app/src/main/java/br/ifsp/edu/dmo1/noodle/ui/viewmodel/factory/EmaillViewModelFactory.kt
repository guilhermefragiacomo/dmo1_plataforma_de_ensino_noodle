package br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.EmailViewModel

class EmaillViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmailViewModel(application) as T
        }
        throw IllegalArgumentException()
    }
}