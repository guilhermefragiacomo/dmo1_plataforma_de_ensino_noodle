package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(tableName = "tb_session")
class Session (
    @PrimaryKey
    @ColumnInfo(name = "session_id")
    var sessionId : String = "",
    @ColumnInfo(name = "user_record")
    var userRecord : String,
    @ColumnInfo(name = "session_started")
    var sessionStarted : String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    ) {

    @Ignore
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    companion object {
        fun createNewSession(user_record: String, session_started: String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))): Session {
            return Session(
                userRecord = user_record,
                sessionStarted = session_started,
                sessionId = generateSessionId()
            )
        }

        @Ignore
        fun generateSessionId() : String {
            return "S" + UUID.randomUUID().toString().replace("-", "").take(10)
        }
    }

    @Ignore
    fun getDateAsLocalDate(): LocalDate {
        return LocalDate.parse(sessionStarted, formatter)
    }
}