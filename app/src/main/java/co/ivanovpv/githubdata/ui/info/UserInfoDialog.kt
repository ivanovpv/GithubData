package co.ivanovpv.githubdata.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import co.ivanovpv.githubdata.R
import co.ivanovpv.githubdata.databinding.UserInfoDialogBinding
import co.ivanovpv.githubdata.model.GithubUser
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterInside
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UserInfoDialog(): BottomSheetDialogFragment() {

    private val viewModel: UserInfoViewModel by viewModels()
    private val args by navArgs<UserInfoDialogArgs>()

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
        user = args.user
        viewModel.countFollowers(user.login)
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.followersCountState.collect {
                when(it) {
                    is CountState.Loading -> {
                        binding.progressBar.isVisible = true
                    }
                    is CountState.Success -> {
                        binding.tvFollowers.text = getString(R.string.calculating_followers) + " " +
                            "${it.count}"
                        binding.progressBar.isVisible = false
                    }
                    is CountState.Finished -> {
                        binding.progressBar.isVisible = false
                        binding.tvFollowers.text =
                            getString(R.string.followers) + " ${it.count}"
                    }
                    is CountState.Error -> {
                        binding.progressBar.isVisible = false
                        binding.tvFollowers.text =
                            "Something went wrong ${it.reason}"
                    }
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