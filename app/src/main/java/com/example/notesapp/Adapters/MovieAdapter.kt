package com.example.notesapp.Adapters

import android.content.Context
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.notesapp.R
import com.example.notesapp.RoomSetup.Movies

class MovieAdapter(val context: Context): RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    private var movies = emptyList<Movies>()
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor
    var OnItemClick:((Movies)->Unit)? = null

    init {
        sharedPreferences = context.getSharedPreferences("your_pref_name", Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }


    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val movieName = itemView.findViewById<TextView>(R.id.movieName)
        val movieCategory = itemView.findViewById<TextView>(R.id.movieCategory)
        val movieStatus = itemView.findViewById<TextView>(R.id.movieStatus)
        val movieCheck = itemView.findViewById<CheckBox>(R.id.checkBox)
        val movieDelete = itemView.findViewById<ImageView>(R.id.movieDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.waiting_list, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = movies[position]
        holder.movieName.text = currentItem.name
        holder.movieCategory.text = currentItem.type
        holder.movieStatus.text = currentItem.status

        holder.movieDelete.setOnClickListener {
            OnItemClick!!.invoke(currentItem)
        }

        val isMovieWatched = sharedPreferences.getBoolean(currentItem.id.toString(), false)
        holder.movieCheck.isChecked = isMovieWatched
        holder.movieStatus.text = if (isMovieWatched) "Watched" else "Pending"

        holder.movieCheck.setOnCheckedChangeListener { _, isChecked ->
            val editor: SharedPreferences.Editor = sharedPreferences.edit()
            editor.putBoolean(currentItem.id.toString(), isChecked)
            editor.apply()

            holder.movieStatus.text = if (isChecked) "Watched" else "Pending"

        }
    }

    fun setData(movies: List<Movies>){
        this.movies = movies
        notifyDataSetChanged()
    }

}