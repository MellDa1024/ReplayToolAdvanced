package io.github.mellda1024.rta.core.json

import java.io.File

interface IJsonManager {

    val programPath: String get() = File(".").canonicalPath

    fun load(): Boolean

    fun save(): Boolean
}