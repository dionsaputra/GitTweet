package ds.gittweet.data.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse<T>(
    @SerializedName("total_count") val totalCount: Int,
    @SerializedName("incomplete_results") val incompleteResults: Boolean,
    @SerializedName("items") val items: T
)