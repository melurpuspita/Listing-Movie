package id.melur.gliandroidtest.model


import com.google.gson.annotations.SerializedName

data class Videos(
    @SerializedName("id")
    val id: Int,
    @SerializedName("results")
    val results: List<VideoItem>
)