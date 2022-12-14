package com.zeroplusx.mobile.ui.sources.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeroplusx.mobile.R
import com.zeroplusx.mobile.databinding.FragmentListBinding
import com.zeroplusx.mobile.domain.model.Source
import com.zeroplusx.mobile.ui.articles.adapter.delegate.EmptyDelegate
import com.zeroplusx.mobile.ui.core.adapter.DelegateAdapter
import com.zeroplusx.mobile.ui.articles.adapter.delegate.ErrorDelegate
import com.zeroplusx.mobile.ui.articles.adapter.delegate.ProgressDelegate
import com.zeroplusx.mobile.ui.articles.adapter.item.EmptyItem
import com.zeroplusx.mobile.ui.articles.adapter.item.ErrorItem
import com.zeroplusx.mobile.ui.articles.adapter.item.ProgressItem
import com.zeroplusx.mobile.ui.articles.fragment.ArticlesFragment
import com.zeroplusx.mobile.ui.core.adapter.AdapterItem
import com.zeroplusx.mobile.ui.core.model.SourceWrapper
import com.zeroplusx.mobile.ui.sources.adapter.delegate.SourceDelegate
import com.zeroplusx.mobile.ui.sources.adapter.item.SourceItem
import com.zeroplusx.mobile.ui.sources.viewModel.SourcesViewModel
import com.zeroplusx.mobile.ui.sources.viewState.SourcesViewState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class SourcesFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: SourcesViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.listView.layoutManager = LinearLayoutManager(requireContext())
        binding.listView.setHasFixedSize(true)
        val delegateAdapter = DelegateAdapter<AdapterItem<*>>(
            listOf(
                SourceDelegate(this::navigateToArticleDetails),
                ProgressDelegate(),
                ErrorDelegate(),
                EmptyDelegate(),
            )
        )
        binding.listView.adapter = delegateAdapter

        viewModel.sourcesState
            .onEach { viewState ->
                val items = when (viewState) {
                    is SourcesViewState.Sources -> {
                        if (viewState.articles.isEmpty()) {
                            listOf(EmptyItem)
                        } else {
                            viewState.articles.map { SourceItem(it) }
                        }
                    }
                    is SourcesViewState.Error -> {
                        listOf(ErrorItem(viewState.error))
                    }
                    SourcesViewState.Loading -> {
                        listOf(ProgressItem)
                    }
                }
                delegateAdapter.setItems(items)
            }
            .launchIn(lifecycleScope)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun navigateToArticleDetails(source: Source) {
        findNavController().navigate(R.id.action_SourcesFragment_to_FirstFragment, Bundle().apply {
            putParcelable(ArticlesFragment.SOURCE_ARG, SourceWrapper(source))
        })
    }
}
