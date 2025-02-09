package br.ifsp.edu.dmo1.noodle.ui.viewmodel.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.ifsp.edu.dmo1.noodle.data.model.Course
import br.ifsp.edu.dmo1.noodle.ui.viewmodel.CourseWorkViewModel
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper

class CourseWorkViewModelFactory(private val application: Application, private val preferencesHelper: PreferencesHelper, private val course : Course) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CourseWorkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CourseWorkViewModel(application, preferencesHelper, course) as T
        }
        throw IllegalArgumentException()
    }
}