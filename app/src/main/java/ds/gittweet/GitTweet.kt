package ds.gittweet

import android.app.Application
import ds.mvpkotlin.di.component.ApplicationComponent
import ds.mvpkotlin.di.component.DaggerApplicationComponent
import ds.mvpkotlin.di.module.ApplicationModule
import javax.inject.Inject

class GitTweet : Application() {

    companion object {
        var applicationComponent: ApplicationComponent? = null
    }

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }
}