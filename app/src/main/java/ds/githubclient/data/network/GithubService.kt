package ds.githubclient.data.network

import ds.githubclient.data.network.model.User
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubService {

    @GET("/users")
    fun listUser(
        @Query("since") userId: Long,
        @Query("per_page") perPage: Int,
        @Query("client_id") clientId: String = CLIENT_ID,
        @Query("client_secret") clientSecret: String = CLIENT_SECRET
    ): Observable<List<User>>

    @GET("/users/{login}")
    fun retrieveUser(
        @Path("login") userLogin: String,
        @Query("client_id") clientId: String = CLIENT_ID,
        @Query("client_secret") clientSecret: String = CLIENT_SECRET
    ): Observable<User>

    companion object {
        private const val BASE_URL = "http://api.github.com"
        private const val CLIENT_ID = "245bcd04581513f36a64"
        private const val CLIENT_SECRET = "8471c1b4bad816c5806c2412f9b4b8323b51193e"

        private var instance: Retrofit? = null

        private fun getInstance(): Retrofit {
            if (instance == null) {
                val interceptor = HttpLoggingInterceptor()
                interceptor.level = HttpLoggingInterceptor.Level.BODY

                val client = OkHttpClient.Builder().addInterceptor(interceptor).build()
                instance = Retrofit
                    .Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build()
            }
            return instance!!
        }

        fun getEndpoint(): GithubService {
            return getInstance()
                .create(GithubService::class.java)
        }
    }
}