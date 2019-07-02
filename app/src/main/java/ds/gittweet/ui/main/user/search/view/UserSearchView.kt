package ds.gittweet.ui.main.user.search.view

import ds.gittweet.data.local.entity.UserEntity
import ds.gittweet.data.remote.response.UserResponse

interface UserSearchView {
    fun initView()
    fun showClearSearchButton(isShow: Boolean)
    fun clearSearchQuery()
    fun showClearRecentConfirmation(isShow: Boolean)
    fun showGroupRecent(isShow: Boolean)
    fun showGroupSearch(isShow: Boolean)
    fun showRecentEmpty()
    fun showRecentResult(users: List<UserEntity>)
    fun showMessage(message: String)
    fun showSearchEmpty()
    fun showSearchResult(userResponses: List<UserResponse>)
    fun checkEndOfSearchData(totalCount: Int)
    fun showLoadMoreSearchLoading(isShow: Boolean)
    fun closeView()
    fun getState(): UserSearchViewState
    fun showUserDetail(login: String)
}
