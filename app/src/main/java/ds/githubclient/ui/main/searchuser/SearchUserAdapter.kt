package ds.githubclient.ui.main.searchuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ds.githubclient.R
import ds.githubclient.data.remote.response.UserResponse
import kotlinx.android.synthetic.main.item_search_user_recent.view.*
import kotlinx.android.synthetic.main.item_search_user_result.view.*

class SearchUserAdapter(
    private var data: MutableList<UserResponse>,
    private var isSearchRecent: Boolean,
    private var onItemSearchClick: ((UserResponse) -> Unit)? = null
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (isSearchRecent) {
            SearchRecentHolder(inflater.inflate(R.layout.item_search_user_recent, parent, false))
        } else {
            SearchResultHolder(inflater.inflate(R.layout.item_search_user_result, parent, false))
        }
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is SearchResultHolder -> onItemSearchClick?.let { holder.bind(data[position], it) }
            is SearchRecentHolder -> holder.bind(data[position])
        }
    }

    fun swapData(data: List<UserResponse>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: List<UserResponse>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun updateData(data: List<UserResponse>) {
        val diffResult = DiffUtil.calculateDiff(SearchUserDiffCallback(this.data, data))
        diffResult.dispatchUpdatesTo(this)
    }

    inner class SearchResultHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: UserResponse, onItemSearchClick: (UserResponse) -> Unit) = with(itemView) {
            Glide.with(itemView.context).load(item.avatarUrl).placeholder(R.drawable.user_placeholder)
                .into(searchResultAvatar)
            searchResultLogin.text = item.login
            onItemSearchClick(item)
        }
    }

    inner class SearchRecentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: UserResponse) = with(itemView) {
            Glide.with(itemView.context).load(item.avatarUrl).placeholder(R.drawable.user_placeholder)
                .into(searchRecentAvatar)
            searchRecentLogin.text = item.login
        }
    }

    inner class SearchUserDiffCallback(
        private val oldData: List<UserResponse>,
        private val newData: List<UserResponse>
    ) :
        DiffUtil.Callback() {

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].id == newData[newItemPosition].id
        }

        override fun getOldListSize(): Int = oldData.size

        override fun getNewListSize(): Int = newData.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldData[oldItemPosition].generalEquals(newData[newItemPosition])
        }

    }
}