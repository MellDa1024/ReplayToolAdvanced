package io.github.mellda1024.rta.controller

import io.github.mellda1024.rta.core.json.impl.ReplayToolDataManager
import io.github.mellda1024.rta.core.rofl.Rofl
import javafx.beans.property.SimpleBooleanProperty
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.collections.ObservableList
import tornadofx.*
import java.io.File

class RoflController : Controller() {

    val selectedRoflName = SimpleStringProperty("선택 : 없음")
    val selectedRofl = SimpleObjectProperty<Rofl?>(null)
    val hasRecentRofl = SimpleBooleanProperty(false)
    private val roflList: ObservableList<Rofl> = FXCollections.observableArrayList()
    val filteredList = SortedFilteredList(roflList, { true })
    private var folder: File? = null

    fun reload() {
        clear()
        load()
    }

    fun updateRecentRofl() {
        hasRecentRofl.value = ReplayToolDataManager.getRecentRofl() != null
    }


    fun updateFolder(file: File?) {
        if (file != null) {
            folder = file
            ReplayToolDataManager.updateRecentRoflFolder(file)
            reload()
        }
    }

    fun selectionUpdate(rofl: Rofl?) {
        selectedRoflName.value = "선택 : ${rofl?.name ?: "없음"}"
        selectedRofl.value = rofl
        if (rofl != null) {
            if (!roflValidCheck()) roflList.remove(rofl)
        }
    }

    fun roflValidCheck() : Boolean {
        if ((folder?.listFiles()?.contains(selectedRofl.value?.file))?.not() ?: return false) {
            selectionUpdate(null)
            return false
        }
        return true
    }

    init {
        load()
    }

    private fun register(file: File) {
        roflList.add(Rofl(file))
    }

    private fun clear() {
        roflList.clear()
    }

    private fun load() {
        folder?.listFiles()?.forEach {
            if (it.isFile) {
                if (it.name.endsWith(".rofl", true)) register(it)
            }
        }
    }
}