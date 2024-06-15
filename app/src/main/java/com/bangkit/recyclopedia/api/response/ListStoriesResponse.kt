package com.bangkit.recyclopedia.api.response

import com.google.gson.annotations.SerializedName

class ListStoriesResponse (
    @field:SerializedName("listStory")
    val listStory: List<ListStoryItemsResponse>,

    @field:SerializedName("error")
    val error: Boolean,

    @field:SerializedName("message")
    val message: String
)

