package com.example.anothertimdatxe.entity

import android.net.Uri
import com.example.anothertimdatxe.adapter.ImageAdapter

class DriverCarImage(
        var absoluteImage: String? = null,
        var uriImage: Uri? = null,
        var type: Int = ImageAdapter.VIEW_TYPE_BTN_ADD,
        var isUrl: Boolean = false
)