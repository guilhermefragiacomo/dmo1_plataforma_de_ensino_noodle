package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@Entity(tableName = "tb_lesson", foreignKeys = [
    ForeignKey (
        entity = Course::class,
        parentColumns = ["course_id"],
        childColumns = ["course_id"],
        onDelete = ForeignKey.CASCADE
    )]
)
class Lesson (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "lesson_id")
    var lessonId : Long = 0,
    @ColumnInfo(name = "name")
    var name : String,
    @ColumnInfo(name = "course_id")
    var course_id : String,
    @ColumnInfo(name = "description")
    var description : String = "",
    @ColumnInfo(name = "date")
    var date : String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
    ) {

    companion object {
        fun createNewLesson(
            name: String,
            description: String,
            course_id: String,
            date: LocalDate
        ): Lesson {
            return Lesson(
                name = name,
                description = description,
                course_id = course_id,
                date = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))
            )
        }
    }

    @Ignore
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    @Ignore
    fun getDateAsLocalDate(): LocalDate {
        return LocalDate.parse(date, formatter)
    }
}