package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.UUID

@Entity(tableName = "tb_user")
class User (
    @ColumnInfo(name = "record")
    var record : String = "",
    @ColumnInfo(name = "name")
    var name : String,
    @ColumnInfo(name = "birth")
    var birth : String = "",
    @ColumnInfo(name = "email")
    var email : String,
    @ColumnInfo(name = "pass")
    var pass : String,
    @ColumnInfo(name = "type")
    var type : String,

    birthLocalDate : LocalDate = LocalDate.now()
    ) {

    @Ignore
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    init {
        birth = formatter.format(birthLocalDate)
        record = generateRecord();
    }

    fun generateRecord() : String {
        return UUID.randomUUID().toString().replace("-", "").take(10)
    }
}