package app
import org.slf4j.Logger
import javax.inject.Inject

class App {

    @Inject
    lateinit var webContentDownloader: WebContentDownloader

    @Inject
    lateinit var logger: Logger

    suspend fun run() {
        logger.info("Enter varyachan thread link:")
        val url = readlnOrNull() ?: return
        webContentDownloader.downloadFromUrl(url)
    }
}