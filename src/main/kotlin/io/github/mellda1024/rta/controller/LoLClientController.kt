package io.github.mellda1024.rta.controller

import io.github.mellda1024.rta.core.json.impl.LoLClientDataManager
import io.github.mellda1024.rta.core.rofl.Rofl
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import tornadofx.*
import java.io.File

class LoLClientController : Controller() {

    val selectedLoLClientInfo = SimpleStringProperty("")
    val selectedLoLClient = SimpleObjectProperty<File?>()
    val selectedRofl = SimpleObjectProperty<Rofl>()
    val selectedRoflVersion = SimpleStringProperty("")
    val selectedRoflName = SimpleStringProperty("")
    val isExecuteAvailable = SimpleBooleanProperty(false)

    val replayBatButton = SimpleStringProperty("Replay.bat으로 저장")
    val executeButton = SimpleStringProperty("리플레이 실행")
    private lateinit var version: String


    fun init(rofl: Rofl) {
        selectedRofl.value = rofl
        selectedRoflName.value = "리플레이 파일 : ${rofl.name}"
        selectedRoflVersion.value = "리플레이 파일 버전 : ${rofl.version}"
        version = rofl.version
        update(LoLClientDataManager[rofl.version])
    }

    fun update(file: File?) {
        if (file != null) {
            isExecuteAvailable.value = false
            LoLClientDataManager.updateLoLClient(version, file)
        }
        else isExecuteAvailable.value = true
        updateLoLClientInfo(file)
        selectedLoLClient.value = file
    }

    private fun updateLoLClientInfo(file: File?) {
        if (file == null) selectedLoLClientInfo.value = "클라이언트의 경로 : 없음"
        else selectedLoLClientInfo.value = "클라이언트의 경로 : ${file.path}"
    }
}