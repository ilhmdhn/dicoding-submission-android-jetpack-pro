package com.ilhmdhn.movieapps.data.source.remote

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ilhmdhn.movieapps.BuildConfig
import com.ilhmdhn.movieapps.data.source.remote.response.ResponseResult
import com.ilhmdhn.movieapps.data.source.remote.response.ResultItems
import com.ilhmdhn.movieapps.data.source.remote.response.ShowResponse
import com.ilhmdhn.movieapps.utils.ApiConfig
import com.ilhmdhn.movieapps.utils.EspressoIdlingResource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource {

    private var apiConfig = ApiConfig
    private var apiKey = BuildConfig.API_KEY

    companion object {
        private const val TAG = "DataSource"

        @Volatile
        private var instance: RemoteDataSource? = null

        fun getInstance(): RemoteDataSource =
            instance ?: synchronized(this) {
                instance ?: RemoteDataSource()
            }
    }

    fun getMovies(): LiveData<ApiResponse<List<ResultItems>>> {
        EspressoIdlingResource.increment()
        val resultMovies = MutableLiveData<ApiResponse<List<ResultItems>>>()
        apiConfig.getApiService().getMovie(apiKey)
            .enqueue(object : Callback<ResponseResult> {
                override fun onResponse(
                    call: Call<ResponseResult>,
                    response: Response<ResponseResult>
                ) {
                    if (response.isSuccessful)
                        resultMovies.postValue(
                            ApiResponse.succes(
                                response.body()
                                    ?.results as List<ResultItems>
                            )
                        )
                    else {
                        ApiResponse.empty(response.message(), response.body())
                        Log.e(TAG, response.message())
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<ResponseResult>, t: Throwable) {
                    ApiResponse.error("${t.message}", null)
                    Log.e(TAG, "${t.message}")
                    EspressoIdlingResource.decrement()
                }

            })
        return resultMovies
    }

    fun getTv(): LiveData<ApiResponse<List<ResultItems>>> {
        EspressoIdlingResource.increment()
        val resultTvShow = MutableLiveData<ApiResponse<List<ResultItems>>>()
        apiConfig.getApiService().getTv(apiKey)
            .enqueue(object : Callback<ResponseResult> {
                override fun onResponse(
                    call: Call<ResponseResult>,
                    response: Response<ResponseResult>
                ) {
                    if (response.isSuccessful)
                        resultTvShow.postValue(
                            ApiResponse.succes(
                                response.body()
                                    ?.results as List<ResultItems>
                            )
                        )
                    else {
                        ApiResponse.empty(response.message(), response.errorBody())
                        Log.e(TAG, response.message())
                    }
                    EspressoIdlingResource.decrement()
                }

                override fun onFailure(call: Call<ResponseResult>, t: Throwable) {
                    ApiResponse.error(t.message.toString(), null)
                    Log.d(TAG, t.message.toString())
                    EspressoIdlingResource.decrement()
                }

            })
        return resultTvShow
    }

    fun getMovieDetail(id: Int): MutableLiveData<ApiResponse<ShowResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailMovie = MutableLiveData<ApiResponse<ShowResponse>>()
        val client = apiConfig.getApiService().getMovieDetail(id, apiKey)
        client.enqueue(object : Callback<ShowResponse> {
            override fun onResponse(
                call: Call<ShowResponse>,
                response: Response<ShowResponse>
            ) {
                if (response.isSuccessful)
                    resultDetailMovie.postValue(ApiResponse.succes(response.body() as ShowResponse))
                else {
                    ApiResponse.empty(response.message(), response.errorBody())
                    Log.e(TAG, response.message())
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<ShowResponse>, t: Throwable) {
                ApiResponse.error("${t.message}", null)
                Log.e(TAG, t.message.toString())
                EspressoIdlingResource.decrement()
            }
        })
        return resultDetailMovie
    }

    fun getTvShowDetail(id: Int): MutableLiveData<ApiResponse<ShowResponse>> {
        EspressoIdlingResource.increment()
        val resultDetailTvShow = MutableLiveData<ApiResponse<ShowResponse>>()
        val client = apiConfig.getApiService().getTvShowDetail(id, apiKey)
        client.enqueue(object : Callback<ShowResponse> {
            override fun onResponse(
                call: Call<ShowResponse>,
                response: Response<ShowResponse>
            ) {
                if (response.isSuccessful)
                    resultDetailTvShow.postValue(ApiResponse.succes(response.body() as ShowResponse))
                else {
                    ApiResponse.empty(response.message(), response.errorBody())
                    Log.e(TAG, response.message())
                }
                EspressoIdlingResource.decrement()
            }

            override fun onFailure(call: Call<ShowResponse>, t: Throwable) {
                ApiResponse.error(t.message.toString(), null)
                Log.e(TAG, "${t.message}")
                EspressoIdlingResource.decrement()
            }
        })
        return resultDetailTvShow
    }
}