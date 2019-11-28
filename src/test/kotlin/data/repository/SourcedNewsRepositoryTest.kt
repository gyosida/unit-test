package data.repository

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import data.datasource.local.NewsLocalDataSource
import data.datasource.remote.ElComercioAPI
import data.datasource.remote.LiveNewsAPI
import data.datasource.remote.exceptions.NoInternetConnectionException
import data.entity.*
import org.junit.Test
import kotlin.test.assertEquals

class SourcedNewsRepositoryTest {

    private val elComercioAPI = mock<ElComercioAPI>()
    private val liveNewsAPI = mock<LiveNewsAPI>()
    private val newsLocalDataSource = mock<NewsLocalDataSource>()
    private val repository = SourcedNewsRepository(
        elComercioAPI, liveNewsAPI, newsLocalDataSource
    )

    @Test
    fun getLastNewsShouldReturnNewsFromMultipleSourcesWhenHaveAccessToInternet() {
        // given
        val elComercioNews = buildElComercioNews()
        val liveNews = buildLiveNews()
        val allNewsEntities = buildNewsEntities(elComercioNews, liveNews)
        whenever(elComercioAPI.getLastElComercioNews()).thenReturn(elComercioNews)
        whenever(liveNewsAPI.getLastLiveNews()).thenReturn(liveNews)
        whenever(newsLocalDataSource.getNewsEntities()).thenReturn(allNewsEntities)

        // when
        val lastNews = repository.getLastNews()

        // then
        verify(newsLocalDataSource).saveNewsEntities(allNewsEntities)
        assertEquals(allNewsEntities.map(::mapNewsEntity), lastNews)
    }

    @Test
    fun getLastNewsShouldReturnStoredNewsWhenInternetIsNotAvailable() {
        // given
        val storedNewsEntities = buildLiveNews().map(::mapLiveNewsEntity)
        whenever(elComercioAPI.getLastElComercioNews()).thenThrow(NoInternetConnectionException)
        whenever(liveNewsAPI.getLastLiveNews()).thenThrow(NoInternetConnectionException)
        whenever(newsLocalDataSource.getNewsEntitiesBySource(NewsEntity.LIVE_NEWS_SOURCE))
            .thenReturn(storedNewsEntities)

        // when
        val lastNews = repository.getLastNews()

        // then
        assertEquals(lastNews, storedNewsEntities.map(::mapNewsEntity))
    }

    private fun buildNewsEntities(
        elComercioNews: List<ElComercioNewsEntity>,
        liveNews: List<LiveNewsEntity>
    ): List<NewsEntity> = (elComercioNews.map(::mapElComercioNewsEntity) + liveNews.map(::mapLiveNewsEntity))

    private fun buildElComercioNews(): List<ElComercioNewsEntity> = listOf(
        ElComercioNewsEntity(
            "id1",
            "titulo1",
            "descripcion1",
            "22-11-19 19:10:00",
            1,
            "Politica"
        ),
        ElComercioNewsEntity(
            "id2",
            "titulo2",
            "descripcion2",
            "22-11-19 19:11:00",
            2,
            "Politica"
        )
    )

    private fun buildLiveNews(): List<LiveNewsEntity> = listOf(
        LiveNewsEntity(
            "id1",
            "title1",
            "description1",
            "22-11-19 19:10:21"
        ),
        LiveNewsEntity(
            "id2",
            "title2",
            "description2",
            "22-11-19 19:10:21"
        )
    )
}
