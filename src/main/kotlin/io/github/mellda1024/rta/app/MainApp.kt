package io.github.mellda1024.rta.app

import io.github.mellda1024.rta.view.MainView
import javafx.scene.image.Image
import javafx.stage.Stage
import tornadofx.*

class MainApp : App(MainView::class, Styles::class) {
    override fun start(stage: Stage) {
        with(stage) {
            isResizable = false
            addStageIcon(Image("/ReplayToolAdvancedIcon.png"))
            width = 850.0
            height = 610.0
        }
        super.start(stage)
    }
}