package com.mehmet.cs388_project3

import com.google.gson.annotations.SerializedName

class TopMovie {
    @SerializedName("id")
    var id: Int = 0

    @JvmField
    @SerializedName("title")
    var title: String? = null

    @SerializedName("poster_path")
    var posterPath: String? = null

    @SerializedName("overview")
    var overview: String? = null
}