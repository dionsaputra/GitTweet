package ds.gittweet.ui.main.user.search.presenter

import ds.gittweet.data.local.database.GithubDatabase
import ds.gittweet.data.local.entity.UserEntity
import ds.gittweet.data.remote.endpoint.GithubService
import ds.gittweet.data.remote.response.SearchResponse
import ds.gittweet.data.remote.response.UserResponse
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject

class UserSearchInteractor @Inject constructor(
    private var githubDatabase: GithubDatabase,
    private var githubService: GithubService
) {

    fun search(query: String, page: Int, perPage: Int): Observable<SearchResponse<List<UserResponse>?>> {
        return githubService.searchUser(query, page, perPage)
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

}