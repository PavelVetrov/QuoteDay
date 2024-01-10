package com.example.quoteday

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.favorite.domain.usecase.DeleteFavoriteQuoteUseCase
import com.example.favorite.domain.usecase.GetFavoriteQuotesUseCase
import com.example.quoteday.testutils.rule.TestViewModelScopeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import io.mockk.junit4.MockKRule
import org.junit.Before
import org.junit.Rule
import javax.inject.Inject
@HiltAndroidTest
open class BaseTest {

    @get:Rule
    val testViewModelScopeRule = TestViewModelScopeRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mockkRule = MockKRule(this)

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var favoriteQuotesDbUseCase: GetFavoriteQuotesUseCase

    @Inject
    lateinit var deleteFavoriteQuoteUseCase: DeleteFavoriteQuoteUseCase

    @Before
    open fun setUp() {
        hiltRule.inject()
    }
}