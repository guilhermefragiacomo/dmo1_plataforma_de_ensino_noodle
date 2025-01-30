package br.ifsp.edu.dmo1.noodle.data.repository

import android.content.Context
import br.ifsp.edu.dmo1.noodle.data.database.AppDatabase
import br.ifsp.edu.dmo1.noodle.data.model.Lesson
import br.ifsp.edu.dmo1.noodle.data.model.Session

class SessionRepository(context : Context) {
    private val database = AppDatabase.getInstance(context)
    private val dao = database.getSessionDao();

    suspend fun insert(session: Session): Boolean{
        return dao.insertSession(session) > 0
    }
    suspend fun update(session: Session): Boolean{
        return dao.updateSession(session) > 0
    }
    suspend fun remove(session: Session): Boolean{
        return dao.deleteSession(session) > 0
    }
    suspend fun findById(id: String): Session {
        return dao.getSession(id)
    }
    suspend fun findAll(): List<Session>{
        return dao.getAll()
    }
}