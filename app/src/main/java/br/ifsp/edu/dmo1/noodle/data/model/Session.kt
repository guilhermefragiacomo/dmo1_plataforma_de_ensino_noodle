package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(tableName = "tb_session")
class Session (
    @ColumnInfo(name = "session_id")
    var sessionId : String = "",
    @ColumnInfo(name = "user_record")
    var userRecord : String,
    @ColumnInfo(name = "session_started")
    var sessionStarted : String = "",

    sessionLocalDate : LocalDate = LocalDate.now()
    ) {

    @Ignore
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    init {
        sessionStarted = formatter.format(sessionLocalDate)
        sessionId = generateRecord();
    }

    fun generateRecord() : String {
        return "S" + UUID.randomUUID().toString().replace("-", "").take(10)
    }
}