package br.ifsp.edu.dmo1.noodle.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.ifsp.edu.dmo1.noodle.data.model.Work
import br.ifsp.edu.dmo1.noodle.util.PreferencesHelper

class HomeViewModel(application : Application, private val preferencesHelper : PreferencesHelper) : AndroidViewModel(application) {
    private val _selected_work = MutableLiveData<Work>()
    val selected_work : LiveData<Work> = _selected_work

    fun selectWork(work : Work?) {
        _selected_work.value = work;
    }
}