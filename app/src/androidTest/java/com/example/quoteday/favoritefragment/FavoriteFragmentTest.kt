package com.example.quoteday.favoritefragment

import androidx.navigation.NavController
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.hasDescendant
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.example.favorite.R
import com.example.favorite.domain.entity.QuoteModalFavorite
import com.example.favorite.domain.repository.FavoriteRepository
import com.example.favorite.presentation.FavoriteFragment
import com.example.quoteday.BaseTest
import com.example.quoteday.di.favorite.FavoriteRepositoryModule
import com.example.quoteday.testutils.espresso.atPosition
import com.example.quoteday.testutils.espresso.scrollToPosition
import com.example.quoteday.testutils.espresso.withItemsCount
import com.example.quoteday.launchNavHiltFragment
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import dagger.hilt.components.SingletonComponent
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Singleton

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
@UninstallModules(FavoriteRepositoryModule::class)
@MediumTest
class FavoriteFragmentTest : BaseTest() {

    @RelaxedMockK
    lateinit var navController: NavController

    private val quotesModalFavoriteOne = QuoteModalFavorite(
        author = "The first author",
        quote = "The first quote",
        id = 1
    )

    private val quotesModalFavoriteTwo = QuoteModalFavorite(
        author = "The second author",
        quote = "The second quote",
        id = 2
    )

    private val quotesFlow =
        MutableStateFlow(listOf(quotesModalFavoriteOne, quotesModalFavoriteTwo))

    private lateinit var scenario: AutoCloseable

    @Before
    override fun setUp() {
        super.setUp()
        every { favoriteQuotesDbUseCase.invoke() } returns quotesFlow
        scenario = launchNavHiltFragment<FavoriteFragment>(navController)
    }

    @Test
    fun quotesAndHeadersAreDisplayedInList() {

        // assert
        onView(withId(R.id.rv_favorite_quotes))
            .perform(scrollToPosition(0)).check(matches(
                atPosition(0, allOf(
                            hasDescendant(
                                allOf(withId(R.id.quotes_favorite_text), ViewMatchers.withText("The first quote"))),
                            hasDescendant(
                                allOf(withId(R.id.quote_favorite_author), ViewMatchers.withText("The first author")))

                        )
                    )
                )
            )

        onView(withId(R.id.rv_favorite_quotes))
            .perform(scrollToPosition(1)).check(matches(
                atPosition(1, allOf(
                            hasDescendant(
                                allOf(withId(R.id.quotes_favorite_text), ViewMatchers.withText("The second quote"))),
                            hasDescendant(
                                allOf(withId(R.id.quote_favorite_author), ViewMatchers.withText("The second author"))),

                            )
                    )
                )
            )

        onView(withId(R.id.rv_favorite_quotes)).check(matches(withItemsCount(2)))
    }

    @After
    fun tearDown() {
        scenario.close()
    }

    @Module
    @InstallIn(SingletonComponent::class)
    class FakeRepositoryModule {
        @Provides
        @Singleton
        fun bindRouter(): FavoriteRepository {
            return mockk(relaxed = true)
        }
    }


}

