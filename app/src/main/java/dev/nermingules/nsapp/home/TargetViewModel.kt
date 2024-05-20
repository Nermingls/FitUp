package dev.nermingules.nsapp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData


data class BodyTarget(
    val name: String,
)

class TargetViewModel : ViewModel() {

    fun getTargetList() = liveData {
        val localTargetList = listOf(
            "abductors",
            "abs",
            "adductors",
            "biceps",
            "calves",
            "cardiovascular system",
            "delts",
            "forearms",
            "glutes",
            "hamstrings",
            "lats",
            "levator scapulae",
            "pectorals",
            "quads",
            "serratus anterior",
            "spine",
            "traps",
            "triceps",
            "upper back"
        ).map { BodyTarget(it) }
        emit(localTargetList)
    }
}
