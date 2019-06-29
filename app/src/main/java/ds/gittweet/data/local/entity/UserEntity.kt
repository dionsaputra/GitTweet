package ds.gittweet.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "search_recent_users")
data class UserEntity(
    @PrimaryKey @ColumnInfo(name = "login") val login: String,
    @ColumnInfo(name = "avatar_url") val avatarUrl: String? = null,
    @ColumnInfo(name = "url") val url: String? = null
)