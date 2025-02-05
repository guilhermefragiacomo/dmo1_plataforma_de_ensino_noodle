package br.ifsp.edu.dmo1.noodle.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.ifsp.edu.dmo1.noodle.data.model.Course

@Dao
interface CourseDao {
    @Insert
    suspend fun insertCourse(course: Course): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourses(vararg courses: Course): List<Long>

    @Delete
    suspend fun deleteCourse(course: Course): Int

    @Update
    suspend fun updateCourse(course: Course): Int

    @Query("SELECT * FROM tb_course ORDER BY name")
    suspend fun getAll(): List<Course>

    @Query("SELECT * FROM tb_course WHERE course_id = :id LIMIT 1")
    suspend fun getCourse(id: String): Course
}