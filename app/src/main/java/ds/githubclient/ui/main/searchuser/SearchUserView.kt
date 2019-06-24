package ds.githubclient.ui.main.searchuser

import ds.githubclient.data.network.model.User

interface SearchUserView {

    fun initView()
    fun showClearSearchButton()
    fun hideClearSearchButton()
    fun clearSearchQuery()
    fun showDeleteRecentConfirmation()
    fun hideDeleteRecentConfirmation()
    fun hideGroupRecent()
    fun showGroupRecent()
    fun hideGroupSearch()
    fun showGroupSearch()
    fun showRecentEmpty()
    fun showRecentResult(users: List<User>)
    fun showError(error: Throwable)
    fun showSearchEmpty()
    fun showFirstPageSearchResult(users: List<User>)
    fun showNextPageSearchResult(users: List<User>)
    fun checkEndOfSearchData(totalCount: Int)
    fun showLoadMoreSearchLoading()
    fun hideLoadMoreSearchLoading()
    fun closeView()

}
