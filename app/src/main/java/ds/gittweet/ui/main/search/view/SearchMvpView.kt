package ds.gittweet.ui.main.search.view

import ds.gittweet.data.remote.response.UserResponse

interface SearchMvpView {

    fun showInitialPageUsers(userResponses: List<UserResponse>)
    fun showNextPageUsers(userResponses: List<UserResponse>)
    fun onRefresh(refreshState: Boolean)
    fun onLoadMore(loadingMoreState: Boolean)
    fun onReachEndOfData()
    fun showEmptyUser()
    fun showErrorMessage(errorMessage: String)
    fun getTotalItem(): Int
}