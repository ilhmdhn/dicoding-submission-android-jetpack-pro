package com.ilhmdhn.movieapps.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.ilhmdhn.movieapps.data.source.local.LocalDataSource
import com.ilhmdhn.movieapps.data.source.local.entity.ListMovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.ListTvShowEntity
import com.ilhmdhn.movieapps.data.source.local.entity.MovieEntity
import com.ilhmdhn.movieapps.data.source.local.entity.TvShowEntity
import com.ilhmdhn.movieapps.data.source.remote.ApiResponse
import com.ilhmdhn.movieapps.data.source.remote.RemoteDataSource
import com.ilhmdhn.movieapps.data.source.remote.response.ResultItems
import com.ilhmdhn.movieapps.data.source.remote.response.ShowResponse
import com.ilhmdhn.movieapps.utils.AppExecutors
import com.ilhmdhn.movieapps.vo.Resource

class FakeMovieRepository(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutors: AppExecutors) :
    DataSource {

    override fun getAllMovie(): LiveData<Resource<PagedList<ListMovieEntity>>> {
        return object : NetworkBoundResource<PagedList<ListMovieEntity>,
                List<ResultItems>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<ListMovieEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(5)
                    .setPageSize(5)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllMovie(), config).build()
            }

            override fun shouldFetch(data: PagedList<ListMovieEntity>?): Boolean =
                data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ResultItems>>> =
                remoteDataSource.getMovies()

            override fun saveCallResult(data: List<ResultItems>) {
                val movieList = ArrayList<ListMovieEntity>()
                for (response in data) {
                    val movie = with(response) {
                        ListMovieEntity(id, posterPath, title, overview)
                    }
                    movieList.add(movie)
                }
                localDataSource.insertListMovie(movieList)
            }
        }.asLiveData()
    }

    override fun getAllTv(): LiveData<Resource<PagedList<ListTvShowEntity>>> {
        return object :
            NetworkBoundResource<PagedList<ListTvShowEntity>, List<ResultItems>>(appExecutors) {
            override fun loadFromDB(): LiveData<PagedList<ListTvShowEntity>> {
                val config = PagedList.Config.Builder()
                    .setEnablePlaceholders(false)
                    .setInitialLoadSizeHint(5)
                    .setPageSize(5)
                    .build()
                return LivePagedListBuilder(localDataSource.getAllTvShow(), config).build()
            }

            override fun shouldFetch(data: PagedList<ListTvShowEntity>?): Boolean =
                data.isNullOrEmpty()

            override fun createCall(): LiveData<ApiResponse<List<ResultItems>>> =
                remoteDataSource.getTv()

            override fun saveCallResult(data: List<ResultItems>) {
                val listTvShow = ArrayList<ListTvShowEntity>()
                data.forEach {
                    val tvShow = with(it) {
                        ListTvShowEntity(id,
                            posterPath,
                            name,
                            overview)
                    }
                    listTvShow.add(tvShow)
                }
                localDataSource.insertListTvShow(listTvShow)
            }

        }.asLiveData()
    }

    override fun getMovieDetail(id: Int): LiveData<Resource<MovieEntity>> {
        return object : NetworkBoundResource<MovieEntity, ShowResponse>(appExecutors){
            override fun loadFromDB(): LiveData<MovieEntity> =
                localDataSource.getDetailMovie(id)

            override fun shouldFetch(data: MovieEntity?): Boolean = data?.id == null

            override fun createCall(): LiveData<ApiResponse<ShowResponse>> =
                remoteDataSource.getMovieDetail(id)

            override fun saveCallResult(data: ShowResponse) {
                val detailMovieEntity = with(data) {
                    MovieEntity(
                        id,
                        posterPath,
                        title,
                        originalLanguage,
                        overview,
                        popularity,
                        voteAverage,
                        false
                    )
                }
                localDataSource.insertDetailMovie(detailMovieEntity)
            }
        }.asLiveData()
    }

    override fun getTvShowDetail(id: Int): LiveData<Resource<TvShowEntity>> {
        return object : NetworkBoundResource<TvShowEntity, ShowResponse>(appExecutors) {
            override fun loadFromDB(): LiveData<TvShowEntity> =
                localDataSource.getDetailTvShow(id)

            override fun shouldFetch(data: TvShowEntity?): Boolean =
                data?.id == null

            override fun createCall(): LiveData<ApiResponse<ShowResponse>> =
                remoteDataSource.getTvShowDetail(id)

            override fun saveCallResult(data: ShowResponse) {
                val detailTvShowEntity = with(data) {
                    TvShowEntity(
                        id,
                        posterPath,
                        name,
                        popularity,
                        originalLanguage,
                        overview,
                        voteAverage,
                        false
                    )
                }
                localDataSource.insertDetailTvShow(detailTvShowEntity)
            }
        }.asLiveData()
    }

    override fun setFavoriteMovie(movie: MovieEntity, state: Boolean) =
        localDataSource.setMovieFavorite(movie, state)

    override fun getFavoriteMovie(): LiveData<PagedList<MovieEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getMovieFavorite(), config).build()
    }

    override fun setFavoriteTvShow(tvShow: TvShowEntity, state: Boolean) =
        localDataSource.setTvShowFavorite(tvShow, state)

    override fun getTvFavorite(): LiveData<PagedList<TvShowEntity>> {
        val config = PagedList.Config.Builder()
            .setEnablePlaceholders(false)
            .setInitialLoadSizeHint(4)
            .setPageSize(4)
            .build()
        return LivePagedListBuilder(localDataSource.getTvShowFavorite(), config).build()
    }
}