package io.github.mellda1024.test

import io.github.mellda1024.rta.core.rofl.Rofl
import org.junit.jupiter.api.Test
import java.io.File

class RoflTest {
    val filePath = "D:\\USER\\Documents\\League of Legends\\Replays\\JP1-340600621.rofl"

    @Test
    fun roflTest() {
        val rofl = Rofl(File(filePath))
        println(rofl.version)
        println(rofl.createdDate)
    }

    @Test
    fun roflTest2() {
        val versionRaw = "13.5.4958836+branch.releases-13-5.content.release"
        val subStringIndex = versionRaw.indexOf("+")
        val version = versionRaw.substring(0 until subStringIndex)
        val dotIndex = version.lastIndexOf(".") + 4
        val finalVersion = "${version.substring(0 until dotIndex)}.${version.substring(dotIndex until version.length)}"
        println(finalVersion)
    }
}