package app

import io.ktor.client.*
import io.ktor.client.request.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.slf4j.Logger
import java.io.File
import javax.inject.Inject

private const val MAX_ATTEMPTS = 10
private const val DIRECTORY_PATH = "downloaded"

class WebContentDownloader @Inject constructor(
    private val client: HttpClient,
    private val urlExtractor: HtmlFileParser,
    private val logger: Logger
) {

    suspend fun downloadFromUrl(url: String) {
        client.use {
            val typedFileUrls = urlExtractor.extractFileUrlsByTypeFromHtml(url)
            val directory = File(DIRECTORY_PATH)

            if (!directory.exists()) directory.mkdirs()

            coroutineScope {
                typedFileUrls.forEach { (dataType, fileUrls) ->
                    logger.info("Downloading ${fileUrls.size} $dataType")
                    fileUrls.forEach { fileUrl ->
                        launch {
                            downloadFile(dataType, fileUrl, directory)
                        }
                    }
                }
            }
        }
    }

    private suspend fun downloadFile(dataType: String, url: String, directory: File) {
        val fileName = url.substringAfterLast("/")
        val destinationDirectory = File(directory, dataType)

        if (!destinationDirectory.exists()) destinationDirectory.mkdirs()

        val destinationFile = File(destinationDirectory, fileName)
        downloadFile(url, destinationFile)
    }

    private suspend fun downloadFile(url: String, destinationFile: File) {
        var attempts = 0

        while (attempts < MAX_ATTEMPTS) {
            try {
                logger.info("Downloading: $url")
                val bytes = client.get<ByteArray>(url)
                destinationFile.writeBytes(bytes)
                logger.info("Downloaded: ${destinationFile.absolutePath}")
                return
            } catch (e: Exception) {
                attempts++
                logger.error("Error downloading file, attempts: $attempts: ${e.message}")
            }
        }

        logger.error("Failed to download file after $MAX_ATTEMPTS attempts")
    }
}