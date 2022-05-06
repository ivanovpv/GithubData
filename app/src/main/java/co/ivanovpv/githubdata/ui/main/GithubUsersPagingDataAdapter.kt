package co.ivanovpv.githubdata.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.ivanovpv.githubdata.api.models.GithubUser
import co.ivanovpv.githubdata.databinding.RowGithubUserBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside

class GithubUsersPagingDataAdapter(private val callback: Callback) :
    PagingDataAdapter<GithubUser, GithubUserViewHolder>(GithubUserDiffCallBack()) {

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        val githubUser = getItem(position)
        if (githubUser != null)
            holder.bind(githubUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        return GithubUserViewHolder(
            callback,
            RowGithubUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    interface Callback {
        fun onUserSelected(user: GithubUser)
    }
}

class GithubUserDiffCallBack : DiffUtil.ItemCallback<GithubUser>() {
    override fun areItemsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: GithubUser, newItem: GithubUser): Boolean {
        return oldItem == newItem
    }
}

class GithubUserViewHolder(
    private val callback: GithubUsersPagingDataAdapter.Callback,
    private val binding: RowGithubUserBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(githubUser: GithubUser) {
        binding.root.setOnClickListener {
            callback.onUserSelected(githubUser)
        }
        binding.tvLogin.text = githubUser.login
        binding.admin.isChecked = githubUser.siteAdmin
        Glide.with(binding.imgUser)
            .load(githubUser.avatarUrl)
            .transform(CenterInside())
            .into(binding.imgUser)
    }
}