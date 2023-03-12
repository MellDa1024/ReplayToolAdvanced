package io.github.mellda1024.rta.core.json.impl

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.mellda1024.rta.core.json.IJsonManager
import io.github.mellda1024.rta.core.json.LoLClientData
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.nio.file.Files

object LoLClientDataManager : IJsonManager {

    private val file = File(programPath + "/LoLClientData.json")
    private val gson = GsonBuilder().setPrettyPrinting().create()

    private lateinit var lolClientData: LoLClientData

    init {
        load()
    }

    operator fun get(version: String) : File?{
        lolClientData.clients[version]?.let {
            if (it.isBlank()) return null
            val file = File(it)
            if (!file.exists()) return null
            return file
        } ?: return null
    }

    fun updateLoLClient(version : String, file : File) {
        lolClientData.clients[version] = file.path
        save()
    }

    override fun load(): Boolean {
        if (!file.exists()) {
            try {
                val stream = javaClass.getResourceAsStream("/json/LoLClientData.json")
                Files.copy(stream, file.toPath())
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        }
        return try {
            lolClientData = gson.fromJson(FileReader(file), object : TypeToken<LoLClientData>() {}.type)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun save(): Boolean {
        return try {
            BufferedWriter(FileWriter(file, false)).use {
                gson.toJson(lolClientData, it)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}