package ds.gittweet.ui.main.searchuser

import ds.gittweet.data.local.database.GithubDatabase
import ds.gittweet.data.local.entity.SearchRecentUser
import ds.gittweet.data.remote.endpoint.GithubService
import ds.gittweet.data.remote.response.SearchResponse
import ds.gittweet.data.remote.response.UserResponse
import io.reactivex.Completable
import javax.inject.Inject

class SearchUserInteractor @Inject constructor(private var githubDatabase: GithubDatabase) {

    fun search(query: String, page: Int, perPage: Int) = GithubService.getEndpoint().searchUser(query, page, perPage)

    fun insertRecent(userResponse: UserResponse): Completable {
        val searchRecentUser = SearchRecentUser(
            login = userResponse.login,
            avatarUrl = userResponse.avatarUrl,
            url = userResponse.url
        )

        return githubDatabase.searchRecentUserDao().insert(searchRecentUser)
    }

    private fun createSearchResponse(
        searchResponse: SearchResponse<List<UserResponse>>,
        userResponses: List<UserResponse>
    ): SearchResponse<List<UserResponse>?> {
        return SearchResponse(searchResponse.totalCount, searchResponse.incompleteResults, userResponses)
    }
}