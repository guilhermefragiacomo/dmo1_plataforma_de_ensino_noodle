package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.UUID

@Entity(tableName = "tb_course")
class Course (
    @PrimaryKey()
    @ColumnInfo(name = "course_id")
    var courseId: String,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "description")
    var description: String = ""
    ) {

    companion object {
        fun createNewCourse(
            name: String,
            description: String
        ): Course {
            return Course(
                name = name,
                description = description,
                courseId = generateCourseId()
            )
        }

        fun generateCourseId() : String {
            return "C" + UUID.randomUUID().toString().replace("-", "").take(10)
        }
    }
}