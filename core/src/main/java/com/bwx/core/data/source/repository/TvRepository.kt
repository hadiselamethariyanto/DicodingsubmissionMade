package com.bwx.core.data.source.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.bwx.core.data.NetworkBoundResource
import com.bwx.core.data.Resource
import com.bwx.core.data.source.local.LocalDataSource
import com.bwx.core.data.source.local.entity.SeasonEntity
import com.bwx.core.data.source.local.entity.TvEntity
import com.bwx.core.data.source.remote.RemoteDataSource
import com.bwx.core.data.source.remote.network.ApiResponse
import com.bwx.core.data.source.remote.response.DetailTVResponse
import com.bwx.core.domain.model.Season
import com.bwx.core.domain.model.Tv
import com.bwx.core.domain.repository.ITvRepository
import com.bwx.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

@OptIn(ExperimentalPagingApi::class)
class TvRepository(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : ITvRepository {
    override fun getPagingPopularTv(): Flow<PagingData<TvEntity>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            remoteMediator = TvRemoteMediator(localDataSource, remoteDataSource)
        ) {
            localDataSource.getPagingSourceTv()
        }.flow
    }

    override fun getDetailTV(tvId: Int): Flow<Resource<TvEntity>> {
        return object : NetworkBoundResource<TvEntity, DetailTVResponse>() {

            override fun loadFromDB(): Flow<TvEntity> =
                localDataSource.getDetailTv(tvId)

            override fun shouldFetch(data: TvEntity?): Boolean =
                data != null && data.genres == ""

            override suspend fun createCall(): Flow<ApiResponse<DetailTVResponse>> =
                remoteDataSource.getDetailTv(tvId)

            override suspend fun saveCallResult(data: DetailTVResponse) {
                val genres = StringBuilder().append("")

                for (g in data.genres.indices) {
                    if (g < data.genres.size - 1) {
                        genres.append("${data.genres[g].name}, ")
                    } else {
                        genres.append(data.genres[g].name)
                    }
                }

//                val tv = TvEntity(
//                    tv_id = data.id,
//                    first_air_date = data.firstAirDate,
//                    overview = data.overview,
//                    poster_path = data.posterPath,
//                    backdrop_path = data.backdropPath,
//                    vote_average = data.voteAverage,
//                    name = data.name,
//                    number_of_seasons = data.numberOfSeasons,
//                    isFav = false,
//                    genres = genres.toString()
//                )
//
//                localDataSource.updateTv(tv)

                val seasonList = ArrayList<SeasonEntity>()
                for (x in data.seasons) {
                    val season =
                        SeasonEntity(
                            id = x.id,
                            name = x.name,
                            tv_id = tvId,
                            x.poster_path.toString()
                        )
                    seasonList.add(season)
                }
                localDataSource.insertSeasonTv(seasonList)
            }

        }.asFlow()
    }

    override fun getFavoriteTv(): Flow<List<Tv>> {
        return localDataSource.getFavoriteTv().map {
            DataMapper.mapTvEntitiesToDomain(it)
        }
    }

    override fun getSeasonTv(tv_id: Int): Flow<List<Season>> {
        return localDataSource.getSeasonTv(tv_id).map {
            DataMapper.mapSeasonEntitiesToDomain(it)
        }
    }

    override suspend fun setFavoriteTv(tv: Tv, state: Boolean) {
        val tvEntity = DataMapper.mapTvDomainToEntity(tv)
        localDataSource.setFavoriteTv(tvEntity, state)
    }

}