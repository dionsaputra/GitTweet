package ds.gittweet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ds.gittweet.data.local.entity.SearchRecentUser
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single

@Dao
interface SearchRecentUserDao {

    @Query("SELECT * FROM search_recent_users")
    fun list(): Single<List<SearchRecentUser>>

    @Insert
    fun insert(searchRecentUser: SearchRecentUser): Completable

    @Query("DELETE FROM search_recent_users")
    fun deleteAll()
}