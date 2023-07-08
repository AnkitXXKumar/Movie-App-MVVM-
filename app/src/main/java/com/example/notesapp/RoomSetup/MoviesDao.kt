package com.example.notesapp.RoomSetup

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MoviesDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertMovies(movies:Movies)

    @Query("SELECT * FROM movies")
    fun readAllMovies():LiveData<List<Movies>>

    @Delete
    fun deleteMovie(movies: Movies)

}