package ds.githubclient.ui.main.searchuser

import android.annotation.SuppressLint
import ds.githubclient.data.local.database.GithubDatabase
import ds.githubclient.data.remote.endpoint.GithubService
import ds.githubclient.data.remote.response.SearchResponse
import ds.githubclient.data.remote.response.UserResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchUserInteractor {

    fun listRecentSearch(onComplete: (List<UserResponse>?, Throwable?) -> Unit) {
        GithubDatabase.getInstance()
    }

    @SuppressLint("CheckResult")
    fun search(
        query: String,
        page: Int,
        perPage: Int,
        onComplete: (SearchResponse<List<UserResponse>?>?, Throwable?) -> Unit
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
        searchResponse: SearchResponse<List<UserResponse>>,
        userResponses: List<UserResponse>
    ): SearchResponse<List<UserResponse>?> {
        return SearchResponse(searchResponse.totalCount, searchResponse.incompleteResults, userResponses)
    }
}