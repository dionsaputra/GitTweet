package ds.githubclient.ui.main.search.contract

interface SearchMvpPresenter {

    fun attachView(view: SearchMvpView)
    fun getAllUsers()
    fun filterUsers(searchQuery: String)
}