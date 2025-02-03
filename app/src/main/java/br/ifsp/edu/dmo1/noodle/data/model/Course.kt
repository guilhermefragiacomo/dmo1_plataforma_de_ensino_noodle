package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tb_course")
class Course (
    @PrimaryKey()
    @ColumnInfo(name = "course_id")
    var courseId: String = "",
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "description")
    var description: String = ""
    ) {

    init {
        courseId = generateCourseId();
    }

    fun generateCourseId() : String {
        return "C" + UUID.randomUUID().toString().replace("-", "").take(10)
    }
}