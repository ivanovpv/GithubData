package co.ivanovpv.githubdata.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import co.ivanovpv.githubdata.R
import co.ivanovpv.githubdata.api.models.GithubUser
import co.ivanovpv.githubdata.databinding.UserInfoDialogBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoDialog(private val viewModel: MainViewModel) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "UserInfoDialog"
        private const val USER: String = "USER"

        fun newInstance(viewModel: MainViewModel, user: GithubUser) =
            UserInfoDialog(viewModel).apply {
                arguments = Bundle().apply {
                    putString(USER, Gson().toJson(user))
                }
            }
    }

    private lateinit var user: GithubUser
    private var _binding: UserInfoDialogBinding? = null
    private val binding
        get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = UserInfoDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val json = arguments?.getString(USER).orEmpty()
        user = Gson().fromJson(json, GithubUser::class.java)
        viewModel.countFollowers(user.login)
        lifecycleScope.launchWhenStarted {
            viewModel.followersCountState.collect {
                if (it.isLoading()) {
                    binding.tvFollowers.text = getString(R.string.calculating_followers)
                    binding.progressBar.isVisible = true
                }
                if (it.isSuccess()) {
                    binding.progressBar.isVisible = false
                    binding.tvFollowers.text =
                        getString(R.string.followers) + " ${it.result!!}"
                }
            }
        }
        initViews(view)
    }

    private fun initViews(view: View) {
        binding.tvLogin.text = user.login
        binding.tvHome.text = user.htmlUrl
        Glide.with(view.context)
            .load(user.avatarUrl)
            .error(R.drawable.ic_no_photo)
            .transform(CenterInside())
            .into(binding.ivUser)
    }
}