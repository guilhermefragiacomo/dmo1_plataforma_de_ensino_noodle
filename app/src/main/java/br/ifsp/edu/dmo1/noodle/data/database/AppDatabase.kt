package br.ifsp.edu.dmo1.noodle.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import br.ifsp.edu.dmo1.noodle.data.dao.CourseDao
import br.ifsp.edu.dmo1.noodle.data.dao.CourseUserDao
import br.ifsp.edu.dmo1.noodle.data.dao.LessonDao
import br.ifsp.edu.dmo1.noodle.data.dao.LessonDocumentDao
import br.ifsp.edu.dmo1.noodle.data.dao.SessionDao
import br.ifsp.edu.dmo1.noodle.data.dao.UserDao
import br.ifsp.edu.dmo1.noodle.data.dao.WorkCommitDao
import br.ifsp.edu.dmo1.noodle.data.dao.WorkCommitDocumentDao
import br.ifsp.edu.dmo1.noodle.data.dao.WorkDao
import br.ifsp.edu.dmo1.noodle.data.dao.WorkDocumentDao
import br.ifsp.edu.dmo1.noodle.data.model.Course
import br.ifsp.edu.dmo1.noodle.data.model.CourseUser
import br.ifsp.edu.dmo1.noodle.data.model.Lesson
import br.ifsp.edu.dmo1.noodle.data.model.LessonDocument
import br.ifsp.edu.dmo1.noodle.data.model.Session
import br.ifsp.edu.dmo1.noodle.data.model.User
import br.ifsp.edu.dmo1.noodle.data.model.Work
import br.ifsp.edu.dmo1.noodle.data.model.WorkCommit
import br.ifsp.edu.dmo1.noodle.data.model.WorkCommitDocument
import br.ifsp.edu.dmo1.noodle.data.model.WorkDocument

@Database(entities = [Course::class, Lesson::class, LessonDocument::class, User::class, Work::class, WorkCommit::class, WorkCommitDocument::class, WorkDocument::class, Session::class, CourseUser::class], version = 4)
abstract class AppDatabase : RoomDatabase() {
    companion object {
        const val DATABASE_NAME = "app_noodle.db"
        private lateinit var instance: AppDatabase
        fun getInstance(context: Context): AppDatabase {
            if (!::instance.isInitialized) {
                synchronized(AppDatabase::class) {
                    instance = Room
                        .databaseBuilder(
                            context,
                            AppDatabase::class.java,
                            DATABASE_NAME
                        )
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return instance
        }
    }

    abstract fun getCourseDao(): CourseDao
    abstract fun getLessonDao(): LessonDao
    abstract fun getWorkDao(): WorkDao
    abstract fun getUserDao(): UserDao
    abstract fun getWorkCommitDao(): WorkCommitDao
    abstract fun getWorkCommitDocumentDao(): WorkCommitDocumentDao
    abstract fun getWorkDocumentDao() : WorkDocumentDao
    abstract fun getLessonDocumentDao() : LessonDocumentDao
    abstract fun getSessionDao() : SessionDao
    abstract fun getCourseUserDao() : CourseUserDao
}
