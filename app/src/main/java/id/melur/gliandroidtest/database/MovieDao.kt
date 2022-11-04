package id.melur.gliandroidtest.database


import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface MovieDao {

    @Query("SELECT * FROM movies ORDER BY release_date DESC")
    fun getMovies(): LiveData<List<MovieEntity>>

    @Query("SELECT * FROM movies WHERE id = :movieId")
    fun getMovie(movieId: Int): LiveData<MovieEntity>

//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertMovies(movies: List<MovieEntity>)
//
//    @Insert(onConflict = OnConflictStrategy.IGNORE)
//    suspend fun insertMovie(movie: MovieEntity)

//    @Query("DELETE FROM movies")
//    suspend fun deleteMovies()
}