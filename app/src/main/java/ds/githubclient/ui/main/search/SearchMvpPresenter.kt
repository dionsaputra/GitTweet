package ds.githubclient.ui.main.search

interface SearchMvpPresenter {

    fun getAllUser()
    fun filterUser(searchQuery: String)

}