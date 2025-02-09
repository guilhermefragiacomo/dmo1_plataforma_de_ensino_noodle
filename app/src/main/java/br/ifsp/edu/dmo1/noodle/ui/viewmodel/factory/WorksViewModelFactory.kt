package br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.WorksViewModel
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper

class WorksViewModelFactory(private val application: Application, private val preferencesHelper: PreferencesHelper) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(WorksViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return WorksViewModel(application, preferencesHelper) as T
        }
        throw IllegalArgumentException()
    }
}