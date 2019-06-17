package ds.githubclient.ui.main.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ds.githubclient.R
import ds.githubclient.data.network.model.User
import kotlinx.android.synthetic.main.item_search_user.view.*

class SearchUserAdapter(var data: MutableList<User>) : RecyclerView.Adapter<SearchUserAdapter.SearchUserHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchUserHolder {
        return SearchUserHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_search_user,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: SearchUserHolder, position: Int) = holder.bind(data[position])

    fun swapData(data: List<User>) {
        this.data.clear()
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun addData(data: List<User>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    fun updateData(data: List<User>) {
        val diffResult = DiffUtil.calculateDiff(SearchUserDiffCallback(this.data, data))
        diffResult.dispatchUpdatesTo(this)
    }

    class SearchUserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(item: User) = with(itemView) {
            Glide.with(itemView.context).load(item.avatarUrl).placeholder(R.drawable.user_placeholder)
                .into(civSearchUserAvatar)
            tvSearchUserName.text = item.name
            tvSearchUserLogin.text = item.login
            tvSearchUserBio.text = item.bio
        }
    }

    class SearchUserDiffCallback(private val oldData: List<User>, private val newData: List<User>) :
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