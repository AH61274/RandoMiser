package com.example.randomiser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.randomiser.model.api.CatData
import com.example.randomiser.model.api.DogData
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

    private val catClient = OkHttpClient.Builder().build()
    private val dogClient = OkHttpClient.Builder().build()

    private val moshi = Moshi.Builder().build()
    private val catAdapter: JsonAdapter<CatData> = moshi.adapter(CatData::class.java)
    private val dogAdapter: JsonAdapter<DogData> = moshi.adapter(DogData::class.java)

    fun getCat() {
        val request = Request.Builder()
            .url("https://api.thecatapi.com/v1/images/search")
            .build()
        catClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val cat = catAdapter.fromJson(response.body?.string() ?: "")
                    _cat.postValue(cat)
                }
            }
        })
    }

    fun getDog() {
        val request = Request.Builder()
            .url("https://random.dog/woof.json")
            .build()
        catClient.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                //
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val dog = dogAdapter.fromJson(response.body?.string() ?: "")
                    _dog.postValue(dog)
                }
            }
        })
    }
}
