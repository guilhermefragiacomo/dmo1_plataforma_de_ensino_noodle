package br.ifsp.edu.dmo1.noodle.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.ifsp.edu.dmo1.noodle.data.model.Work
import br.ifsp.edu.dmo1.noodle.data.repository.CourseRepository
import br.ifsp.edu.dmo1.noodle.data.repository.CourseUserRepository
import br.ifsp.edu.dmo1.noodle.data.repository.SessionRepository
import br.ifsp.edu.dmo1.noodle.data.repository.WorkRepository
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper
import kotlinx.coroutines.launch
import java.time.LocalDate

class WorksViewModel(application : Application, private val preferencesHelper: PreferencesHelper) : AndroidViewModel(application) {
    private val workRepository = WorkRepository(application);
    private val courseUserRepository = CourseUserRepository(application);
    private val sessionRepository = SessionRepository(application)

    private val _works = MutableLiveData<List<Work>>()
    val works : LiveData<List<Work>> = _works

    init {
        setDataset();
    }

    fun setDataset() {
        viewModelScope.launch {
            val sessionId = preferencesHelper.getSessionId()

            if (sessionId != null) {
                val session = sessionRepository.findById(sessionId)

                if (session != null) {
                    val course_user_list = courseUserRepository.findByUser(session.userRecord);
                    val work_list = ArrayList<Work>();

                    for (c in course_user_list) {
                        if (c.courseRole.equals("Student")) {
                            for (w in workRepository.getByCourse(c.courseId)) {
                                if (w.getDeadLineAsLocalDate().isAfter(LocalDate.now())) {
                                    work_list.add(w);
                                }
                            }
                        }
                    }
                    _works.postValue(work_list);
                }
            }
        }
    }
}