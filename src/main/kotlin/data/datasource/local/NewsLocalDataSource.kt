package data.datasource.local

import data.entity.NewsEntity

interface NewsLocalDataSource {

    fun saveNewsEntities(newsEntities: List<NewsEntity>)
    fun getNewsEntities(): List<NewsEntity>
    fun getNewsEntitiesBySource(source: Int): List<NewsEntity>
}
