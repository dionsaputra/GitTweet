package ds.githubclient.ui.main.search.presenter

import ds.githubclient.ui.main.search.view.SearchMvpView

interface SearchMvpPresenter {

    fun attachView(view: SearchMvpView)
    fun getUsers(searchQuery: String, perPage: Int, isStartingPage: Boolean)

}