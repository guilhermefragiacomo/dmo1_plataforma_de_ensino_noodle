package br.ifsp.edu.dmo1.noodle.data.repository

import android.content.Context
import br.ifsp.edu.dmo1.noodle.data.database.AppDatabase
import br.ifsp.edu.dmo1.noodle.data.model.Lesson
import br.ifsp.edu.dmo1.noodle.data.model.LessonDocument
import br.ifsp.edu.dmo1.noodle.data.model.User

class UserRepository(context : Context) {
    private val database = AppDatabase.getInstance(context)
    private val dao = database.getUserDao();

    suspend fun insert(user: User): Boolean{
        return dao.insertUser(user) > 0
    }
    suspend fun update(user: User): Boolean{
        return dao.updateUser(user) > 0
    }
    suspend fun remove(user: User): Boolean{
        return dao.deleteUser(user) > 0
    }
    suspend fun findByRecord(record: String): User {
        return dao.getUser(record)
    }
    suspend fun findAll(): List<User>{
        return dao.getAll()
    }
}