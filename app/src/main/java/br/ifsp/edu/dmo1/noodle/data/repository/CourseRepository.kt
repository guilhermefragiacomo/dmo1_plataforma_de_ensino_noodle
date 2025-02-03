package br.ifsp.edu.dmo1.noodle.data.repository

import android.content.Context
import br.ifsp.edu.dmo1.noodle.data.database.AppDatabase
import br.ifsp.edu.dmo1.noodle.data.model.Course

class CourseRepository(context : Context) {
    private val database = AppDatabase.getInstance(context)
    private val dao = database.getCourseDao();

    suspend fun insert(course: Course): Boolean{
        return dao.insertCourse(course) > 0
    }
    suspend fun update(course: Course): Boolean{
        return dao.updateCourse(course) > 0
    }
    suspend fun remove(course: Course): Boolean{
        return dao.deleteCourse(course) > 0
    }
    suspend fun findById(id: Long): Course{
        return dao.getCourse(id)
    }
    suspend fun findAll(): List<Course>{
        return dao.getAll()
    }
}