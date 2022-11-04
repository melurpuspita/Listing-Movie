package id.melur.gliandroidtest.model

import com.google.gson.annotations.SerializedName

data class ReviewResponse(

    @field:SerializedName("results")
    val results: List<MovieReviews>
)
