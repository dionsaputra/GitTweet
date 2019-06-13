package ds.mvpkotlin.di.module

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import ds.mvpkotlin.di.qualifier.ActivityContext

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityContext
    fun provideContext(): Context = activity

    @Provides
    fun provideActivity(): Activity = activity

    /* TO ADD RX:
    * @Provides
    * fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()
    *
    * @Provides
    * fun provideSchedulerProvider(): SchedulerProvider = SchedulerProvider()
    * */

    // TODO: add other activity component here
}