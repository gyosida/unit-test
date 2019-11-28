package data.entity

data class NewsEntity(
    val id: String,
    val title: String,
    val description: String,
    val date: String,
    val source: Int
) {

    companion object {
        const val EL_COMERCIO_SOURCE = 1
        const val LIVE_NEWS_SOURCE = 2
    }
}
