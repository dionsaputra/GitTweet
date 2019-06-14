package ds.githubclient.ui.main.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ds.githubclient.R
import ds.githubclient.data.network.model.User
import kotlinx.android.synthetic.main.item_search_user.view.*

class SearchUserAdapter(var data: List<User>) : RecyclerView.Adapter<SearchUserAdapter.SearchUserHolder>() {

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
        this.data = data
        notifyDataSetChanged()
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
}