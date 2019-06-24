package ds.githubclient.ui.main.search.interactor

import android.annotation.SuppressLint
import ds.githubclient.data.network.GithubService
import ds.githubclient.data.network.model.SearchResponse
import ds.githubclient.data.network.model.User
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.BiFunction
import io.reactivex.schedulers.Schedulers

class SearchInteractor : SearchMvpInteractor {

    @SuppressLint("CheckResult")
    override fun listUsers(since: Long, perPage: Int, onComplete: (List<User>?, Throwable?) -> Unit) {
        GithubService.getEndpoint().listUser(since, perPage)
            .flatMap { users -> Observable.fromIterable(users) }
            .flatMap { user -> GithubService.getEndpoint().retrieveUser(user.login.orEmpty()).onErrorReturn { user } }
            .toList()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError {
                onComplete(null, it)
            }
            .subscribe { result, throwable -> onComplete(result, throwable) }
    }

    @SuppressLint("CheckResult")
    override fun filterUsers(
        query: String,
        page: Int,
        perPage: Int,
        onComplete: (SearchResponse<List<User>?>?, Throwable?) -> Unit
    ) {
        GithubService.getEndpoint().searchUser(query, page, perPage)
//            .flatMap { searchResponse ->
//                Single.zip(
//                    Single.just(searchResponse),
//                    Observable.fromIterable(searchResponse.items)
//                        .flatMap { user ->
//                            GithubService.getEndpoint().retrieveUser(user.login.orEmpty()).onErrorReturn { user }
//                        }.toList(),
//                    BiFunction<SearchResponse<List<User>>, List<User>, SearchResponse<List<User>?>> { t1, t2 ->
//                        createSearchResponse(
//                            t1,
//                            t2
//                        )
//                    }
//                ).toObservable()
//            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnError {
                onComplete(null, it)
            }
            .subscribe { result ->
                onComplete(result, null)
            }
    }

    private fun createSearchResponse(
        searchResponse: SearchResponse<List<User>>,
        users: List<User>
    ): SearchResponse<List<User>?> {
        return SearchResponse(searchResponse.totalCount, searchResponse.incompleteResults, users)
    }

}