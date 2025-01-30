package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "tb_work_commit_document",
    foreignKeys = [
        ForeignKey (
            entity = WorkCommit::class,
            parentColumns = ["work_commit_id"],
            childColumns = ["work_commit_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
class WorkCommitDocument (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "work_commit_document_id")
    var workCommitDocumentId : Int,
    @ColumnInfo(name = "work_commit_id")
    var workCommitId : Int,
    @ColumnInfo(name = "document")
    var document : String = ""
    ) {
}