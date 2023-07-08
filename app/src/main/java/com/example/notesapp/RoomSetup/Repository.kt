package com.example.notesapp.RoomSetup

import androidx.lifecycle.LiveData

class Repository(private val dao:MoviesDao){

    suspend fun insertMovies(movies: Movies){
        dao.insertMovies(movies)
    }

   val readAllMovies : LiveData<List<Movies>> = dao.readAllMovies()

    suspend fun deleteMovie(movies: Movies){
        dao.deleteMovie(movies)
    }

}