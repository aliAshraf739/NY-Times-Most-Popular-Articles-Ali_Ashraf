package com.ali.ashraf.mvvm.data.repository

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.builbee.app.util.Internet
import com.ali.ashraf.mvvm.R
import com.ali.ashraf.mvvm.data.api.RestApi
import com.ali.ashraf.mvvm.data.model.article.GetArticlesResponse
import com.ali.ashraf.mvvm.utils.Constants
import com.ali.ashraf.mvvm.utils.Resource
import com.ali.ashraf.mvvm.utils.Status
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class MainRepository(val application: Application) {

    private val TAG = "MainRepository"
    private val volumesResponseLiveData: MutableLiveData<Resource<GetArticlesResponse>> = MutableLiveData()

    fun getArticlesFromServer(){
        volumesResponseLiveData.postValue(Resource(Status.LOADING, null, ""))
        val call: Call<GetArticlesResponse>? = RestApi.getService()?.getMostPopularArticles(Constants.API_KEY)
        call?.enqueue(object : Callback<GetArticlesResponse> {
            override fun onResponse(call: Call<GetArticlesResponse>, response: Response<GetArticlesResponse>) {
                if (response.isSuccessful) {
                    Log.e(TAG, "List Items: "+ response.body().toString())
                    if(response.body()!=null){
                        if(response.body()?.results?.isNotEmpty()==true){
                            volumesResponseLiveData.postValue(Resource(Status.SUCCESS, response.body(), ""))
                        }else{
                            volumesResponseLiveData.postValue(Resource(Status.NO_DATA, null, ""))
                        }
                    }else{
                        Log.e(TAG, "onResponse: ")
                        volumesResponseLiveData.postValue(Resource(Status.ERROR, null, application.resources.getString(R.string.something_went_wrong)))
                    }
                } else {
                    Log.e(TAG, "onResponse: ")
                    volumesResponseLiveData.postValue(Resource(Status.ERROR, null, application.resources.getString(R.string.something_went_wrong)))
                }
            }
            override fun onFailure(
                call: Call<GetArticlesResponse>,
                t: Throwable
            ) {
                Log.e(TAG, "onFailure: ${t.message}")
                volumesResponseLiveData.postValue(Resource(Status.ERROR, null, application.resources.getString(R.string.something_went_wrong)))
            }
        })
    }

    fun getArticlesLiveData(): LiveData<Resource<GetArticlesResponse>> {
        return volumesResponseLiveData
    }
}