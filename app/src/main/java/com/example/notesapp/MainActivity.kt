package com.example.notesapp

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.Movie
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.Adapters.MovieAdapter
import com.example.notesapp.RoomSetup.Movies
import com.example.notesapp.databinding.ActivityMainBinding
import java.lang.reflect.Array

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var dialog : Dialog
    private lateinit var SelectedCategories : String
    private lateinit var viewmodel : com.example.notesapp.RoomSetup.ViewModel
    private lateinit var movieAdapter : MovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding  = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        movieAdapter = MovieAdapter(this)

        binding.movieRec.layoutManager = LinearLayoutManager( this)
        binding.movieRec.adapter = movieAdapter


        viewmodel = ViewModelProvider(this).get(com.example.notesapp.RoomSetup.ViewModel::class.java)
        viewmodel.readAllMovies.observe(this , {movies -> movieAdapter.setData(movies) })
        binding.floatingActionButton.setOnClickListener {
           showDialog()
        }

        movieAdapter.OnItemClick = {
            viewmodel.deleteMoview(it)
            Toast.makeText(this, "Movie Deleted", Toast.LENGTH_SHORT).show()
        }

    }
    private fun showDialog() {
        dialog = Dialog(this)
        dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT));
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.movie_list_item)
        dialog.setCancelable(true)
        val layoutParams = WindowManager.LayoutParams()
        layoutParams.copyFrom(dialog.window!!.attributes)
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT
        layoutParams.height = WindowManager.LayoutParams.WRAP_CONTENT

        val movieField : EditText = dialog.findViewById(R.id.movieField)
        val MoveListItem : AutoCompleteTextView = dialog.findViewById(R.id.autoCompleteTextView)

        MoveListItem.setOnClickListener {
            val list = resources.getStringArray(R.array.categories)
            val arrayAdapter = ArrayAdapter(this , R.layout.drop_down_item , list)
            MoveListItem.setAdapter(arrayAdapter)
        }

        MoveListItem.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, position, id ->
            val itemSelected = adapterView.getItemAtPosition(position)
            SelectedCategories = itemSelected.toString()

        }

        dialog.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }

        dialog.findViewById<Button>(R.id.btnAddMovie).setOnClickListener {
            if (movieField.text.toString().isEmpty() || MoveListItem.text.toString().isEmpty()){
                Toast.makeText(this, "Please fill all detailes", Toast.LENGTH_SHORT).show()
            }
            else{
                val movie = Movies(0 , SelectedCategories , movieField.text.toString() , "Pending")
                viewmodel.insertMovies(movie)
                Toast.makeText(this, "Movie Added", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }
        }

        dialog.show()
        dialog.window!!.attributes = layoutParams

    }
}