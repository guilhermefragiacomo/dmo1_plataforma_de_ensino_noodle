package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tb_work_document",
    foreignKeys = [
        ForeignKey (
            entity = Work::class,
            parentColumns = ["work_id"],
            childColumns = ["work_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class WorkDocument (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "work_document_id")
    var workDocumentId : Long = 0,
    @ColumnInfo(name = "work_id")
    var workId : Long,
    @ColumnInfo(name = "document")
    var document : String = "",
    @ColumnInfo(name = "name")
    var name : String
    ) {
}