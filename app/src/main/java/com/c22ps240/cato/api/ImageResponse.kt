package com.c22ps240.cato.api

import com.google.gson.annotations.SerializedName

data class ImageResponse(

    @field:SerializedName("image")
    val image: String,

    @field:SerializedName("message")
    val message: String,

    @field:SerializedName("predictions")
    val predictions: String,

    //@field:SerializedName("Error")
    //val error: String,

)