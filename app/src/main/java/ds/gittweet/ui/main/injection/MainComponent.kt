package ds.gittweet.ui.main.injection

import dagger.Component
import ds.gittweet.ui.main.searchuser.SearchUserFragment
import ds.mvpkotlin.di.component.ApplicationComponent

@Component(dependencies = [ApplicationComponent::class])
interface MainComponent {

    fun inject(searchUserFragment: SearchUserFragment)

}