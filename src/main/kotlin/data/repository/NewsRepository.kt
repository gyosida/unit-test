package data.repository

import data.model.News

interface NewsRepository {

    fun getLastNews(): List<News>
}
