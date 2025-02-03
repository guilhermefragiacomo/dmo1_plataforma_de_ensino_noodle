package br.ifsp.edu.dmo1.noodle.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.ifsp.edu.dmo1.noodle.data.model.Course
import br.ifsp.edu.dmo1.noodle.data.repository.CourseRepository
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper
import kotlinx.coroutines.launch

class CoursesViewModel(application : Application, private val preferencesHelper : PreferencesHelper) : AndroidViewModel(application) {
    private val course_repository = CourseRepository(application);

    private val _courses = MutableLiveData<List<Course>>()
    val courses : LiveData<List<Course>> = _courses

    init {
        checkDatabase()
    }

    fun checkDatabase(){
        viewModelScope.launch {
            val list = course_repository.findAll()
            _courses.postValue(list)
        }
    }


    fun addCourse(name : String, description : String) {
        viewModelScope.launch {
            val course = Course(name = name, description = description)

            if (course_repository.insert(course)) {
                checkDatabase()
            }
        }
    }
}