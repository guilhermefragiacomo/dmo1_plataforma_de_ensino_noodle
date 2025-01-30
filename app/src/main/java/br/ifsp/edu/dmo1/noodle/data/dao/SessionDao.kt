package br.ifsp.edu.dmo1.noodle.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import br.ifsp.edu.dmo1.noodle.data.model.Session
import br.ifsp.edu.dmo1.noodle.data.model.User

@Dao
interface SessionDao {
    @Insert
    suspend fun insertSession(session: Session): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertSessions(vararg session: Session): Long

    @Delete
    suspend fun deleteSession(session: Session): Int

    @Update
    suspend fun updateSession(session: Session): Int

    @Query("SELECT * FROM tb_session ORDER BY session_id")
    suspend fun getAll(): List<Session>

    @Query("SELECT * FROM tb_session WHERE session_id = :id LIMIT 1")
    suspend fun getSession(id: String): Session
}