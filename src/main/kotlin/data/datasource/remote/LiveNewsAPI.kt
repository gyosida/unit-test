package data.datasource.remote

import data.datasource.remote.exceptions.NoInternetConnectionException
import data.entity.LiveNewsEntity

interface LiveNewsAPI {

    @Throws(NoInternetConnectionException::class)
    fun getLastLiveNews(): List<LiveNewsEntity>
}
