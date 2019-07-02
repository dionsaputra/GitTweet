package ds.mvpkotlin.di.component

import dagger.Component
import ds.gittweet.GitTweet
import ds.gittweet.data.local.database.GithubDatabase
import ds.gittweet.data.remote.endpoint.GithubService
import ds.gittweet.injection.module.NetModule
import ds.mvpkotlin.di.module.AppModule
import io.reactivex.disposables.CompositeDisposable

@Component(modules = [AppModule::class, NetModule::class])
interface AppComponent {
    fun inject(gitTweet: GitTweet)
    fun compositeDisposable(): CompositeDisposable
    fun githubDatabase(): GithubDatabase
    fun githubService(): GithubService
}