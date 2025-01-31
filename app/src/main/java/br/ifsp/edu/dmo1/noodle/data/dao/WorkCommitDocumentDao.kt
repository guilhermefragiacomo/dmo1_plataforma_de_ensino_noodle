package br.ifsp.edu.dmo1.noodle.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.ifsp.edu.dmo1.noodle.data.model.WorkCommitDocument

@Dao
interface WorkCommitDocumentDao {
    @Insert
    suspend fun insertWorkCommitDocument(workCommitDocument: WorkCommitDocument): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkCommitDocuments(vararg workCommitDocument: WorkCommitDocument): List<Long>

    @Delete
    suspend fun deleteWorkCommitDocument(workCommitDocument: WorkCommitDocument): Int

    @Update
    suspend fun updateWorkCommitDocument(workCommitDocument: WorkCommitDocument): Int

    @Query("SELECT * FROM tb_work_commit_document ORDER BY work_commit_document_id")
    suspend fun getAll(): List<WorkCommitDocument>

    @Query("SELECT * FROM tb_work_commit_document WHERE work_commit_document_id = :id LIMIT 1")
    suspend fun getWorkCommitDocument(id: Long): WorkCommitDocument
}