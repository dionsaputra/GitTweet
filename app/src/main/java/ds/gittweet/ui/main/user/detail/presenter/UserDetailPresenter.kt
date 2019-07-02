package ds.gittweet.ui.main.user.detail.presenter

import ds.gittweet.data.remote.response.UserResponse
import ds.gittweet.ui.main.user.detail.view.UserDetailView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UserDetailPresenter @Inject constructor(
    private val compositeDisposable: CompositeDisposable,
    private val userDetailInteractor: UserDetailInteractor
) {

    private lateinit var view: UserDetailView

    fun attachView(view: UserDetailView) {
        this.view = view
        view.initView()

        loadDetailUser()
    }

    fun onBackNavigationClick() {
        view.closeView()
    }

    fun onEditProfileClick(){
        view.showUserEdit()
    }

    fun onAvatarProfileClick() {
        view.showUserAvatar()
    }

    fun loadDetailUser() {
        //view.showUserDetail(user)
    }

    fun detachView() {
        compositeDisposable.dispose()
    }
}