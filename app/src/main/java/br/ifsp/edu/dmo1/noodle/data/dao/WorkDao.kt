package br.ifsp.edu.dmo1.noodle.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.ifsp.edu.dmo1.noodle.data.model.Work

@Dao
interface WorkDao {
    @Insert
    suspend fun insertWork(work: Work): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWork(vararg work: Work): Long

    @Delete
    suspend fun deleteWork(work: Work): Int

    @Update
    suspend fun updateWork(work: Work): Int

    @Query("SELECT * FROM tb_work ORDER BY name")
    suspend fun getAll(): List<Work>

    @Query("SELECT * FROM tb_work WHERE work_id = :id LIMIT 1")
    suspend fun getWork(id: Long): Work
}