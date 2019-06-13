package ds.mvpkotlin.di.component

import dagger.Component
import ds.mvpkotlin.di.module.ActivityModule
import ds.mvpkotlin.di.scope.PerActivity

@PerActivity
@Component(dependencies = [ApplicationComponent::class], modules = [ActivityModule::class])
interface ActivityComponent {

    // TODO: Add inject function like example below
    //fun inject(activity: MainActivity)

}
