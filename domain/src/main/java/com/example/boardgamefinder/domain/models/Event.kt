package com.example.boardgamefinder.domain.models

import com.google.gson.annotations.SerializedName

data class Event(
    val id: Int,
    val title: String,
    val imageUrl: String?,
    val date: String,
    val tags: List<Tag>?,
    val locationShort: String,
    val visitorsCount: Int,
    val visitorsLimit: Int,
    val distance: Int,
    val creator: User,
    val likes: Int,
    val liked: Boolean,
    val subscriptionStatus: SubscriptionStatus,
    val latitude: Double,
    val longitude: Double
)

{
    enum class SubscriptionStatus{
        @SerializedName("not_submitted")
        NOT_SUBMITTED,
        @SerializedName("requested")
        REQUESTED,
        @SerializedName("accepted")
        ACCEPTED
    }
}