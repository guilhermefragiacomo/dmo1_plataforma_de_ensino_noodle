package br.ifsp.edu.dmo1.noodle.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.ifsp.edu.dmo1.noodle.data.model.Course
import br.ifsp.edu.dmo1.noodle.data.model.Work
import br.ifsp.edu.dmo1.noodle.data.repository.WorkRepository
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper
import kotlinx.coroutines.launch
import java.time.LocalDate

class CourseWorkViewModel(application : Application, private val preferencesHelper : PreferencesHelper, private val course : Course) : AndroidViewModel(application) {
    private val work_repository = WorkRepository(application);

    private val _works = MutableLiveData<List<Work>>()
    val works : LiveData<List<Work>> = _works

    private val _inserted = MutableLiveData<Boolean>()
    val inserted : LiveData<Boolean> = _inserted

    init {
        _inserted.value = false;
        checkDatabase()
    }

    fun checkDatabase() {
        viewModelScope.launch {
            val work_list = work_repository.getByCourse(course.courseId)

            _works.postValue(work_list)
        }
    }

    fun addWork(name : String, description : String, start_date : LocalDate = LocalDate.now(), dead_line : LocalDate = LocalDate.now().plusDays(1)) {
        viewModelScope.launch {
            val work = Work.createNewWork(name, description, course.courseId, start_date, dead_line)

            _inserted.value = work_repository.insert(work)

            if (_inserted.value == true) {
                checkDatabase()
            }
        }
    }
}