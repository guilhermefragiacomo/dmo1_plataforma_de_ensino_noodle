package br.ifsp.edu.dmo1.noodle.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.ifsp.edu.dmo1.noodle.data.model.WorkCommit

@Dao
interface WorkCommitDao {
    @Insert
    suspend fun insertWorkCommit(workCommit: WorkCommit): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkCommits(vararg workCommit: WorkCommit): List<Long>

    @Delete
    suspend fun deleteWorkCommit(workCommit: WorkCommit): Int

    @Update
    suspend fun updateWorkCommit(workCommit: WorkCommit): Int

    @Query("SELECT * FROM tb_work_commit ORDER BY work_id")
    suspend fun getAll(): List<WorkCommit>

    @Query("SELECT * FROM tb_work_commit WHERE work_commit_id = :id LIMIT 1")
    suspend fun getWorkCommit(id: Long): WorkCommit
}