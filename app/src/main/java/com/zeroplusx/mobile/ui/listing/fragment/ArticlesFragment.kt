package com.zeroplusx.mobile.ui.listing.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeroplusx.mobile.databinding.FragmentListBinding
import com.zeroplusx.mobile.ui.core.adapter.DelegateAdapter
import com.zeroplusx.mobile.ui.listing.adapter.delegate.ArticleDelegate
import com.zeroplusx.mobile.ui.listing.adapter.delegate.ErrorDelegate
import com.zeroplusx.mobile.ui.listing.adapter.delegate.ProgressDelegate
import com.zeroplusx.mobile.ui.listing.adapter.item.ArticleItem
import com.zeroplusx.mobile.ui.listing.adapter.item.ErrorItem
import com.zeroplusx.mobile.ui.listing.adapter.item.ProgressItem
import com.zeroplusx.mobile.ui.listing.viewModel.ArticlesViewModel
import com.zeroplusx.mobile.ui.listing.viewState.ArticleListViewState
import com.zeroplusx.mobile.ui.model.SourceWrapper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class ArticlesFragment : Fragment() {

    private var _binding: FragmentListBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @Inject
    lateinit var viewModelAssistedFactory: ArticlesViewModel.Factory

    private val viewModel: ArticlesViewModel by viewModels(
        factoryProducer = {
            ArticlesViewModel.provideFactory(
                viewModelAssistedFactory,
                checkNotNull(requireArguments().getParcelable<SourceWrapper>(SOURCE_ARG)?.source)
            )
        }
    )

    private val delegateAdapter = DelegateAdapter(
        listOf(
            ArticleDelegate(),
            ProgressDelegate(),
            ErrorDelegate()
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.articleListState
            .onEach { viewState ->
                delegateAdapter.data = when (viewState) {
                    is ArticleListViewState.Articles -> {
                        viewState.articles.map { ArticleItem(it) }
                    }
                    is ArticleListViewState.Error -> {
                        listOf(ErrorItem(viewState.error))
                    }
                    ArticleListViewState.Loading -> {
                        listOf(ProgressItem)
                    }
                }
                delegateAdapter.notifyDataSetChanged()
            }
            .launchIn(lifecycleScope)

        binding.listView.layoutManager = LinearLayoutManager(requireContext())
        binding.listView.setHasFixedSize(true)
        binding.listView.adapter = delegateAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val SOURCE_ARG = "source"
    }
}
