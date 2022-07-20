package com.example.randomiser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.randomiser.model.api.CatData
import com.example.randomiser.model.api.DogData
import com.example.randomiser.model.api.NameResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HacktivityViewModel @Inject constructor() : ViewModel() {

    private val _cat: MutableStateFlow<CatData> = MutableStateFlow(CatData(emptyList(), "", "", -1, -1))
    val cat: StateFlow<CatData> = _cat

    private val _dog: MutableStateFlow<DogData> = MutableStateFlow(DogData(-1L, ""))
    val dog: StateFlow<DogData> = _dog

    private val _names: MutableStateFlow<List<String>> = MutableStateFlow(emptyList())
    val names: StateFlow<List<String>> = _names

    private val client = OkHttpClient.Builder().build()

    suspend fun initViewModel() {
        getNames()

        getDog()
        getCat()
    }

    private fun getCat() {
        val request = Request.Builder()
            .url("https://api.thecatapi.com/v1/images/search")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    viewModelScope.launch {
                        val newResponse =
                            response.body?.string()?.removePrefix("[")?.removeSuffix("]") ?: "{}"
                        val moshi = Moshi.Builder().build()
                        val catAdapter: JsonAdapter<CatData> = moshi.adapter(CatData::class.java)
                        val cat = catAdapter.fromJson(newResponse)
                        cat?.let { _cat.emit(it) }
                    }
                }
            }
        })
    }

    private fun getDog() {
        val request = Request.Builder()
            .url("https://random.dog/woof.json")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    viewModelScope.launch {
                        val moshi = Moshi.Builder().build()
                        val dogAdapter: JsonAdapter<DogData> = moshi.adapter(DogData::class.java)
                        val dog = dogAdapter.fromJson(response.body?.string() ?: "")
                        dog?.let { _dog.emit(it) }
                    }
                }
            }
        })
    }

    suspend fun getNames() = viewModelScope.launch {
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
                    viewModelScope.launch {
                        val nameDataList = Types.newParameterizedType(List::class.java, String::class.java)
                        val newString = "{${response.body?.string() ?: "[]"}}"
                        val moshi = Moshi.Builder()
                            .add(KotlinJsonAdapterFactory())
                            .build()
                        val nameAdapter: JsonAdapter<List<String>> =
                            moshi.adapter(nameDataList)
                        val names = nameAdapter.fromJson(response.body?.string() ?: "[]")
                        names?.let { _names.emit(it) }
                    }
                }
            }
        })
    }
}
