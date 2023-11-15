package di

import app.App
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class, LoggerModule::class])
interface AppComponent {
    fun inject(app: App)
}