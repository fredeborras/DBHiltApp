package com.dbhiltapp.app.feature.main.entities

import java.io.Serializable

class PixabayImagesOut : Serializable {
    var hits: List<Hit>? = null
    var total: Long? = null
    var totalHits: Long? = null
}