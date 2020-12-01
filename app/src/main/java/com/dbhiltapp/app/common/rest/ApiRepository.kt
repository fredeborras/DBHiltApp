package com.dbhiltapp.app.common.rest

import com.dbhiltapp.app.feature.main.entities.PixabayImagesIn
import com.dbhiltapp.app.feature.main.entities.PixabayImagesOut
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ApiRepository @Inject constructor(private val dataSource: ApiDataSource) {

    suspend fun launchPixabayImages(pixabayIn: PixabayImagesIn): Response<PixabayImagesOut> = this.dataSource.pixabayImages(
        pixabayIn.key,
        pixabayIn.q,
        pixabayIn.imageType,
        pixabayIn.pretty
    )

}