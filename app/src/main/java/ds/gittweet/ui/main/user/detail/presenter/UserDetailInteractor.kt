package ds.gittweet.ui.main.user.detail.presenter

import ds.gittweet.data.local.database.GithubDatabase
import ds.gittweet.data.remote.endpoint.GithubService
import ds.gittweet.data.remote.response.UserResponse
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import javax.inject.Inject

class UserDetailInteractor @Inject constructor(
    private val githubService: GithubService
) {

    fun getUser(userLogin: String): Observable<UserResponse> {
        return githubService.getUserByLogin(userLogin)
    }
}