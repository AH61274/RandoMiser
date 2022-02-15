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

    private val _teammates: MutableLiveData<MutableList<Teammate>> = MutableLiveData(Teammate.makeList())
    val teammates: LiveData<MutableList<Teammate>> = _teammates
    var isDone = false

    private val _whosUp: MutableLiveData<Teammate> = MutableLiveData(_teammates.value?.random())
    val whosUp: LiveData<Teammate> = _whosUp

    private val _isUpdating: MutableLiveData<Boolean> = MutableLiveData(false)
    val isUpdating: LiveData<Boolean> = _isUpdating

    init {
        _teammates.value?.remove(_whosUp.value)
    }

    fun nextUp() {
        if (isDone) {
            restart()
        } else {
            _teammates.value?.let { teammates ->
                if (teammates.size > 0) {
                    val next = teammates[Random.nextInt(0, teammates.size)]
                    _whosUp.postValue(next)
                    onDismiss(next)
                } else {
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

    fun restart() {
        isDone = false
        _teammates.postValue(Teammate.makeList())
        nextUp()
    }

    fun onDismiss(teammate: Teammate) {
        val oldList = _teammates.value
        val newList = oldList?.filter { it != teammate }
        _teammates.value = newList?.toMutableList()
    }
}
