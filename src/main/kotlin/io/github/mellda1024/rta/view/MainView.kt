package io.github.mellda1024.rta.view

import io.github.mellda1024.rta.app.Styles.Companion.fontedButton
import io.github.mellda1024.rta.app.Styles.Companion.fontedText
import io.github.mellda1024.rta.app.Styles.Companion.menuButton
import io.github.mellda1024.rta.app.Styles.Companion.roflColumn
import io.github.mellda1024.rta.app.Styles.Companion.searchField
import io.github.mellda1024.rta.controller.RoflController
import io.github.mellda1024.rta.core.json.impl.ReplayToolDataManager
import io.github.mellda1024.rta.core.rofl.Rofl
import io.github.mellda1024.rta.util.tryOpenRofl
import javafx.beans.value.ObservableValue
import javafx.geometry.Pos
import javafx.scene.control.Label
import javafx.stage.Modality
import tornadofx.*

class MainView : View("ReplayToolAdvanced") {
    override val root = borderpane {
        top<LogoView>()
        left<LeftView>()
        right<RightView>()
    }
}

class RoflListView : View() {
    private val controller: RoflController by inject()

    override val root = vbox {
        paddingTop = 5.0
        paddingLeft = 10.0

        controller.updateFolder(ReplayToolDataManager.getRecentRoflFolder())

        @Suppress("Deprecation") //uh oh
        tableview(controller.filteredList) {

            placeholder = Label("Rofl 파일이 없습니다.").addClass(fontedText)
            prefWidth = 580.0
            readonlyColumn("파일 이름", Rofl::name) {
                addClass(roflColumn)
                fixedWidth(275.0)
                impl_setReorderable(false)
            }
            readonlyColumn("클라이언트 버전", Rofl::version) {
                addClass(roflColumn)
                fixedWidth(120.0)
                impl_setReorderable(false)
            }
            readonlyColumn("생성 일자", Rofl::createdDate) {
                addClass(roflColumn)
                fixedWidth(170.0)
                impl_setReorderable(false)
            }

            onUserSelect(clickCount = 1) {
                controller.selectionUpdate(it)
            }

            onUserSelect {
                tryOpenRofl(it)
            }
        }

        label {
            addClass(fontedText)
            paddingTop = 5.0
            bind(controller.selectedRoflName)
        }
    }
}

class LeftView : View() {
    override val root = borderpane {
        top<RoflListView>()
        bottom<RoflListControlView>()
    }
}

class RightView : View() {
    override val root = borderpane {
        paddingRight = 2.0
        top<ControlButtonView>()
        bottom<InfoButtonView>()
    }
}

class ControlButtonView : View() {
    private val controller: RoflController by inject()

    override val root = vbox(8) {
        paddingTop = 5.0

        alignment = Pos.TOP_RIGHT
        controller.updateRecentRofl()

        button("선택한 파일 열기") {
            addClass(menuButton)
            disableProperty().bind(controller.selectedRofl.isNull)
            setOnAction {
                tryOpenRofl(controller.selectedRofl.value)
            }
        }

        button("최근 파일 열기") {
            addClass(menuButton)
            disableProperty().bind(!controller.hasRecentRofl)
            setOnAction {
                tryOpenRofl(ReplayToolDataManager.getRecentRofl())
            }
        }

        button("폴더 설정") {
            addClass(menuButton)
            setOnAction {
                isDisable = true
                val folder = chooseDirectory("폴더를 선택해주세요.", ReplayToolDataManager.getRecentRoflFolder())
                if (folder != null) {
                    controller.updateFolder(folder)
                    controller.selectionUpdate(null)
                }
                isDisable = false
            }
        }
    }
}

class InfoButtonView : View() {
    override val root = vbox(alignment = Pos.TOP_RIGHT) {
        paddingBottom = 55.0
        button("기타 정보") {
            addClass(menuButton)
            setOnAction {
                InfoView().openWindow(resizable = false, modality = Modality.APPLICATION_MODAL)
            }
        }
    }
}

class LogoView : View() {
    override val root = vbox {
        paddingTop = 10.0
        imageview("/ReplayToolAdvancedLogo.png") {
            isPreserveRatio = true
            fitHeight = 100.0
        }
    }
}

class RoflListControlView : View() {
    override val root = borderpane {
        left<RefreshView>()
        right<SearchView>()
    }
}

class SearchView : View() {
    private val controller: RoflController by inject()

    override val root = vbox {
        paddingBottom = 5.0

        textfield {
            addClass(searchField)

            promptText = "검색"
            parent.requestFocus()

            textProperty().addListener { _: ObservableValue<out String>, _: String, newValue: String ->
                controller.filteredList.predicate = {
                    it.name.contains(newValue, true)
                            || it.version.contains(newValue, true)
                            || it.createdDate.contains(newValue, true)
                }
            }
        }
    }
}

class RefreshView : View() {
    private val controller: RoflController by inject()

    override val root = vbox {

        paddingLeft = 10.0
        paddingTop = 3.0

        button("새로 고침") {
            addClass(fontedButton)

            setOnAction {
                controller.reload()
                controller.roflValidCheck()
                runAsync {
                    isDisable = true
                    Thread.sleep(1000)
                    isDisable = false
                }
            }
        }
    }
}