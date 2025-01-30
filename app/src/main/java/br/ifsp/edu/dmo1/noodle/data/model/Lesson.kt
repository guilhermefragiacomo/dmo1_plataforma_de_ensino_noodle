package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@Entity(tableName = "tb_lesson")
class Lesson (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "lesson_id")
    var lessonId : Long = 0,
    @ColumnInfo(name = "name")
    var name : String,
    @ColumnInfo(name = "description")
    var description : String = "",
    @ColumnInfo(name = "date")
    var date : String = "",

    dateLocalDate : LocalDate = LocalDate.now()
    ) {

    @Ignore
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    init {
        date = formatter.format(dateLocalDate)
    }
}