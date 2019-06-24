package ds.githubclient.ui.main.searchuser

import android.annotation.SuppressLint
import ds.githubclient.data.network.GithubService
import ds.githubclient.data.network.model.SearchResponse
import ds.githubclient.data.network.model.User
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchUserInteractor {

    fun listRecentSearch(onComplete: (List<User>?, Throwable?) -> Unit) {

    }

    @SuppressLint("CheckResult")
    fun search(
        query: String,
        page: Int,
        perPage: Int,
        onComplete: (SearchResponse<List<User>?>?, Throwable?) -> Unit
    ) {
        GithubService.getEndpoint().searchUser(query, page, perPage)
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