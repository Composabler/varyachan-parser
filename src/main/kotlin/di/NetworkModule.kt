package di

import dagger.Module
import dagger.Provides
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.*
import javax.inject.Singleton

@Module
class NetworkModule {

        @Provides
        @Singleton
        fun provideHttpClient(): HttpClient {
            return HttpClient(CIO) {
                install(HttpTimeout) {
                    requestTimeoutMillis = 5 * 60 * 1000
                    connectTimeoutMillis = 1 * 60 * 1000
                    socketTimeoutMillis = 1 * 60 * 1000
                }
            }
        }
}