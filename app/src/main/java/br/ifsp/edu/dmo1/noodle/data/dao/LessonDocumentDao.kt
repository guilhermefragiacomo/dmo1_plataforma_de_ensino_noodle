package br.ifsp.edu.dmo1.noodle.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.ifsp.edu.dmo1.noodle.data.model.LessonDocument

@Dao
interface LessonDocumentDao {
    @Insert
    suspend fun insertLessonDocument(lesson: LessonDocument): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLessonsDocuments(vararg lesson: LessonDocument): List<Long>

    @Delete
    suspend fun deleteLessonDocument(lesson: LessonDocument): Int

    @Update
    suspend fun updateLessonDocument(lesson: LessonDocument): Int

    @Query("SELECT * FROM tb_lesson_document ORDER BY name")
    suspend fun getAll(): List<LessonDocument>

    @Query("SELECT * FROM tb_lesson_document WHERE lesson_document_id = :id LIMIT 1")
    suspend fun getLessonDocument(id: Long): LessonDocument
}