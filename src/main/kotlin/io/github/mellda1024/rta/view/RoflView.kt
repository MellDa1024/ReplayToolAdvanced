package io.github.mellda1024.rta.view

import io.github.mellda1024.rta.controller.LoLClientController
import io.github.mellda1024.rta.core.json.impl.ReplayToolDataManager
import io.github.mellda1024.rta.core.rofl.Rofl
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.text.TextAlignment
import javafx.stage.FileChooser.ExtensionFilter
import tornadofx.*
import java.io.File

class RoflView(rofl: Rofl) : View("ReplayToolAdvanced") {
    private val controller: LoLClientController by inject()

    override val root = borderpane {
        prefWidth = 700.0
        controller.init(rofl)
        top<RoflInfoView>()
        bottom<RoflControlView>()
    }
}

class RoflControlView : View() {
    private val controller: LoLClientController by inject()

    override val root = hbox(20) {

        val extFilter = arrayOf(
            ExtensionFilter("League of Legends.exe", "League of Legends.exe")
        )

        alignment = Pos.CENTER
        button {
            textAlignment = TextAlignment.CENTER
            text = "클라이언트 경로 설정"
            setOnAction {
                val lolClient = chooseFile(
                    "클라이언트를 선택해주세요.",
                    filters = extFilter,
                    initialDirectory = controller.selectedLoLClient.value?.parentFile
                )
                if (lolClient.isNotEmpty()) {
                    controller.update(lolClient[0])
                }
            }
        }
        button {
            textProperty().bind(controller.executeButton)
            disableProperty().bind(controller.isExecuteAvailable)
            setOnAction {
                runAsync {
                    controller.isExecuteAvailable.value = true
                    Platform.runLater {
                        controller.executeButton.value = "실행중입니다..."
                    }
                    controller.selectedRofl.value.execute(
                        controller.selectedLoLClient.value!!
                    )
                    ReplayToolDataManager.updateRecentRoflFile(controller.selectedRofl.value.file)
                    Thread.sleep(1000)
                    Platform.runLater {
                        controller.executeButton.value = "리플레이 실행"
                    }
                    controller.isExecuteAvailable.value = false
                }
            }
        }
        button {
            textProperty().bind(controller.replayBatButton)
            disableProperty().bind(controller.isExecuteAvailable)
            setOnAction {
                runAsync {
                    controller.isExecuteAvailable.value = true
                    val result = controller.selectedRofl.value.saveBatchFile(
                        controller.selectedLoLClient.value!!,
                        File(".")
                    )
                    Platform.runLater {
                        controller.replayBatButton.value = if (result) "저장하였습니다."
                        else "저장에 실패하였습니다."
                    }
                    ReplayToolDataManager.updateRecentRoflFile(controller.selectedRofl.value.file)
                    Thread.sleep(1000)
                    Platform.runLater {
                        controller.replayBatButton.value = "Replay.bat으로 저장"
                    }
                    controller.isExecuteAvailable.value = false
                }
            }
        }
    }
}


class RoflInfoView : View() {
    private val controller: LoLClientController by inject()
    override val root = vbox {
        label {
            bind(controller.selectedLoLClientInfo)
        }
        label {
            bind(controller.selectedRoflName)
        }
        label {
            bind(controller.selectedRoflVersion)
        }
    }
}

class RoflErrorView : View() {
    override val root = vbox {

    }
}
