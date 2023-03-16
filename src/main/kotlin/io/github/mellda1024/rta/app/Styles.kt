package io.github.mellda1024.rta.app

import javafx.scene.text.FontSmoothingType
import tornadofx.*

class Styles : Stylesheet() {

    private val pretendardMedium = loadFont("/fonts/Pretendard-Medium.otf", 12.0)!!

    companion object {
        val fontedText by cssclass()
        val titleText by cssclass()
        val searchField by cssclass()
        val roflColumn by cssclass()
        val fontedButton by cssclass()
        val menuButton by cssclass()

        val menuButtonColor = c("#1F1F15")
        val menuButtonColorHovered = c("#293D52")
        val menuButtonFontColor = c("#FFFFFF")

        val white = c("#FFFFFF")
        val black = c("#000000")
    }

    init {
        fontedText and label {
            fontSize = 15.px
            fontFamily = pretendardMedium.family
            fontSmoothingType = FontSmoothingType.GRAY
        }

        titleText and label {
            fontSmoothingType = FontSmoothingType.GRAY
            fontFamily = pretendardMedium.family
            fontSize = 25.px
        }

        searchField and textField {

            prefWidth = 200.px
            prefHeight = 25.px


            accentColor = c("#007ACC")
            focusColor = c("#4089FF")

            backgroundColor = multi(
                c("#00AAFF"), white, white
            )

            backgroundInsets = multi(
                box(0.px),
                box(0.px, 0.px, 1.px, 0.px)
            )

            backgroundRadius += box(0.px)

            and(focused) {
                backgroundColor = multi(
                    black, white, white
                )
            }

            fontSize = 14.px
            fontFamily = pretendardMedium.family
            fontSmoothingType = FontSmoothingType.GRAY
        }

        roflColumn and tableColumn {
            fontSize = 13.px
            fontFamily = pretendardMedium.family
            fontSmoothingType = FontSmoothingType.GRAY
        }

        fontedButton and button {
            padding = box(3.0.px, 10.0.px)

            backgroundRadius = multi(box(7.px))
            borderRadius = multi(box(7.px))

            borderColor += box(black)
            backgroundColor += white

            fontSize = 14.px
            fontFamily = pretendardMedium.family
            fontSmoothingType = FontSmoothingType.GRAY

            and(hover) {
                borderColor += box(c("#6B6B6B"))
                backgroundColor += c("#D1D1D1")
            }
        }

        menuButton and button {
            fontSmoothingType = FontSmoothingType.GRAY
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