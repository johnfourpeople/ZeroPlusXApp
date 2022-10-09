package com.zeroplusx.mobile.ui.articles.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.zeroplusx.mobile.databinding.FragmentListBinding
import com.zeroplusx.mobile.domain.interactor.ArticlesInteractor
import com.zeroplusx.mobile.ui.articles.adapter.delegate.ArticleDelegate
import com.zeroplusx.mobile.ui.articles.adapter.delegate.EmptyDelegate
import com.zeroplusx.mobile.ui.articles.adapter.delegate.ErrorDelegate
import com.zeroplusx.mobile.ui.articles.adapter.delegate.ProgressDelegate
import com.zeroplusx.mobile.ui.articles.adapter.delegate.SmallErrorDelegate
import com.zeroplusx.mobile.ui.articles.adapter.delegate.SmallProgressDelegate
import com.zeroplusx.mobile.ui.articles.adapter.item.ArticleItem
import com.zeroplusx.mobile.ui.articles.adapter.item.EmptyItem
import com.zeroplusx.mobile.ui.articles.adapter.item.ErrorItem
import com.zeroplusx.mobile.ui.articles.adapter.item.ProgressItem
import com.zeroplusx.mobile.ui.articles.adapter.item.SmallErrorItem
import com.zeroplusx.mobile.ui.articles.adapter.item.SmallProgressItem
import com.zeroplusx.mobile.ui.articles.viewModel.ArticlesViewModel
import com.zeroplusx.mobile.ui.core.OnBottomReachedListener
import com.zeroplusx.mobile.ui.core.adapter.AdapterItem
import com.zeroplusx.mobile.ui.core.adapter.DelegateAdapter
import com.zeroplusx.mobile.ui.core.model.SourceWrapper
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

    @Suppress("Deprecation")
    private val viewModel: ArticlesViewModel by viewModels(
        factoryProducer = {
            ArticlesViewModel.provideFactory(
                viewModelAssistedFactory,
                checkNotNull(requireArguments().getParcelable<SourceWrapper>(SOURCE_ARG)?.source)
            )
        }
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

        val delegateAdapter = DelegateAdapter<AdapterItem<*>>(
            listOf(
                ArticleDelegate(),
                EmptyDelegate(),
                ProgressDelegate(),
                SmallProgressDelegate(),
                SmallErrorDelegate(viewModel::onRetry),
                ErrorDelegate(viewModel::onRetry),
            )
        )
            .apply {
                setHasStableIds(true)
            }

        viewModel.articleListState
            .onEach { state ->
                val itemsList = mutableListOf<AdapterItem<*>>().apply {
                    when (state) {
                        is ArticlesInteractor.State.Error -> {
                            if (state.articles.isEmpty()) {
                                add(ErrorItem(state.error))
                            } else {
                                state.articles.forEach { add(ArticleItem(it)) }
                                add(SmallErrorItem(state.error))
                            }
                        }
                        is ArticlesInteractor.State.Idle -> {
                            if (state.articles.isEmpty()) {
                                add(EmptyItem)
                            } else {
                                state.articles.forEach { add(ArticleItem(it)) }
                            }
                        }
                        is ArticlesInteractor.State.Loading -> {
                            if (state.articles.isEmpty()) {
                                add(ProgressItem)
                            } else {
                                state.articles.forEach { add(ArticleItem(it)) }
                                add(SmallProgressItem)
                            }
                        }
                    }
                }
                delegateAdapter.setItems(itemsList)
            }
            .launchIn(lifecycleScope)

        binding.listView.layoutManager = LinearLayoutManager(requireContext())
        binding.listView.adapter = delegateAdapter
        binding.listView.addOnScrollListener(OnBottomReachedListener { viewModel.onNextPage() })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val SOURCE_ARG = "source"
    }
}
