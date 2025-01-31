package br.ifsp.edu.dmo1.noodle.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.ifsp.edu.dmo1.noodle.data.model.CourseUser
import br.ifsp.edu.dmo1.noodle.data.model.Session

@Dao
interface CourseUserDao {
    @Insert
    suspend fun insertCourseUser(courseUser: CourseUser): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertCourseUsers(vararg courseUser: CourseUser): List<Long>

    @Delete
    suspend fun deleteCourseUser(courseUser: CourseUser): Int

    @Update
    suspend fun updateCourseUser(courseUser: CourseUser): Int

    @Query("SELECT * FROM tb_course_user ORDER BY course_id")
    suspend fun getAll(): List<CourseUser>

    @Query("SELECT * FROM tb_course_user WHERE user_record = :record AND course_id = :id LIMIT 1")
    suspend fun getByCourseAndUser(id : Long, record: String): CourseUser
}