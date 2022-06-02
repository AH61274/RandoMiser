package com.example.randomiser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.randomiser.model.api.CatData
import com.example.randomiser.model.api.DogData
import com.example.randomiser.model.api.NameResponse
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.*
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HacktivityViewModel @Inject constructor() : ViewModel() {

    private val _cat: MutableLiveData<CatData> = MutableLiveData()
    val cat: LiveData<CatData> = _cat

    private val _dog: MutableLiveData<DogData> = MutableLiveData()
    val dog: LiveData<DogData> = _dog

    private val _names: MutableLiveData<NameResponse> = MutableLiveData()
    val names: LiveData<NameResponse> = _names

    private val client = OkHttpClient.Builder().build()

    fun initViewModel() {
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
                    val newResponse =
                        response.body?.string()?.removePrefix("[")?.removeSuffix("]") ?: "{}"
                    val moshi = Moshi.Builder().build()
                    val catAdapter: JsonAdapter<CatData> = moshi.adapter(CatData::class.java)
                    val cat = catAdapter.fromJson(newResponse)
                    _cat.postValue(cat)
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
                    val moshi = Moshi.Builder().build()
                    val dogAdapter: JsonAdapter<DogData> = moshi.adapter(DogData::class.java)
                    val dog = dogAdapter.fromJson(response.body?.string() ?: "")
                    _dog.postValue(dog)
                }
            }
        })
    }

    fun getNames() {
        val request = Request.Builder()
            .url("https://randommer.io/pet-names")
            .post(
                FormBody.Builder()
                    .add("animal", "Dog")
                    .add("number", "20")
                    .build()
            )
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val newString = "{list: ${response.body?.string() ?: "[]"}}"
                    val moshi = Moshi.Builder().build()
                    val nameAdapter: JsonAdapter<NameResponse> =
                        moshi.adapter(NameResponse::class.java)
                    val names: NameResponse? = nameAdapter.fromJson(newString)
                    _names.postValue(names)
                }
            }
        })
    }
}
