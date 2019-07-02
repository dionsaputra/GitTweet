package ds.gittweet.ui.main.injection

import dagger.Component
import ds.gittweet.ui.main.user.search.view.UserSearchFragment
import ds.mvpkotlin.di.component.AppComponent

@Component(dependencies = [AppComponent::class])
interface MainComponent {

    fun inject(userSearchFragment: UserSearchFragment)

}