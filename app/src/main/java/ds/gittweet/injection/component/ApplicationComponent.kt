package ds.mvpkotlin.di.component

import dagger.Component
import ds.gittweet.GitTweet
import ds.gittweet.data.local.database.GithubDatabase
import ds.gittweet.ui.main.injection.MainComponent
import ds.mvpkotlin.di.module.ApplicationModule
import io.reactivex.disposables.CompositeDisposable

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {

    fun inject(gitTweet: GitTweet)

    fun compositeDisposable(): CompositeDisposable

    fun githubDatabase(): GithubDatabase
}