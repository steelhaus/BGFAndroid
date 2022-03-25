package com.example.boardgamefinder.domain.models

import com.google.gson.annotations.SerializedName

data class Creator(
    val id: Int,
    @SerializedName("nickname")
    val username: String,
    val imageUrl: String?
)