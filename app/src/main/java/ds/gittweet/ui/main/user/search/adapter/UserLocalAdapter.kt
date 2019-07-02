package ds.gittweet.ui.main.user.search.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ds.gittweet.R
import ds.gittweet.data.local.entity.UserEntity
import kotlinx.android.synthetic.main.item_local_user.view.*

class UserLocalAdapter(
    private var data: List<UserEntity>,
    private val onItemClick: ((UserEntity) -> Unit)? = null
) : RecyclerView.Adapter<UserLocalAdapter.LocalUserHolder>() {

    private val itemLayout = R.layout.item_local_user

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocalUserHolder {
        val inflater = LayoutInflater.from(parent.context)
        return LocalUserHolder(inflater.inflate(itemLayout, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: LocalUserHolder, position: Int) {
        holder.bind(data[position], onItemClick)
    }

    fun swapData(data: List<UserEntity>) {
        this.data = data
        notifyDataSetChanged()
    }

    class LocalUserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: UserEntity, onItemClick: ((UserEntity) -> Unit)?) = with(itemView) {
            Glide.with(context)
                .load(item.avatarUrl)
                .placeholder(R.drawable.user_placeholder)
                .into(localUserAvatar)

            localUserLogin.text = item.login

            onItemClick?.let { setOnClickListener { it(item) } }
        }

    }
}