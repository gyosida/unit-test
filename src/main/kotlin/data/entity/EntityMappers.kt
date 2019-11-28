package data.entity

import data.model.News
import java.text.SimpleDateFormat
import java.util.*

fun mapElComercioNewsEntity(elComercioNewsEntity: ElComercioNewsEntity): NewsEntity = with(elComercioNewsEntity) {
    NewsEntity(
        _id,
        titulo,
        descripcion,
        fecha,
        NewsEntity.EL_COMERCIO_SOURCE
    )
}

fun mapLiveNewsEntity(liveNewsEntity: LiveNewsEntity): NewsEntity = with(liveNewsEntity) {
    NewsEntity(
        id,
        title,
        description,
        date,
        NewsEntity.LIVE_NEWS_SOURCE
    )
}

fun mapNewsEntity(newsEntity: NewsEntity): News = with(newsEntity) {
    News(
        id,
        title,
        description,
        parseDate(date)
    )
}

private fun parseDate(date: String): Date = with(SimpleDateFormat("dd-MM-yyyy hh:mm:ss")) {
    parse(date)
}
