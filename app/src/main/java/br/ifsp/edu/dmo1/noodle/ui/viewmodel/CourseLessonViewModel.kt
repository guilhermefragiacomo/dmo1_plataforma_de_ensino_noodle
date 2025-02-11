package br.ifsp.edu.dmo1.noodle.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.ifsp.edu.dmo1.noodle.data.model.Course
import br.ifsp.edu.dmo1.noodle.data.model.Lesson
import br.ifsp.edu.dmo1.noodle.data.model.LessonDocument
import br.ifsp.edu.dmo1.noodle.data.model.Work
import br.ifsp.edu.dmo1.noodle.data.repository.CourseUserRepository
import br.ifsp.edu.dmo1.noodle.data.repository.LessonDocumentRepository
import br.ifsp.edu.dmo1.noodle.data.repository.LessonRepository
import br.ifsp.edu.dmo1.noodle.data.repository.SessionRepository
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper
import kotlinx.coroutines.launch
import java.time.LocalDate

class CourseLessonViewModel(application : Application, private val preferencesHelper : PreferencesHelper, private val course : Course) : AndroidViewModel(application) {
    private val lesson_repository = LessonRepository(application);
    private val lesson_document_repository = LessonDocumentRepository(application)
    private val course_user_repository = CourseUserRepository(application)
    private val session_repository = SessionRepository(application)

    private val _lessons = MutableLiveData<List<Lesson>>()
    val lessons : LiveData<List<Lesson>> = _lessons

    private val _inserted = MutableLiveData<Boolean>()
    val inserted : LiveData<Boolean> = _inserted

    private val _allowed = MutableLiveData<Boolean>()
    val allowed : LiveData<Boolean> = _allowed

    private val _file = MutableLiveData<String>()
    val file : LiveData<String> = _file;

    init {
        _inserted.value = false;
        _allowed.value = false;
        checkDatabase()
        check_allowed()
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

                var ld =
                    _file.value?.let { LessonDocument(lessonId = lesson.lessonId, document = it, name = it.split("/").last()) };
                if (ld != null) {
                    lesson_document_repository.insert(ld)
                }
                checkDatabase()
            }
        }
    }

    fun check_allowed() {
        viewModelScope.launch {
            val sessionId = preferencesHelper.getSessionId()

            if (sessionId != null) {
                val session = session_repository.findById(sessionId)

                if (session != null) {
                    var course_user = course_user_repository.findByCourseAndUser(course.courseId, session.userRecord)

                    if (course_user != null) {
                        if (course_user.courseRole == "Professor") {
                            _allowed.value = true;
                        }
                    }
                }
            }
        }
    }

    fun saveDocument(file : String) {
        _file.value = file;
    }
}