package io.github.mellda1024.rta.view

import io.github.mellda1024.rta.app.Styles.Companion.fontedButton
import io.github.mellda1024.rta.app.Styles.Companion.fontedText
import io.github.mellda1024.rta.controller.LoLClientController
import io.github.mellda1024.rta.controller.RoflController
import io.github.mellda1024.rta.core.json.impl.ReplayToolDataManager
import io.github.mellda1024.rta.core.rofl.Rofl
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.scene.text.TextAlignment
import javafx.stage.FileChooser.ExtensionFilter
import tornadofx.*
import java.io.File

class RoflView(rofl: Rofl) : View("ReplayToolAdvanced") {
    private val loLClientController: LoLClientController by inject()

    override val root = borderpane {
        prefWidth = 700.0
        loLClientController.init(rofl)

        top<RoflInfoView>()
        bottom<RoflControlView>()
    }
}

class RoflControlView : View() {
    private val loLClientController: LoLClientController by inject()
    private val roflController: RoflController by inject()

    override val root = hbox(20) {

        paddingTop = 5.0
        paddingBottom = 5.0
        paddingBottom = 5.0

        val extFilter = arrayOf(
            ExtensionFilter("League of Legends.exe", "League of Legends.exe")
        )

        alignment = Pos.CENTER
        button {
            addClass(fontedButton)
            textAlignment = TextAlignment.CENTER
            text = "클라이언트 경로 설정"
            setOnAction {
                isDisable = true
                val lolClient = chooseFile(
                    "클라이언트를 선택해주세요.",
                    filters = extFilter,
                    initialDirectory = loLClientController.selectedLoLClient.value?.parentFile
                )
                if (lolClient.isNotEmpty()) {
                    loLClientController.update(lolClient[0])
                }
                isDisable = false
            }
        }
        button {
            addClass(fontedButton)
            textProperty().bind(loLClientController.executeButton)
            disableProperty().bind(loLClientController.isExecuteAvailable)
            setOnAction {
                runAsync {
                    loLClientController.isExecuteAvailable.value = true
                    Platform.runLater {
                        loLClientController.executeButton.value = "실행중입니다..."
                    }
                    loLClientController.selectedRofl.value.execute(
                        loLClientController.selectedLoLClient.value!!
                    )
                    ReplayToolDataManager.updateRecentRoflFile(loLClientController.selectedRofl.value.file)
                    roflController.updateRecentRofl()
                    Thread.sleep(1000)
                    Platform.runLater {
                        loLClientController.executeButton.value = "리플레이 실행"
                    }
                    loLClientController.isExecuteAvailable.value = false
                }
            }
        }
        button {
            addClass(fontedButton)
            textProperty().bind(loLClientController.replayBatButton)
            disableProperty().bind(loLClientController.isExecuteAvailable)
            setOnAction {
                runAsync {
                    loLClientController.isExecuteAvailable.value = true
                    val result = loLClientController.selectedRofl.value.saveBatchFile(
                        loLClientController.selectedLoLClient.value!!,
                        File(".")
                    )
                    Platform.runLater {
                        loLClientController.replayBatButton.value = if (result) "저장하였습니다."
                        else "저장에 실패하였습니다."
                    }
                    ReplayToolDataManager.updateRecentRoflFile(loLClientController.selectedRofl.value.file)
                    roflController.updateRecentRofl()
                    Thread.sleep(1000)
                    Platform.runLater {
                        loLClientController.replayBatButton.value = "Replay.bat으로 저장"
                    }
                    loLClientController.isExecuteAvailable.value = false
                }
            }
        }
    }
}


class RoflInfoView : View() {
    private val loLClientController: LoLClientController by inject()
    override val root = vbox {

        paddingLeft = 10.0
        paddingTop = 10.0

        label {
            addClass(fontedText)
            bind(loLClientController.selectedLoLClientInfo)
        }
        label {
            addClass(fontedText)
            bind(loLClientController.selectedRoflName)
        }
        label {
            addClass(fontedText)
            bind(loLClientController.selectedRoflVersion)
        }
    }
}