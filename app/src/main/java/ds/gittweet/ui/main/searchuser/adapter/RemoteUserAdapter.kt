package ds.gittweet.ui.main.searchuser.adapter

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

class RemoteUserAdapter(
    private var data: MutableList<UserResponse>,
    private val onItemClick: ((UserResponse) -> Unit)? = null
) : RecyclerView.Adapter<RemoteUserAdapter.RemoteUserHolder>() {

    private val itemLayout = R.layout.item_remote_user

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteUserHolder {
        val inflater = LayoutInflater.from(parent.context)
        return RemoteUserHolder(inflater.inflate(itemLayout, parent, false))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: RemoteUserHolder, position: Int) {
        holder.bind(data[position], onItemClick)
    }

    fun swapData(data: List<UserResponse>) {
        this.data = data.toMutableList()
        notifyDataSetChanged()
    }

    fun addData(data: List<UserResponse>) {
        this.data.addAll(data)
        notifyDataSetChanged()
    }

    class RemoteUserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(item: UserResponse, onItemClick: ((UserResponse) -> Unit)?) = with(itemView) {
            Glide.with(context)
                .load(item.avatarUrl)
                .placeholder(R.drawable.user_placeholder)
                .into(userRemoteAvatar)

            userRemoteLogin.text = item.login
            onItemClick?.let { setOnClickListener { it(item) } }
        }
    }

    inner class RemoteUserDiffCallback(
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