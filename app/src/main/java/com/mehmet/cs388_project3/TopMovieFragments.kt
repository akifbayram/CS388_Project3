package com.mehmet.cs388_project3

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.Headers
import org.json.JSONObject

private const val API_KEY = "UGRp35FQyTR5cjmMLj6YRdUC6elqZKJH"

class TopMovieFragment : Fragment(), OnListFragmentInteractionListener {

    /*
     * Constructing the view
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top_movies_list, container, false)
        val progressBar = view.findViewById<View>(R.id.progress) as ContentLoadingProgressBar
        val recyclerView = view.findViewById<View>(R.id.list) as RecyclerView
        val context = view.context
        recyclerView.layoutManager = GridLayoutManager(context, 1) // 1 column
        updateAdapter(progressBar, recyclerView)
        return view
    }

    /*
     * Updates the RecyclerView adapter with new data.  This is where the
     * networking magic happens!
     */
    private fun updateAdapter(progressBar: ContentLoadingProgressBar, recyclerView: RecyclerView) {
        progressBar.show()

        // Create and set up an AsyncHTTPClient() here
        val client = AsyncHttpClient()

        // Using the client, perform the HTTP request
        client.get(
            "https://api.themoviedb.org/3/movie/now_playing?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed",
            object : JsonHttpResponseHandler() {
                /*
                 * The onSuccess function gets called when
                 * HTTP response status is "200 OK"
                 */
                override fun onSuccess(
                    statusCode: Int,
                    headers: Headers,
                    json: JsonHttpResponseHandler.JSON
                ) {
                    // The wait for a response is over
                    progressBar.hide()

                    // Parse JSON into Models
                    val gson = Gson()
                    val arrayMovieType = object : TypeToken<List<TopMovie>>() {}.type
                    val moviesRawJSON: String = json.jsonObject.get("results").toString()
                    val models: List<TopMovie> = gson.fromJson(moviesRawJSON, arrayMovieType)
                    recyclerView.adapter =
                        TopMoviesRecyclerViewAdapter(models, this@TopMovieFragment)

                    // Look for this in Logcat:
                    Log.d("TopMoviesFragment", "response successful")
                }

                /*
                 * The onFailure function gets called when
                 * HTTP response status is "4XX" (eg. 401, 403, 404)
                 */
                override fun onFailure(
                    statusCode: Int,
                    headers: Headers?,
                    errorResponse: String,
                    t: Throwable?
                ) {
                    // The wait for a response is over
                    progressBar.hide()

                    // If the error is not null, log it!
                    t?.message?.let {
                        Log.e("TopMoviesFragment", errorResponse)
                    }
                }
            }
        )
    }

    /*
     * What happens when a particular movie is clicked.
     */
    override fun onItemClick(item: TopMovie) {
        Toast.makeText(context, "test: " + item.title, Toast.LENGTH_LONG).show()
    }

}