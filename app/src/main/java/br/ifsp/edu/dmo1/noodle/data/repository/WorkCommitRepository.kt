package br.ifsp.edu.dmo1.noodle.data.repository

import android.content.Context
import br.ifsp.edu.dmo1.noodle.data.database.AppDatabase
import br.ifsp.edu.dmo1.noodle.data.model.Work
import br.ifsp.edu.dmo1.noodle.data.model.WorkCommit

class WorkCommitRepository(context : Context) {
    private val database = AppDatabase.getInstance(context)
    private val dao = database.getWorkCommitDao();

    suspend fun insert(workCommit: WorkCommit): Boolean{
        return dao.insertWorkCommit(workCommit) > 0
    }
    suspend fun update(workCommit: WorkCommit): Boolean{
        return dao.updateWorkCommit(workCommit) > 0
    }
    suspend fun remove(workCommit: WorkCommit): Boolean{
        return dao.deleteWorkCommit(workCommit) > 0
    }
    suspend fun findById(id: Long): WorkCommit {
        return dao.getWorkCommit(id)
    }
    suspend fun findAll(): List<WorkCommit>{
        return dao.getAll()
    }
}