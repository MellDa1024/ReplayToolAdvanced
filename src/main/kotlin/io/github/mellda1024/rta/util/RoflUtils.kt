package io.github.mellda1024.rta.util

import io.github.mellda1024.rta.core.rofl.Rofl
import io.github.mellda1024.rta.view.RoflError
import io.github.mellda1024.rta.view.RoflView
import javafx.scene.control.Alert
import javafx.scene.control.Button
import javafx.scene.control.ButtonType
import javafx.scene.image.Image
import javafx.stage.Modality
import javafx.stage.Stage

fun tryOpenRofl(rofl: Rofl?) {
    if (rofl == null) showRoflError(null)
    else if (rofl.version == "null") showRoflError(RoflError.VERSION_NOT_FOUND)
    else RoflView(rofl)
        .openWindow(resizable = false, modality = Modality.APPLICATION_MODAL)
}

fun showRoflError(error: RoflError?) {
    Alert(Alert.AlertType.ERROR).apply {
        (dialogPane.scene.window as Stage).icons.add(Image("/ReplayToolAdvancedIcon.png"))
        (dialogPane.lookupButton(ButtonType.OK) as Button).text = "확인"

        title = "ReplayToolAdvanced"
        contentText = when (error) {
            RoflError.VERSION_NOT_FOUND -> "Rofl 파일의 버전을 인식 할 수 없습니다."
            null -> "Rofl 파일을 다시 선택해주세요."
        }

        headerText = null
    }.show()
}