package ds.gittweet.ui.main.searchuser

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ds.gittweet.R
import ds.gittweet.data.remote.response.UserResponse
import kotlinx.android.synthetic.main.item_local_user.view.*
import kotlinx.android.synthetic.main.item_remote_user.view.*

class SearchUserAdapter(
    private var data: MutableList<UserResponse>,
    private var isSearchRecent: Boolean,
    private var onItemSearchClick: ((UserResponse) -> Unit)? = null
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (isSearchRecent) {
            SearchRecentHolder(inflater.inflate(R.layout.item_local_user, parent, false))
        } else {
            SearchResultHolder(inflater.inflate(R.layout.item_remote_user, parent, false))
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
                .into(userRemoteAvatar)
            userRemoteLogin.text = item.login

            setOnClickListener { onItemSearchClick(item) }

        }
    }

    inner class SearchRecentHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: UserResponse) = with(itemView) {
            Glide.with(itemView.context).load(item.avatarUrl).placeholder(R.drawable.user_placeholder)
                .into(localUserAvatar)
            localUserLogin.text = item.login
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