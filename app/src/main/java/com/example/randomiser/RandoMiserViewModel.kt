package com.example.randomiser

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.randomiser.model.Platform
import com.example.randomiser.model.Teammate
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class RandoMiserViewModel @Inject constructor() : ViewModel() {

    val teammates = Teammate.makeList()
    var isDone = false

    private val _whosUp: MutableLiveData<Teammate> = MutableLiveData(teammates.random())
    val whosUp: LiveData<Teammate> = _whosUp

    init {
        teammates.remove(_whosUp.value)
    }

    fun nextUp() {
        if (isDone) {
            teammates.addAll(
                0,
                Teammate.makeList()
            )
            isDone = false
        } else {
            if (teammates.size > 0) {
                val next = teammates[Random.nextInt(0, teammates.size)]
                teammates.remove(next)
                _whosUp.postValue(next)
            } else {
                teammates.clear()
                _whosUp.postValue(
                    Teammate(
                        "Leadership\nProduct\n& Scrum",
                        Color(0xFF06237A),
                        Platform.Teamliness
                    )
                )
                isDone = true
            }
        }
    }
}
