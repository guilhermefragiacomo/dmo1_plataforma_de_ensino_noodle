package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_course")
class Course (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    var courseId: Long = 0,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "description")
    var description: String = ""
    ) {
}