package br.ifsp.edu.dmo1.noodle.data.repository

import android.content.Context
import br.ifsp.edu.dmo1.noodle.data.database.AppDatabase
import br.ifsp.edu.dmo1.noodle.data.model.WorkCommit
import br.ifsp.edu.dmo1.noodle.data.model.WorkCommitDocument

class WorkCommitDocumentRepository(context : Context) {
    private val database = AppDatabase.getInstance(context)
    private val dao = database.getWorkCommitDocumentDao();

    suspend fun insert(workCommitDocument: WorkCommitDocument): Boolean{
        return dao.insertWorkCommitDocument(workCommitDocument) > 0
    }
    suspend fun update(workCommitDocument: WorkCommitDocument): Boolean{
        return dao.updateWorkCommitDocument(workCommitDocument) > 0
    }
    suspend fun remove(workCommitDocument: WorkCommitDocument): Boolean{
        return dao.deleteWorkCommitDocument(workCommitDocument) > 0
    }
    suspend fun findById(id: Long): WorkCommitDocument {
        return dao.getWorkCommitDocument(id)
    }
    suspend fun findAll(): List<WorkCommitDocument>{
        return dao.getAll()
    }
}