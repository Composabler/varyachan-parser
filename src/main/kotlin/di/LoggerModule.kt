package di

import dagger.Module
import dagger.Provides
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import javax.inject.Singleton

@Module
class LoggerModule {

    @Provides
    @Singleton
    fun provideLogger(): Logger {
        return LoggerFactory.getLogger("AppLogger")
    }
}