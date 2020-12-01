package com.dbhiltapp.app.common.rest

import com.dbhiltapp.app.feature.main.entities.PixabayImagesOut
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiDataSource {

    @GET("api")
    suspend fun pixabayImages(
        @Query("key") key: String?,
        @Query("q") q: String?,
        @Query("image_type") imageType: String?,
        @Query("pretty") pretty: Boolean?
    ): Response<PixabayImagesOut>

}