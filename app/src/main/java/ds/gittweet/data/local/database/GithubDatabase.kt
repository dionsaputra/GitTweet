package ds.gittweet.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ds.gittweet.data.local.dao.UserDao
import ds.gittweet.data.local.entity.UserEntity

@Database(entities = [UserEntity::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun getUserDao(): UserDao

}