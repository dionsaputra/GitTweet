package ds.gittweet.ui.main.user.detail.view

import ds.gittweet.data.remote.response.UserResponse

interface UserDetailView {
    fun initView()
    fun closeView()
    fun showUserEdit()
    fun showUserAvatar()
    fun showUserDetail(user: UserResponse)
}