package ds.gittweet.ui.main.searchuser

import ds.gittweet.data.remote.response.UserResponse

interface SearchUserView {

    fun initView()
    fun showClearSearchButton(isShow: Boolean)
    fun clearSearchQuery()
    fun showClearRecentConfirmation(isShow: Boolean)
    fun showGroupRecent(isShow: Boolean)
    fun showGroupSearch(isShow: Boolean)
    fun showRecentEmpty()
    fun showRecentResult(userResponses: List<UserResponse>)
    fun showMessage(message: String)
    fun showSearchEmpty()
    fun showSearchResult(userResponses: List<UserResponse>, isFirstPage: Boolean)
    fun checkEndOfSearchData(totalCount: Int)
    fun showLoadMoreSearchLoading(isShow: Boolean)
    fun closeView()
    fun getState(): SearchUserViewState

}
