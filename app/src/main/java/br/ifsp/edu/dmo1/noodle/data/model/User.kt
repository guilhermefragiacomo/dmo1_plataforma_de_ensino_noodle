package br.ifsp.edu.dmo1.noodle.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.Index
import androidx.room.PrimaryKey
import br.ifsp.edu.dmo1.noodle.util.sha256
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.UUID

@Entity(tableName = "tb_user", indices = [Index(value = ["email"],
    unique = true)])
class User (
    @PrimaryKey
    @ColumnInfo(name = "record")
    var record : String,
    @ColumnInfo(name = "name")
    var name : String,
    @ColumnInfo(name = "birth")
    var birth : String = LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
    @ColumnInfo(name = "email")
    var email : String,
    @ColumnInfo(name = "pass")
    var pass : String,
    @ColumnInfo(name = "verified")
    var verified : Boolean
    ) {

    @Ignore
    private val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    companion object {
        fun createNewUser(name: String, password : String, birth : String, email : String, verified : Boolean) : User {
            return User(
                record = generateRecord(),
                name = name,
                birth = birth,
                email = email,
                pass = hashPassword(password),
                verified = verified
            )
        }

        @Ignore
        fun generateRecord() : String {
            return "ND" + UUID.randomUUID().toString().replace("-", "").take(10)
        }

        @Ignore
        fun hashPassword(pass : String) : String{
            return pass.sha256()
        }
    }

    @Ignore
    fun getBirthAsLocalDate(): LocalDate {
        return LocalDate.parse(birth, formatter)
    }

    @Ignore
    override fun toString(): String {
        return "User(record='$record', name='$name', birth='$birth', email='$email', pass='$pass'"
    }
}