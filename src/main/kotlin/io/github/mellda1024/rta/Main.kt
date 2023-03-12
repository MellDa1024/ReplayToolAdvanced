package io.github.mellda1024.rta

import io.github.mellda1024.rta.app.MainApp
import io.github.mellda1024.rta.core.json.impl.LoLClientDataManager
import io.github.mellda1024.rta.core.json.impl.ReplayToolDataManager
import tornadofx.*

fun main() {
    val successCheck =
        ReplayToolDataManager.load() && LoLClientDataManager.load()
    if (successCheck) launch<MainApp>()
}
