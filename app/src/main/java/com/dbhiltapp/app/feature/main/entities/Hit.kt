package com.dbhiltapp.app.feature.main.entities

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.squareup.moshi.Json
import java.io.Serializable

//Adding BindingAdapter method to top level to avoid static definition
@BindingAdapter("imageURL")
fun loadImage(view: ImageView, url: String) = Glide.with(view).load(url).into(view)

class Hit : Serializable {
    var comments: Long? = null
    var downloads: Long? = null
    var favorites: Long? = null
    var id: Long? = null
    var imageHeight: Long? = null
    var imageSize: Long? = null
    var imageWidth: Long? = null
    var largeImageURL: String? = null
    var likes: Long? = null
    var pageURL: String? = null
    var previewHeight: Long? = null
    var previewURL: String? = null
    var previewWidth: Long? = null
    var tags: String? = null
    var type: String? = null
    var user: String? = null
    @Json(name = "user_id")
    var userId: Long? = null
    var userImageURL: String? = null
    var views: Long? = null
    var webformatHeight: Long? = null
    var webformatURL: String? = null
    var webformatWidth: Long? = null
}