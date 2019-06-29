package ds.gittweet.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import ds.gittweet.data.local.entity.UserEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

@Dao
interface SearchRecentUserDao {

    @Query("SELECT * FROM search_recent_users")
    fun list(): Observable<List<UserEntity>>

    @Insert
    fun insert(searchRecentUser: UserEntity): Completable

    @Query("DELETE FROM search_recent_users")
    fun deleteAll()
}