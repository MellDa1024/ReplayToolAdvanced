package io.github.mellda1024.rta.core.json.impl

import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import io.github.mellda1024.rta.core.json.IJsonManager
import io.github.mellda1024.rta.core.json.ReplayToolData
import io.github.mellda1024.rta.core.rofl.Rofl
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.nio.file.Files

object ReplayToolDataManager : IJsonManager {

    private val file = File(programPath + "/ReplayToolData.json")
    private val gson = GsonBuilder().setPrettyPrinting().create()

    private lateinit var replayToolData: ReplayToolData

    init {
        load()
    }

    fun getRecentRofl() : Rofl? {
        if (replayToolData.recentRoflFile.isBlank()) return null
        val file = File(replayToolData.recentRoflFile)
        if (!file.exists()) return null
        return Rofl(file)
    }

    fun getRecentRoflFolder() : File? {
        if (replayToolData.recentRoflFolder.isBlank()) return null
        val file = File(replayToolData.recentRoflFolder)
        if (!file.exists()) return null
        return file
    }

    fun updateRecentRoflFile(file : File) {
        replayToolData.recentRoflFile = file.path
        save()
    }

    fun updateRecentRoflFolder(file: File) {
        replayToolData.recentRoflFolder = file.path
        save()
    }

    override fun load(): Boolean {
        if (!file.exists()) {
            try {
                val stream = javaClass.getResourceAsStream("/json/ReplayToolData.json")
                Files.copy(stream, file.toPath())
            } catch (e: Exception) {
                e.printStackTrace()
                return false
            }
        }
        return try {
            replayToolData = gson.fromJson(FileReader(file), object : TypeToken<ReplayToolData>() {}.type)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    override fun save(): Boolean {
        return try {
            BufferedWriter(FileWriter(file, false)).use {
                gson.toJson(replayToolData, it)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}