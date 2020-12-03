package com.dbhiltapp.app.feature.main.view.adapter

import com.dbhiltapp.app.feature.main.entities.Hit

interface OnItemClick {
    /**
     * Called when item from MainListAdapter is clicked
     */
    fun itemClick(data: Hit)
}