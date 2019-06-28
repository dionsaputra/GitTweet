package ds.githubclient.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ds.githubclient.data.local.dao.SearchRecentUserDao
import ds.githubclient.data.local.entity.SearchRecentUser

@Database(entities = [SearchRecentUser::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun searchRecentUserDao(): SearchRecentUserDao

    companion object {
        private var instance: GithubDatabase? = null

        fun getInstance(context: Context): GithubDatabase {
            if (instance == null) {
                synchronized(GithubDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        GithubDatabase::class.java,
                        "github_database.db"
                    ).build()
                }
            }
            return instance!!
        }

        fun destroyInstance() {
            instance = null
        }
    }
}