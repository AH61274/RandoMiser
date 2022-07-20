package com.example.randomiser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomiser.model.Animal
import com.example.randomiser.model.Round
import com.example.randomiser.model.api.CatData
import com.example.randomiser.model.api.DogData
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HacktivityViewModel @Inject constructor() : ViewModel() {

    private val _contenders: MutableStateFlow<Pair<Animal, Animal>> =
        MutableStateFlow(Pair(Animal(), Animal()))
    val contenders: StateFlow<Pair<Animal, Animal>> = _contenders

    private val _names: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    private val names: StateFlow<List<String>> = _names

    private val _roundWinners: MutableStateFlow<List<Animal>> = MutableStateFlow(emptyList())
    val roundWinners: StateFlow<List<Animal>> = _roundWinners

    internal var currentRound: Round = Round.QUALIFIERS

    private var lastRoundWinners: MutableList<Animal> = mutableListOf()

    private val client = OkHttpClient.Builder().build()

    fun initViewModel() {
        getNames()
        viewModelScope.launch {
            names.collect {
                if (it.size == 2) {
                    getDog(it[0])
                    getCat(it[1])
                }
            }
        }
    }

    private fun getCat(name: String) = viewModelScope.launch(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://api.thecatapi.com/v1/images/search")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val newResponse =
                        response.body?.string()?.removePrefix("[")?.removeSuffix("]") ?: "{}"
                    val moshi = Moshi.Builder().build()
                    val catAdapter: JsonAdapter<CatData> = moshi.adapter(CatData::class.java)
                    val cat = catAdapter.fromJson(newResponse)
                    cat?.let { updateCat(it, name) }
                }
            }
        })
    }

    private fun getDog(name: String) = viewModelScope.launch(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://random.dog/woof.json")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val moshi = Moshi.Builder().build()
                    val dogAdapter: JsonAdapter<DogData> = moshi.adapter(DogData::class.java)
                    val dog = dogAdapter.fromJson(response.body?.string() ?: "")

                    if (dog?.url?.lowercase()?.endsWith("mp4") == true) {

                        // Dirty Hacky work around that I hate
                        val request = Request.Builder()
                            .url("https://random.dog/woof.json")
                            .build()
                        client.newCall(request).enqueue(object : Callback {
                            override fun onFailure(call: Call, e: IOException) {
                                //
                            }

                            override fun onResponse(call: Call, response: Response) {
                                if (response.isSuccessful) {
                                    val moshi = Moshi.Builder().build()
                                    val dogAdapter: JsonAdapter<DogData> = moshi.adapter(DogData::class.java)
                                    val dog = dogAdapter.fromJson(response.body?.string() ?: "")
                                    dog?.let { updateDog(it, name) }
                                }
                            }
                        })
                    } else {
                        dog?.let { updateDog(it, name) }
                    }
                }
            }
        })
    }

    private fun getNames() = viewModelScope.launch(Dispatchers.IO) {
        val request = Request.Builder()
            .url("https://randommer.io/pet-names")
            .post(
                FormBody.Builder()
                    .add("animal", "Dog")
                    .add("number", "2")
                    .build()
            )
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val nameDataListType =
                        Types.newParameterizedType(List::class.java, String::class.java)
                    val moshi = Moshi.Builder()
                        .add(KotlinJsonAdapterFactory())
                        .build()
                    val nameAdapter: JsonAdapter<List<String>> = moshi.adapter(nameDataListType)
                    val names = nameAdapter.fromJson(response.body?.string() ?: "[]")
                    names?.let { updateNames(it) }
                }
            }
        })
    }

    private fun updateNames(names: List<String>) = viewModelScope.launch {
        _names.emit(names)
    }

    private fun updateCat(cat: CatData, name: String) = viewModelScope.launch {
        _contenders.getAndUpdate {
            it.copy(
                first = Animal(
                    url = cat.url,
                    name = name
                )
            )
        }
    }

    private fun updateDog(dog: DogData, name: String) = viewModelScope.launch {
        _contenders.getAndUpdate {
            it.copy(
                second = Animal(
                    url = dog.url,
                    name = name
                )
            )
        }
    }

    internal fun selectWinner(roundWinner: Animal) = viewModelScope.launch {
        _roundWinners.updateAndGet {
            val newList = it.toMutableList()
            newList.add(roundWinner)
            newList
        }
        if (isRoundOver(Round.QUALIFIERS, _roundWinners.value.size)) {
            currentRound = Round.ROUND_1
            _roundWinners.emit(emptyList())
            contendersFromLast()
        } else if (isRoundOver(Round.ROUND_1, _roundWinners.value.size)) {
            currentRound = Round.ROUND_2
            _roundWinners.emit(emptyList())
            contendersFromLast()
        } else if (isRoundOver(Round.ROUND_2, _roundWinners.value.size)) {
            currentRound = Round.SEMI_FINALS
            _roundWinners.emit(emptyList())
            contendersFromLast()
        } else if (isRoundOver(Round.SEMI_FINALS, _roundWinners.value.size)) {
            currentRound = Round.FINAL
            _roundWinners.emit(emptyList())
            contendersFromLast()
        } else if (isRoundOver(Round.FINAL, _roundWinners.value.size)) {
            currentRound = Round.QUALIFIERS
            lastRoundWinners.removeAll { true }
            _roundWinners.emit(emptyList())
            getNames()
            // Restart
        } else {
            if (currentRound == Round.QUALIFIERS) {
                getNames()
            } else if (currentRound == Round.FINAL) {
                // Something Different
            } else {
                contendersFromLast()
            }
        }
    }

    private fun contendersFromLast() = viewModelScope.launch {
        val contenderA = lastRoundWinners.random()
        lastRoundWinners.remove(contenderA)
        val contenderB = lastRoundWinners.random()
        lastRoundWinners.remove(contenderB)
        _contenders.emit(Pair(contenderA, contenderB))
    }

    private fun isRoundOver(round: Round, winners: Int): Boolean {
        val isRoundOver = winners >= round.winners && currentRound == round
        if (isRoundOver && currentRound != Round.FINAL) {
            lastRoundWinners = _roundWinners.value.toMutableList()
        }
        return isRoundOver
    }
}
