package br.ifsp.edu.dmo1.noodle.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.ifsp.edu.dmo1.noodle.data.model.Course
import br.ifsp.edu.dmo1.noodle.data.model.Lesson
import br.ifsp.edu.dmo1.noodle.data.model.Work
import br.ifsp.edu.dmo1.noodle.data.repository.LessonRepository
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper
import kotlinx.coroutines.launch
import java.time.LocalDate

class CourseLessonViewModel(application : Application, private val preferencesHelper : PreferencesHelper, private val course : Course) : AndroidViewModel(application) {
    private val lesson_repository = LessonRepository(application);

    private val _lessons = MutableLiveData<List<Lesson>>()
    val lessons : LiveData<List<Lesson>> = _lessons

    private val _inserted = MutableLiveData<Boolean>()
    val inserted : LiveData<Boolean> = _inserted

    init {
        _inserted.value = false;
        checkDatabase()
    }

    fun checkDatabase() {
        viewModelScope.launch {
            val lesson_list = lesson_repository.getByCourse(course.courseId)

            _lessons.postValue(lesson_list)
        }
    }

    fun addLesson(name : String, description : String, date : LocalDate = LocalDate.now()) {
        viewModelScope.launch {
            val lesson = Lesson.createNewLesson(name, description, course.courseId, date)

            _inserted.value = lesson_repository.insert(lesson)

            if (_inserted.value == true) {
                checkDatabase()
            }
        }
    }
}