package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date

@Entity(tableName = "tb_work")
class Work (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "work_id")
    var workId : Long = 0,
    @ColumnInfo(name = "name")
    var name : String = "",
    @ColumnInfo(name = "description")
    var description : String = "",
    @ColumnInfo(name = "start_date")
    var startDate : String = "",
    @ColumnInfo(name = "dead_line")
    var deadLine : String = "",

    startLocalDate : LocalDate = LocalDate.now(),
    deadLineLocalDate : LocalDate = LocalDate.now().plusDays(1)
    ) {

    @Ignore
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    init {
        startDate = formatter.format(startLocalDate)
        deadLine = formatter.format(deadLineLocalDate)
    }
}