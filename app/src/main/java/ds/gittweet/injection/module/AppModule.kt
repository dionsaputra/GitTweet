package ds.mvpkotlin.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import ds.gittweet.data.local.database.GithubDatabase
import ds.gittweet.utility.AppConstant
import io.reactivex.disposables.CompositeDisposable

@Module
class AppModule(private val application: Application) {

    @Provides
    fun provideApplicationContext(): Context = application.applicationContext

    @Provides
    fun provideApplication(): Application = application

    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideDatabase(context: Context): GithubDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            GithubDatabase::class.java,
            AppConstant.DATABASE_NAME
        ).build()
    }

    /*** FOR DATABASE ***/
    //@Provides
    //@DatabaseInfo
    //fun provideDatabaseName(): String = AppConstants.DB_NAME;

    /*** FOR API ***/
    //@Provides
    //@ApiInfo
    //fun provideApiKey(): String = BuildConfig.API_KEY

    /*** FOR PREFERENCE ***/
    //@Provides
    //@PreferenceInfo
    //fun providePreferenceName(): String = AppConstants.PREF_NAME

    /* FOR TRANSLATE LATER

    @Provides
    @Singleton
    DataManager provideDataManager(AppDataManager appDataManager) {
        return appDataManager;
    }

    @Provides
    @Singleton
    DbHelper provideDbHelper(AppDbHelper appDbHelper) {
        return appDbHelper;
    }

    @Provides
    @Singleton
    PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
        return appPreferencesHelper;
    }

    @Provides
    @Singleton
    ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
        return appApiHelper;
    }

    @Provides
    @Singleton
    ApiHeader.ProtectedApiHeader provideProtectedApiHeader(@ApiInfo String apiKey,
                                                           PreferencesHelper preferencesHelper) {
        return new ApiHeader.ProtectedApiHeader(
                apiKey,
                preferencesHelper.getCurrentUserId(),
                preferencesHelper.getAccessToken());
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/source-sans-pro/SourceSansPro-Regular.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build();
    }
     */
}