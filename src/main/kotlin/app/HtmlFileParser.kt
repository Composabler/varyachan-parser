package app

import io.ktor.client.*
import io.ktor.client.request.*
import org.jsoup.Jsoup
import org.slf4j.Logger
import java.net.URL
import javax.inject.Inject

class HtmlFileParser @Inject constructor(
    private val client: HttpClient,
    private val logger: Logger,
) {

    suspend fun extractFileUrlsByTypeFromHtml(url: String): Map<String, List<String>> {
        return try {
            val response = client.get<String>(url)
            val baseUrl = URL(url).let { "${it.protocol}://${it.host}" }
            val document = Jsoup.parse(response, baseUrl)

            document.select("div.post-file a[target='_blank']")
                .mapNotNull {
                    val href = it.attr("abs:href")
                    if (href.startsWith(baseUrl)) {
                        it.parent().attr("data-type") to href
                    } else {
                        null
                    }
                }
                .groupBy({ it.first }, { it.second })
        } catch (e: Exception) {
            logger.error("Error extracting file URLs from HTML: ${e.message}")
            emptyMap()
        }
    }
}