package ds.gittweet.ui.main.searchuser

import ds.gittweet.data.local.database.GithubDatabase
import ds.gittweet.data.local.entity.UserEntity
import ds.gittweet.data.remote.endpoint.GithubService
import ds.gittweet.data.remote.response.SearchResponse
import ds.gittweet.data.remote.response.UserResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable
import javax.inject.Inject

class SearchUserInteractor @Inject constructor(private var githubDatabase: GithubDatabase) {

    fun search(query: String, page: Int, perPage: Int): Observable<SearchResponse<List<UserResponse>?>> {
        return GithubService.getEndpoint().searchUser(query, page, perPage)
    }

    fun insertRecent(userResponse: UserResponse): Completable {
        val searchRecentUser = UserEntity(
            login = userResponse.login.orEmpty(),
            avatarUrl = userResponse.avatarUrl,
            url = userResponse.url
        )

        return githubDatabase.searchRecentUserDao().insert(searchRecentUser)
    }

    fun listLocalUser(): Observable<List<UserEntity>> {
        return githubDatabase.searchRecentUserDao().list()
    }

    private fun createSearchResponse(
        searchResponse: SearchResponse<List<UserResponse>>,
        userResponses: List<UserResponse>
    ): SearchResponse<List<UserResponse>?> {
        return SearchResponse(searchResponse.totalCount, searchResponse.incompleteResults, userResponses)
    }
}