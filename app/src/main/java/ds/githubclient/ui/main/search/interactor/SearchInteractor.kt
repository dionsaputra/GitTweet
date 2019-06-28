package ds.githubclient.ui.main.search.interactor

import android.annotation.SuppressLint
import ds.githubclient.data.remote.endpoint.GithubService
import ds.githubclient.data.remote.response.SearchResponse
import ds.githubclient.data.remote.response.UserResponse
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchInteractor : SearchMvpInteractor {

    @SuppressLint("CheckResult")
    override fun listUsers(since: Long, perPage: Int, onComplete: (List<UserResponse>?, Throwable?) -> Unit) {
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
        onComplete: (SearchResponse<List<UserResponse>?>?, Throwable?) -> Unit
    ) {
        GithubService.getEndpoint().searchUser(query, page, perPage)
//            .flatMap { searchResponse ->
//                Single.zip(
//                    Single.just(searchResponse),
//                    Observable.fromIterable(searchResponse.items)
//                        .flatMap { user ->
//                            GithubService.getEndpoint().retrieveUser(user.login.orEmpty()).onErrorReturn { user }
//                        }.toList(),
//                    BiFunction<SearchResponse<List<UserResponse>>, List<UserResponse>, SearchResponse<List<UserResponse>?>> { t1, t2 ->
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
        searchResponse: SearchResponse<List<UserResponse>>,
        userResponses: List<UserResponse>
    ): SearchResponse<List<UserResponse>?> {
        return SearchResponse(searchResponse.totalCount, searchResponse.incompleteResults, userResponses)
    }

}