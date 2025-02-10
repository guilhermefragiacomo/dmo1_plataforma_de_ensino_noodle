package br.ifsp.edu.dmo1.noodle.ui.viewmodel

import android.app.Application
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.ifsp.edu.dmo1.noodle.R
import br.ifsp.edu.dmo1.noodle.data.model.Course
import br.ifsp.edu.dmo1.noodle.data.model.CourseUser
import br.ifsp.edu.dmo1.noodle.data.model.Lesson
import br.ifsp.edu.dmo1.noodle.data.model.Work
import br.ifsp.edu.dmo1.noodle.data.repository.CourseRepository
import br.ifsp.edu.dmo1.noodle.data.repository.CourseUserRepository
import br.ifsp.edu.dmo1.noodle.data.repository.SessionRepository
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper
import kotlinx.coroutines.launch

class CoursesViewModel(application : Application, private val preferencesHelper : PreferencesHelper) : AndroidViewModel(application) {
    private val course_repository = CourseRepository(application);
    private val course_user_repository = CourseUserRepository(application)
    private val session_repository = SessionRepository(application)

    private val _courses = MutableLiveData<List<Course>>()
    val courses : LiveData<List<Course>> = _courses

    private val _saved = MutableLiveData<Boolean>()
    val saved : LiveData<Boolean> = _saved

    private val _selected_lesson = MutableLiveData<Lesson?>()
    val selected_lesson : LiveData<Lesson?> = _selected_lesson

    init {
        checkDatabase()
        _saved.value = false;
    }

    fun checkDatabase(){
        viewModelScope.launch {
            val sessionId = preferencesHelper.getSessionId()

            if (sessionId != null) {
                val session = session_repository.findById(sessionId)

                if (session != null) {

                    val course_user_list = course_user_repository.findByUser(session.userRecord)

                    val course_list : MutableList<Course> = ArrayList()
                    for (i in course_user_list) {
                        course_list.add(course_repository.findById(i.courseId))
                    }
                    _courses.postValue(course_list)
                }
            }
        }
    }

    fun addCourse(name : String, description : String) {
        viewModelScope.launch {
            val course = Course.createNewCourse(name = name, description = description)

            if (course_repository.insert(course)) {
                val sessionId = preferencesHelper.getSessionId()

                if (sessionId != null) {
                    val session = session_repository.findById(sessionId)

                    if (session != null) {
                        val course_user = CourseUser(session.userRecord, course.courseId, "Professor")
                        _saved.value = course_user_repository.insert(course_user)

                        if (_saved.value == false) {
                            course_repository.remove(course)
                            Toast.makeText(getApplication<Application>(), getApplication<Application>().getString(R.string.error_create_course), Toast.LENGTH_SHORT).show()
                        }

                        checkDatabase()
                    } else {
                        course_repository.remove(course)
                        Toast.makeText(getApplication<Application>(), getApplication<Application>().getString(R.string.error_create_course), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    course_repository.remove(course)
                    Toast.makeText(getApplication<Application>(), getApplication<Application>().getString(R.string.error_create_course), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun selectLesson(lesson : Lesson?) {
        _selected_lesson.value = lesson;
    }
}