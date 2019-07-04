package ds.gittweet.data.remote.endpoint

import ds.gittweet.data.remote.response.SearchResponse
import ds.gittweet.data.remote.response.UserResponse
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.security.cert.CertificateException
import javax.net.ssl.*

interface GithubService {

    @GET("/users")
    fun listUser(
        @Query("since") lastUserId: Long,
        @Query("per_page") perPage: Int,
        @Query("client_id") clientId: String = CLIENT_ID,
        @Query("client_secret") clientSecret: String = CLIENT_SECRET
    ): Observable<List<UserResponse>>

    @GET("/users/{login}")
    fun getUserByLogin(
        @Path("login") userLogin: String,
        @Query("client_id") clientId: String = CLIENT_ID,
        @Query("client_secret") clientSecret: String = CLIENT_SECRET
    ): Observable<UserResponse>

    @GET("/search/users")
    fun searchUser(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int,
        @Query("client_id") clientId: String = CLIENT_ID,
        @Query("client_secret") clientSecret: String = CLIENT_SECRET
    ): Observable<SearchResponse<List<UserResponse>?>>

    companion object {
        const val BASE_URL = "http://api.github.com"
        private const val CLIENT_ID = "245bcd04581513f36a64"
        private const val CLIENT_SECRET = "8471c1b4bad816c5806c2412f9b4b8323b51193e"
    }
}