package io.github.mellda1024.rta.core.json

import com.google.gson.annotations.SerializedName

data class ReplayToolData(
    @SerializedName("RecentRoflFolder")
    var recentRoflFolder : String,

    @SerializedName("RecentRoflFile")
    var recentRoflFile : String
)