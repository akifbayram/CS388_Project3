package com.mehmet.cs388_project3

/**
 * This interface is used by the [BestSellerBooksRecyclerViewAdapter] to ensure
 * it has an appropriate Listener.
 *
 * In this app, it's implemented by [TopMovieFragment]
 */
interface OnListFragmentInteractionListener {
    fun onItemClick(item: TopMovie)
}
