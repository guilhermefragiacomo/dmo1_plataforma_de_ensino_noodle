package br.ifsp.edu.dmo1.noodle.data.repository

import android.content.Context
import br.ifsp.edu.dmo1.noodle.data.database.AppDatabase
import br.ifsp.edu.dmo1.noodle.data.model.Course

class CourseRepository(context : Context) {
    private val database = AppDatabase.getInstance(context)
    private val dao = database.getCourseDao();

    suspend fun insert(task: Course): Boolean{
        return dao.insertCourse(task) > 0
    }
    suspend fun update(task: Course): Boolean{
        return dao.updateCourse(task) > 0
    }
    suspend fun remove(task: Course): Boolean{
        return dao.deleteCourse(task) > 0
    }
    suspend fun findById(id: Long): Course{
        return dao.getCourse(id)
    }
    suspend fun findAll(): List<Course>{
        return dao.getAll()
    }
}