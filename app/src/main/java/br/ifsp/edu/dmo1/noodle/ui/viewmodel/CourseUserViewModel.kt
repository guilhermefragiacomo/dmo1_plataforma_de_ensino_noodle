package br.ifsp.edu.dmo1.noodle.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.ifsp.edu.dmo1.noodle.data.model.Course
import br.ifsp.edu.dmo1.noodle.data.model.CourseUser
import br.ifsp.edu.dmo1.noodle.data.model.User
import br.ifsp.edu.dmo1.noodle.data.model.Work
import br.ifsp.edu.dmo1.noodle.data.model.auxiliar.AddUser
import br.ifsp.edu.dmo1.noodle.data.repository.CourseUserRepository
import br.ifsp.edu.dmo1.noodle.data.repository.SessionRepository
import br.ifsp.edu.dmo1.noodle.data.repository.UserRepository
import br.ifsp.edu.dmo1.noodle.data.repository.WorkRepository
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper
import kotlinx.coroutines.launch
import java.time.LocalDate

class CourseUserViewModel(application : Application, private val preferencesHelper : PreferencesHelper, private val course : Course) : AndroidViewModel(application) {
    private val user_repository = UserRepository(application);
    private val course_user_repository = CourseUserRepository(application)
    private val session_repository = SessionRepository(application)

    private val _users = MutableLiveData<List<AddUser>>()
    val users : LiveData<List<AddUser>> = _users

    private val _course_users = MutableLiveData<List<User>>()
    val course_users : LiveData<List<User>> = _course_users

    private val _inserted = MutableLiveData<Boolean>()
    val inserted : LiveData<Boolean> = _inserted

    private val _allowed = MutableLiveData<Boolean>()
    val allowed : LiveData<Boolean> = _allowed

    init {
        _inserted.value = false;
        _allowed.value = false;
        checkDatabase()
        check_allowed()
    }

    fun checkDatabase() {
        viewModelScope.launch {
            val course_users_list = course_user_repository.getByCourse(course.courseId)
            val user_list = ArrayList<User>();

            for (u : CourseUser in course_users_list) {
                var user = user_repository.findByRecord(u.userRecord)
                user_list.add(user)
            }
            _course_users.postValue(user_list)

            val db_user_list = user_repository.findAll();
            val add_user_list = ArrayList<AddUser>();
            db_user_list.forEach { user ->
                val addUser = AddUser(user.record, user.name);
                add_user_list.add(addUser);
            }
            _users.postValue(add_user_list);
        }
    }

    fun addCourseUser(user_record : String, role : String) {
        viewModelScope.launch {
            val course_user = CourseUser(user_record, course.courseId, role);

            Log.d("Noodle_test", course_user.toString());

            for (i in course_user_repository.findAll()) {
                Log.d("Noodle_test1", i.toString());
            }

            _inserted.value = course_user_repository.insert(course_user)

            Log.d("Noodle", _inserted.toString());

            if (_inserted.value == true) {
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
}