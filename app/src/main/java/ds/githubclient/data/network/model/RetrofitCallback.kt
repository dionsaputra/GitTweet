package ds.githubclient.data.network.model

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RetrofitCallback<T>(val onComplete: (T?, String?) -> Unit) : Callback<T?> {

    override fun onFailure(call: Call<T?>, t: Throwable) {
        call.cancel()
        onComplete(null, t.message)
    }

    override fun onResponse(call: Call<T?>, response: Response<T?>) {
        if (response.isSuccessful) {
            onComplete(response.body(), null)
        } else {
            onComplete(null, response.message())
        }
    }
}