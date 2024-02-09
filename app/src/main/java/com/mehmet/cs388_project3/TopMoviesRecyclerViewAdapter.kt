package com.mehmet.cs388_project3

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mehmet.cs388_project3.R.id

class TopMoviesRecyclerViewAdapter(
    private val movies: List<TopMovie>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<TopMoviesRecyclerViewAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_top_movie, parent, false)
        return MovieViewHolder(view)
    }

    inner class MovieViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        var mItem: TopMovie? = null
        val mMovieTitle: TextView = mView.findViewById<View>(id.movie_title) as TextView
        val mMovieImage: ImageView = mView.findViewById<View>(id.movie_image) as ImageView
        val mMovieDescription: TextView = mView.findViewById<View>(id.movie_description) as TextView

        override fun toString(): String {
            return super.toString() + " '" + mMovieTitle.text + "'"
        }
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movies[position]

        holder.mItem = movie
        holder.mMovieTitle.text = movie.title

        // Limit the number of characters in the overview
        val maxChars = 160
        holder.mMovieDescription.text = if (movie.overview?.length ?: 0 > maxChars) {
            "${movie.overview?.substring(0, maxChars)}..."
        } else {
            movie.overview
        }

        holder.mView.setOnClickListener {
            holder.mItem?.let { movie ->
                mListener?.onItemClick(movie)
            }
        }

        Glide.with(holder.mView)
            .load("https://image.tmdb.org/t/p/w500" + movie.posterPath)
            .placeholder(R.drawable.loading_placeholder)
            .centerInside()
            .into(holder.mMovieImage)
    }

    override fun getItemCount(): Int {
        return movies.size
    }
}