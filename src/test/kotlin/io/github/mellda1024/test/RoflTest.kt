package io.github.mellda1024.test

import io.github.mellda1024.rta.core.rofl.Rofl
import org.junit.jupiter.api.Test
import java.io.File

class RoflTest {
    val filePath = "D:\\USER\\Documents\\League of Legends\\Replays\\JP1-340600621.rofl"

    @Test
    fun roflFest() {
        val rofl = Rofl(File(filePath))
        println(rofl.version)
        println(rofl.createdDate)
    }
}