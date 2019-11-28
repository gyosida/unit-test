package data.repository

import data.datasource.local.NewsLocalDataSource
import data.datasource.remote.ElComercioAPI
import data.datasource.remote.LiveNewsAPI
import data.datasource.remote.exceptions.NoInternetConnectionException
import data.entity.NewsEntity
import data.entity.mapNewsEntity
import data.entity.mapLiveNewsEntity
import data.entity.mapElComercioNewsEntity
import data.model.News

class SourcedNewsRepository(
    private val elComercioAPI: ElComercioAPI,
    private val liveNewsAPI: LiveNewsAPI,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {

    override fun getLastNews(): List<News> =
        try {
            val elComercioNews = elComercioAPI.getLastElComercioNews()
            val liveNews = liveNewsAPI.getLastLiveNews()
            val newsEntities = elComercioNews.map(::mapElComercioNewsEntity) + liveNews.map(::mapLiveNewsEntity)
            newsLocalDataSource.saveNewsEntities(newsEntities)
            newsLocalDataSource.getNewsEntities().map(::mapNewsEntity)
        } catch (e: NoInternetConnectionException) {
            newsLocalDataSource.getNewsEntitiesBySource(NewsEntity.LIVE_NEWS_SOURCE)
                .map(::mapNewsEntity)
        }
}
