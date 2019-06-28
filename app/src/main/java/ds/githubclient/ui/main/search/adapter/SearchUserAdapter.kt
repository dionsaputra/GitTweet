package ds.githubclient.ui.main.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ds.githubclient.R
import ds.githubclient.data.remote.response.UserResponse
import kotlinx.android.synthetic.main.item_search_user_result.view.*

class SearchUserAdapter(var data: MutableList<UserResponse>) : RecyclerView.Adapter<SearchUserAdapter.SearchUserHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserHolder {
        return SearchUserHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_search_user_result,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SearchUserHolder, position: Int) = holder.bind(data[position])

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

    class SearchUserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: UserResponse) = with(itemView) {
            Glide.with(itemView.context).load(item.avatarUrl).placeholder(R.drawable.user_placeholder)
                .into(searchResultAvatar)
            searchResultLogin.text = item.login
//            tvSearchUserBio.text = item.bio
        }
    }

    class SearchUserDiffCallback(private val oldData: List<UserResponse>, private val newData: List<UserResponse>) :
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