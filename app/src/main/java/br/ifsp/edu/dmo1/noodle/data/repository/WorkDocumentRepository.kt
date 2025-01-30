package br.ifsp.edu.dmo1.noodle.data.repository

import android.content.Context
import br.ifsp.edu.dmo1.noodle.data.database.AppDatabase
import br.ifsp.edu.dmo1.noodle.data.model.WorkCommitDocument
import br.ifsp.edu.dmo1.noodle.data.model.WorkDocument

class WorkDocumentRepository(context : Context) {
    private val database = AppDatabase.getInstance(context)
    private val dao = database.getWorkDocumentDao();

    suspend fun insert(workDocument: WorkDocument): Boolean{
        return dao.insertWorkDocument(workDocument) > 0
    }
    suspend fun update(workDocument: WorkDocument): Boolean{
        return dao.updateWorkDocument(workDocument) > 0
    }
    suspend fun remove(workDocument: WorkDocument): Boolean{
        return dao.deleteWorkDocument(workDocument) > 0
    }
    suspend fun findById(id: Long): WorkDocument {
        return dao.getWorkDocument(id)
    }
    suspend fun findAll(): List<WorkDocument>{
        return dao.getAll()
    }
}