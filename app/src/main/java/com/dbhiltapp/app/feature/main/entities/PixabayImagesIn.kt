package com.dbhiltapp.app.feature.main.entities

import com.squareup.moshi.Json
import java.io.Serializable

class PixabayImagesIn : Serializable {
    var key: String? = null
    var q: String? = null
    @Json(name = "image_type")
    var imageType: String? = null
    var pretty: Boolean = false
}