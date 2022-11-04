package co.ivanovpv.githubdata.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.ivanovpv.githubdata.databinding.RowGithubUserBinding
import co.ivanovpv.githubdata.model.GithubUser
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside

class GithubUsersPagingDataAdapter(
    private val onUserSelected: (githubUser: GithubUser) -> Unit
    ) :
    PagingDataAdapter<GithubUser, GithubUserViewHolder>(GithubUserDiffCallBack()) {

    override fun onBindViewHolder(holder: GithubUserViewHolder, position: Int) {
        val githubUser = getItem(position)
        if (githubUser != null)
            holder.bind(githubUser)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GithubUserViewHolder {
        return GithubUserViewHolder(
            onUserSelected,
            RowGithubUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
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
    private val callback: (githubUser: GithubUser) -> Unit,
    private val binding: RowGithubUserBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(githubUser: GithubUser) {
        binding.root.setOnClickListener {
            callback(githubUser)
        }
        binding.tvLogin.text = githubUser.login
        binding.admin.isChecked = githubUser.siteAdmin
        Glide.with(binding.imgUser)
            .load(githubUser.avatarUrl)
            .transform(CenterInside())
            .into(binding.imgUser)
    }
}