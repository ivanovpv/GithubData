package co.ivanovpv.githubdata.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import co.ivanovpv.githubdata.databinding.MainFragmentBinding
import co.ivanovpv.githubdata.domain.model.GithubUser
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()
    private var _binding: MainFragmentBinding? = null
    private val binding get() = _binding!!
    private var adapter: GithubUsersPagingDataAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = GithubUsersPagingDataAdapter(::onUserSelected)
        val userList = binding.rvUsers
        userList.adapter = adapter
        lifecycleScope.launch {
            viewModel.getUsers().collectLatest {
                adapter?.submitData(it)
            }
        }
        lifecycleScope.launch {
            adapter?.loadStateFlow?.collectLatest { loadStates ->
                binding.progressBar.isVisible = loadStates.refresh is LoadState.Loading
                Log.i("ivanovpv", "load state = ${loadStates}")
                if (loadStates.refresh is LoadState.Error) {
                    Log.i("ivanovpv", "load state in error")
                    MyAlertDialog(requireContext())
                        .withCloseButton { this@MainFragment.onBackPressed() }
                        .withDescription((loadStates.refresh as LoadState.Error).error.localizedMessage ?: "")
                        .show()
                }
            }
        }
    }

    fun onBackPressed() {
        if (!findNavController().popBackStack()) {
            activity?.onBackPressed()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        adapter = null
        _binding = null
    }

    private fun onUserSelected(user: GithubUser) {
        val direction = MainFragmentDirections.actionMainFragmentToUserInfoDialog(user)
        findNavController().navigate(direction)
    }

}