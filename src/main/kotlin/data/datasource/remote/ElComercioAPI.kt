package data.datasource.remote

import data.datasource.remote.exceptions.NoInternetConnectionException
import data.entity.ElComercioNewsEntity

interface ElComercioAPI {

    @Throws(NoInternetConnectionException::class)
    fun getLastElComercioNews(): List<ElComercioNewsEntity>
}
