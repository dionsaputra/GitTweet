package ds.gittweet.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ds.gittweet.data.local.dao.SearchRecentUserDao
import ds.gittweet.data.local.entity.SearchRecentUser
import ds.gittweet.util.AppConstant

@Database(entities = [SearchRecentUser::class], version = 1)
abstract class GithubDatabase : RoomDatabase() {

    abstract fun searchRecentUserDao(): SearchRecentUserDao

}