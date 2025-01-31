package br.ifsp.edu.dmo1.noodle.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.ifsp.edu.dmo1.noodle.data.model.Lesson

@Dao
interface LessonDao {
    @Insert
    suspend fun insertLesson(lesson: Lesson): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertLessons(vararg lesson: Lesson): List<Long>

    @Delete
    suspend fun deleteLesson(lesson: Lesson): Int

    @Update
    suspend fun updateLesson(lesson: Lesson): Int

    @Query("SELECT * FROM tb_lesson ORDER BY name")
    suspend fun getAll(): List<Lesson>

    @Query("SELECT * FROM tb_lesson WHERE lesson_id = :id LIMIT 1")
    suspend fun getLesson(id: Long): Lesson
}