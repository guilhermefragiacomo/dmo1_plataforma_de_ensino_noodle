package br.ifsp.edu.dmo1.noodle.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.ifsp.edu.dmo1.noodle.data.model.WorkDocument

@Dao
interface WorkDocumentDao {
    @Insert
    suspend fun insertWorkDocument(workDocument: WorkDocument): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertWorkDocument(vararg workDocument: WorkDocument): Long

    @Delete
    suspend fun deleteWorkDocument(workDocument: WorkDocument): Int

    @Update
    suspend fun updateWorkDocument(workDocument: WorkDocument): Int

    @Query("SELECT * FROM tb_work_document ORDER BY work_document_id")
    suspend fun getAll(): List<WorkDocument>

    @Query("SELECT * FROM tb_work_document WHERE work_document_id = :id LIMIT 1")
    suspend fun getWorkDocument(id: Long): WorkDocument
}