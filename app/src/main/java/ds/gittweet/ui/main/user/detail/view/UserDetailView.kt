package ds.gittweet.ui.main.user.detail.view

import ds.gittweet.data.remote.response.UserResponse

interface UserDetailView {
    fun initView()
    fun closeView()
    fun showUserEdit()
    fun showUserAvatar()
    fun showUserDetail(user: UserResponse)
    fun showLoading(isLoading: Boolean)
    fun showErrorResponse(it: Throwable?)
    fun getState(): UserDetailViewState
}