package io.github.mellda1024.rta.app

import javafx.scene.text.FontWeight
import tornadofx.*

class Styles : Stylesheet() {

    private val pretendardMedium = loadFont("/fonts/Pretendard-Medium.ttf", 48.0)!!

    companion object {
        val menuButton by cssclass()
        val titleText by cssclass()

        val menuButtonColor = c("#1F1F15")
        val menuButtonColorHovered = c("#293D52")
        val menuButtonFontColor = c("#FFFFFF")
    }

    init {
        titleText and label {
            fontFamily = pretendardMedium.family
            fontSize = 25.px
            fontWeight = FontWeight.BOLD
        }

        menuButton and button {
            fontFamily = pretendardMedium.family
            fontSize = 25.px
            textFill = menuButtonFontColor
            prefWidth = 200.px

            backgroundColor += menuButtonColor
            padding = box(10.px)
            and(hover) {
                backgroundColor += menuButtonColorHovered
            }
        }
    }
}