package ds.gittweet.ui.main.user.detail.presenter

import ds.gittweet.helper.applyScheduler
import ds.gittweet.ui.main.user.detail.view.UserDetailView
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class UserDetailPresenter @Inject constructor(
    private val compositeDisposable: CompositeDisposable,
    private val interactor: UserDetailInteractor
) {

    private lateinit var view: UserDetailView

    fun attachView(view: UserDetailView) {
        this.view = view
        view.initView()

        loadDetailUser(view.getState().userLogin)
    }

    fun onBackNavigationClick() {
        view.closeView()
    }

    fun onEditProfileClick() {
        view.showUserEdit()
    }

    fun onAvatarProfileClick() {
        view.showUserAvatar()
    }

    private fun loadDetailUser(userLogin: String) {
        compositeDisposable.add(
            interactor.getUser(userLogin)
                .applyScheduler()
                .doOnSubscribe { view.showLoading(true) }
                .doOnTerminate { view.showLoading(false) }
                .subscribe(
                    { view.showUserDetail(it) },
                    { view.showErrorResponse(it) }
                )
        )
    }

    fun detachView() {
        compositeDisposable.dispose()
    }
}