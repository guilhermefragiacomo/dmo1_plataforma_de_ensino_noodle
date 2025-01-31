package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@Entity(tableName = "tb_work_commit",
    foreignKeys = [
        ForeignKey (
            entity = Work::class,
            parentColumns = ["work_id"],
            childColumns = ["work_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey (
            entity = User::class,
            parentColumns = ["record"],
            childColumns = ["user_record"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class WorkCommit (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "work_commit_id")
    var workCommitId : Long = 0,
    @ColumnInfo(name = "work_id")
    var workId : Int,
    @ColumnInfo(name = "user_record")
    var userRecord : String,
    @ColumnInfo(name = "commit_date")
    var commitDate : String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
    @ColumnInfo(name = "grade")
    var grade : Double = 0.0,
    ) {

    @Ignore
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun getStartDateAsLocalDate(): LocalDate {
        return LocalDate.parse(commitDate, formatter)
    }
}