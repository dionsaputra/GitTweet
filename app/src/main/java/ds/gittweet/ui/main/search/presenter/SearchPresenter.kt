package ds.gittweet.ui.main.search.presenter

import ds.gittweet.ui.main.search.view.SearchMvpView

class SearchPresenter {

    lateinit var view: SearchMvpView

    fun attachView(view: SearchMvpView) {
        this.view = view
    }

}