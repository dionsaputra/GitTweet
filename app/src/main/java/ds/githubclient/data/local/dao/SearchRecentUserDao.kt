package ds.githubclient.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ds.githubclient.data.local.entity.SearchRecentUser

@Dao
interface SearchRecentUserDao {

    @Query("SELECT * FROM search_recent_users")
    fun list(): List<SearchRecentUser>

    @Insert
    fun insert(searchRecentUser: SearchRecentUser)

    @Query("DELETE FROM search_recent_users")
    fun deleteAll()
}