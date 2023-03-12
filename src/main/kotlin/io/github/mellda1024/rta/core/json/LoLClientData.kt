package io.github.mellda1024.rta.core.json

import com.google.gson.annotations.SerializedName

data class LoLClientData(
    @SerializedName("Clients")
    val clients: MutableMap<String, String>
)