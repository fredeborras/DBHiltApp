package com.dbhiltapp.app.feature.main.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dbhiltapp.app.common.rest.ApiRepository
import com.dbhiltapp.app.feature.main.entities.PixabayImagesIn
import com.dbhiltapp.app.feature.main.entities.PixabayImagesOut
import kotlinx.coroutines.launch

class MainViewModel @ViewModelInject constructor(private val repository: ApiRepository) :
    ViewModel() {

    val images = MutableLiveData<PixabayImagesOut>()

    init {
        launchPixabayImages()
    }

    private fun launchPixabayImages() {
        //Init values by default
        val pixabayIn = PixabayImagesIn()
        pixabayIn.key = "17111786-e5d5d4c6e2e59c875a9ab3e5f"
        pixabayIn.q = "world"
        pixabayIn.imageType = "photo"
        pixabayIn.pretty = true

        viewModelScope.launch {
            repository.launchPixabayImages(pixabayIn).let {
                if (it.isSuccessful) {
                    images.value = it.body()
                } else {
                    images.value = PixabayImagesOut() //Empty response
                }
            }
        }
    }
}