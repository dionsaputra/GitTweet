package ds.gittweet.ui.main.search.presenter

import ds.gittweet.data.remote.response.UserResponse
import ds.gittweet.ui.main.search.view.SearchMvpView
import ds.gittweet.utility.orFalse
import ds.gittweet.utility.orZero

class SearchPresenter {

    lateinit var view: SearchMvpView

    fun attachView(view: SearchMvpView) {
        this.view = view
    }

}