package ds.gittweet

import android.app.Application
import ds.mvpkotlin.di.component.AppComponent
import ds.mvpkotlin.di.component.DaggerAppComponent
import ds.mvpkotlin.di.module.AppModule

class GitTweet : Application() {

    companion object {
        var appComponent: AppComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
    }
}