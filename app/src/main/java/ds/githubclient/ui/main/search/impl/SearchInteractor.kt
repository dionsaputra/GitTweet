package ds.githubclient.ui.main.search.impl

import ds.githubclient.data.network.Endpoint
import ds.githubclient.data.network.model.User
import ds.githubclient.ui.main.search.contract.SearchMvpInteractor
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class SearchInteractor : SearchMvpInteractor {

    var compositeDisposable: CompositeDisposable = CompositeDisposable()

    override fun getAllUsers(onComplete: (List<User>?, String?) -> Unit) {
        compositeDisposable.add(Endpoint.getEndpoint().listUser().flatMap { users ->
            Observable.fromIterable(users)
        }.flatMap { user ->
            Endpoint.getEndpoint().retrieveUser(user.login.orEmpty()).timeout(30, TimeUnit.SECONDS)
                .onErrorReturn { user }
//            Endpoint.getEndpoint().retrieveUser(user.login.orEmpty()).timeout(30, TimeUnit.SECONDS)
        }.toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .onErrorReturn { null }
            .subscribe { result, _ ->
                onComplete(result, null)
            })
    }

    override fun filterUsers(searchQuery: String, onComplete: (List<User>?, String?) -> Unit) {

    }
}