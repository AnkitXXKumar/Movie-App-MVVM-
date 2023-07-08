package com.example.notesapp.RoomSetup

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ViewModel (application: Application) : AndroidViewModel(application){

    private val repository : Repository
    val readAllMovies : LiveData<List<Movies>>


    init {
        val dao = MoviesDatabase.getDatabase(application).moviesdao()
        repository = Repository(dao)
        readAllMovies = repository.readAllMovies
    }


    fun insertMovies(movies: Movies){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertMovies(movies)
        }
    }

    fun deleteMoview(movies: Movies){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovie(movies)
        }
    }


}