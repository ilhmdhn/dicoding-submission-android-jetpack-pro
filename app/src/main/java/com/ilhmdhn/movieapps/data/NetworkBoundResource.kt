package com.ilhmdhn.movieapps.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.ilhmdhn.movieapps.data.source.remote.ApiResponse
import com.ilhmdhn.movieapps.data.source.remote.StatusResponse
import com.ilhmdhn.movieapps.utils.AppExecutors
import com.ilhmdhn.movieapps.vo.Resource

abstract class NetworkBoundResource<ResultType, RequestType>(private val mAppExecutors: AppExecutors) {

    private val result = MediatorLiveData<Resource<ResultType>>()

    init {
        result.value = Resource.loading(null)

        @Suppress("LeakingThis")
        val dbSource = loadFromDB()

        result.addSource(dbSource) { data ->
            result.removeSource(dbSource)
            if (shouldFetch(data)) {
                fetchFromNetwork(dbSource)
            } else {
                result.addSource(dbSource) { newData ->
                    result.value = Resource.success(newData)
                }
            }
        }
    }

    protected open fun onFetchFailed() {}

    protected abstract fun loadFromDB(): LiveData<ResultType>

    protected abstract fun shouldFetch(data: ResultType?): Boolean

    protected abstract fun createCall(): LiveData<ApiResponse<RequestType>>

    protected abstract fun saveCallResult(data: RequestType)

    private fun fetchFromNetwork(dbSource: LiveData<ResultType>) {
        val apiResponse = createCall()
        result.addSource(dbSource) {
            result.value = Resource.loading(it)
        }
        result.addSource(apiResponse) { response ->
            result.removeSource(apiResponse)
            result.removeSource(dbSource)
            when (response.status) {
                StatusResponse.SUCCES ->
                    mAppExecutors.diskIO().execute {
                        saveCallResult(response.body)
                        mAppExecutors.mainThread().execute {
                            result.addSource(loadFromDB()) {
                                result.value = Resource.success(it)
                            }
                        }
                    }
                StatusResponse.EMPTY -> mAppExecutors.mainThread().execute {
                    result.addSource(loadFromDB()) {
                        result.value = Resource.success(it)
                    }
                }
                StatusResponse.ERROR -> {
                    onFetchFailed()
                    result.addSource(dbSource) {
                        result.value = Resource.error("${response.message}", it)
                    }
                }
            }
        }
    }

    fun asLiveData(): LiveData<Resource<ResultType>> = result

}