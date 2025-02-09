package br.ifsp.edu.dmo1.noodle.data.repository

import android.content.Context
import br.ifsp.edu.dmo1.noodle.data.database.AppDatabase
import br.ifsp.edu.dmo1.noodle.data.model.User
import br.ifsp.edu.dmo1.noodle.data.model.Work

class WorkRepository(context : Context) {
    private val database = AppDatabase.getInstance(context)
    private val dao = database.getWorkDao();

    suspend fun insert(work: Work): Boolean{
        return dao.insertWork(work) > 0
    }
    suspend fun update(work: Work): Boolean{
        return dao.updateWork(work) > 0
    }
    suspend fun remove(work: Work): Boolean{
        return dao.deleteWork(work) > 0
    }
    suspend fun findById(id: Long): Work {
        return dao.getWork(id)
    }
    suspend fun findAll(): List<Work>{
        return dao.getAll()
    }
    suspend fun getByCourse(course_id : String) : List<Work> {
        return dao.getWorksByCourse(course_id)
    }
}