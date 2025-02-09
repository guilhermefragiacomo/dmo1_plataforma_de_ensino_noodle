package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Entity(tableName = "tb_work", foreignKeys = [
    ForeignKey (
        entity = Course::class,
        parentColumns = ["course_id"],
        childColumns = ["course_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
class Work (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "work_id")
    var workId : Long = 0,
    @ColumnInfo(name = "name")
    var name : String = "",
    @ColumnInfo(name = "description")
    var description : String = "",
    @ColumnInfo(name = "course_id")
    var course_id : String,
    @ColumnInfo(name = "start_date")
    var startDate : String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
    @ColumnInfo(name = "dead_line")
    var deadLine : String = LocalDate.now().plusDays(1).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    ) {

    companion object {
        fun createNewWork(
            name: String,
            description: String,
            course_id: String,
            start_date: LocalDate,
            dead_line: LocalDate
        ): Work {
            return Work(
                name = name,
                description = description,
                course_id = course_id,
                startDate = start_date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                deadLine = dead_line.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            )
        }
    }

    @Ignore
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    fun getStartDateAsLocalDate(): LocalDate {
        return LocalDate.parse(startDate, formatter)
    }
    fun getDeadLineAsLocalDate(): LocalDate {
        return LocalDate.parse(deadLine, formatter)
    }
}