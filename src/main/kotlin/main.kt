import app.App
import di.AppComponent
import di.DaggerAppComponent

suspend fun main() {
    val component: AppComponent = DaggerAppComponent.create()

    val app = App()
    component.inject(app)

    app.run()
}