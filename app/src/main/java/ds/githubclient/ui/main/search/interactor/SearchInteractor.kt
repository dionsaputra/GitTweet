package ds.githubclient.ui.main.search.interactor

import android.annotation.SuppressLint
import ds.githubclient.data.network.GithubService
import ds.githubclient.data.network.model.User
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchInteractor : SearchMvpInteractor {

    @SuppressLint("CheckResult")
    override fun getAllUsers(sinceId: Long, paginationSize: Int, onComplete: (List<User>?, String?) -> Unit) {
        GithubService.getEndpoint().listUser(sinceId, paginationSize)
            .flatMap { users -> Observable.fromIterable(users) }.toList()
//            .flatMap { user ->
//                GithubService.getEndpoint().retrieveUser(user.login.orEmpty()).timeout(30, TimeUnit.SECONDS)
//                    .onErrorReturn { user }
//            }.toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .onErrorReturn { null }
            .subscribe { result, _ -> onComplete(result, null) }
    }

    override fun filterUsers(searchQuery: String, onComplete: (List<User>?, String?) -> Unit) {

    }
}