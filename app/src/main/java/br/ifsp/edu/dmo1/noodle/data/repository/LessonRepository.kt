package br.ifsp.edu.dmo1.noodle.data.repository

import android.content.Context
import br.ifsp.edu.dmo1.noodle.data.database.AppDatabase
import br.ifsp.edu.dmo1.noodle.data.model.Course
import br.ifsp.edu.dmo1.noodle.data.model.Lesson
import br.ifsp.edu.dmo1.noodle.data.model.Work

class LessonRepository(context : Context) {
    private val database = AppDatabase.getInstance(context)
    private val dao = database.getLessonDao();

    suspend fun insert(lesson: Lesson): Boolean{
        return dao.insertLesson(lesson) > 0
    }
    suspend fun update(lesson: Lesson): Boolean{
        return dao.updateLesson(lesson) > 0
    }
    suspend fun remove(lesson: Lesson): Boolean{
        return dao.deleteLesson(lesson) > 0
    }
    suspend fun findById(id: Long): Lesson {
        return dao.getLesson(id)
    }
    suspend fun findAll(): List<Lesson>{
        return dao.getAll()
    }
    suspend fun getByCourse(course_id : String) : List<Lesson> {
        return dao.getWorksByCourse(course_id)
    }
}