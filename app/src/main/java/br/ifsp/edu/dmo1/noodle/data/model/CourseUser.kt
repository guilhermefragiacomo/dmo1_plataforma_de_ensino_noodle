package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(tableName = "tb_course_user",
    foreignKeys = [
        ForeignKey (
            entity = Course::class,
            parentColumns = ["course_id"],
            childColumns = ["course_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey (
            entity = User::class,
            parentColumns = ["record"],
            childColumns = ["user_record"],
            onDelete = ForeignKey.CASCADE
        )
    ], primaryKeys = ["user_record" , "course_id"]
)
class CourseUser (
    @ColumnInfo(name = "user_record")
    var userRecord : String,
    @ColumnInfo(name = "course_id")
    var courseId : String,
    @ColumnInfo(name = "course_role")
    var courseRole : String
    ) {

    override fun toString() : String {
        return userRecord + " / " + courseId + " / " + courseRole;
    }
}