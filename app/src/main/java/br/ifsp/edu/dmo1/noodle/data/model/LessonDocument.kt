package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tb_lesson_document",
        foreignKeys = [
            ForeignKey (
                entity = Lesson::class,
                parentColumns = ["lesson_id"],
                childColumns = ["lesson_id"],
                onDelete = ForeignKey.CASCADE
            )
        ]
    )
class LessonDocument (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "lesson_document_id")
    var lessonDocumentId : Long = 0,
    @ColumnInfo(name = "lesson_id")
    var lessonId : Long,
    @ColumnInfo(name = "document")
    var document : String,
    @ColumnInfo(name = "name")
    var name : String = ""
    ) {
}