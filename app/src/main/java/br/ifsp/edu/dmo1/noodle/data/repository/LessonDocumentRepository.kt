package br.ifsp.edu.dmo1.noodle.data.repository

import android.content.Context
import br.ifsp.edu.dmo1.noodle.data.database.AppDatabase
import br.ifsp.edu.dmo1.noodle.data.model.Lesson
import br.ifsp.edu.dmo1.noodle.data.model.LessonDocument

class LessonDocumentRepository(context : Context) {
    private val database = AppDatabase.getInstance(context)
    private val dao = database.getLessonDocumentDao();

    suspend fun insert(lessonDocument: LessonDocument): Boolean{
        return dao.insertLessonDocument(lessonDocument) > 0
    }
    suspend fun update(lessonDocument: LessonDocument): Boolean{
        return dao.updateLessonDocument(lessonDocument) > 0
    }
    suspend fun remove(lessonDocument: LessonDocument): Boolean{
        return dao.deleteLessonDocument(lessonDocument) > 0
    }
    suspend fun findById(id: Long): LessonDocument {
        return dao.getLessonDocument(id)
    }
    suspend fun findAll(): List<LessonDocument>{
        return dao.getAll()
    }
}