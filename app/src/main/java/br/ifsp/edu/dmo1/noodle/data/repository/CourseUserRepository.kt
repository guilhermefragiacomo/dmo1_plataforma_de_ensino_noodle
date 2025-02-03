package br.ifsp.edu.dmo1.noodle.data.repository

import android.content.Context
import br.ifsp.edu.dmo1.noodle.data.database.AppDatabase
import br.ifsp.edu.dmo1.noodle.data.model.CourseUser
import br.ifsp.edu.dmo1.noodle.data.model.Session

class CourseUserRepository(context : Context) {
    private val database = AppDatabase.getInstance(context)
    private val dao = database.getCourseUserDao();

    suspend fun insert(courseUser: CourseUser): Boolean{
        return dao.insertCourseUser(courseUser) > 0
    }
    suspend fun update(courseUser: CourseUser): Boolean{
        return dao.updateCourseUser(courseUser) > 0
    }
    suspend fun remove(courseUser: CourseUser): Boolean{
        return dao.deleteCourseUser(courseUser) > 0
    }
    suspend fun findByCourseAndUser(id: String, record : String): CourseUser {
        return dao.getByCourseAndUser(id, record)
    }
    suspend fun findByUser(record : String) : List<CourseUser> {
        return dao.getByUser(record)
    }
    suspend fun findAll(): List<CourseUser>{
        return dao.getAll()
    }
}