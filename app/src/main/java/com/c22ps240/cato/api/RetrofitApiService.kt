package com.c22ps240.cato.api

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.*


interface RetrofitApiService {
    @Multipart
    @POST("/success")
    fun postImage(
        @Part file: MultipartBody.Part,
    ) : Call<ImageResponse>

}