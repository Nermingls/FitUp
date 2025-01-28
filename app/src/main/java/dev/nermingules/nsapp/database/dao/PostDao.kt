package dev.nermingules.nsapp.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import dev.nermingules.nsapp.model.Post

@Dao
interface PostDao {
    @Insert
    suspend fun insertPost(post: Post)

    @Query("SELECT * FROM posts")
    suspend fun getAllPosts(): List<Post>
}
