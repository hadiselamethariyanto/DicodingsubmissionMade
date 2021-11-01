package com.bwx.made.ui.home

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.bwx.made.R
import com.bwx.made.utils.DataDummy
import com.bwx.made.utils.EspressoIdlingResource
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class HomeActivityTest {
    private val dummyMovie = DataDummy.generateDummyMovies()
    private val dummyTv = DataDummy.generateDummyTv()
    private val dummyDetailMovie = DataDummy.generateDetailMovie()
    private val dummyDetailTV = DataDummy.generateDetailTVDummy()

    @Before
    fun setUp() {
        ActivityScenario.launch(HomeActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @get:Rule
    var activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @Test
    fun loadMovies() {
        onView(withId(R.id.rvMovies)).check(matches(isDisplayed()))
        onView(withId(R.id.rvMovies)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyMovie.size
            )
        )
    }

    @Test
    fun loadDetailMovie() {
        onView(withId(R.id.rvMovies)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_movie)).check(matches(withText(dummyDetailMovie.title)))
        onView(withId(R.id.tv_releasedate_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_releasedate_movie)).check(matches(withText(dummyDetailMovie.releaseDate)))
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_category_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_category_movie)).check(matches(withText(dummyDetailMovie.genres?.get(0)?.name)))
        onView(withId(R.id.tv_duration_movie)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_duration_movie)).check(matches(withText(dummyDetailMovie.runtime.toString())))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(withText(dummyDetailMovie.overview)))
        onView(withId(R.id.rvCast)).check(matches(isDisplayed()))
    }

    @Test
    fun loadTv() {
        onView(withText("TV")).perform(click())
        onView(withId(R.id.rvTv)).check(matches(isDisplayed()))
        onView(withId(R.id.rvTv)).perform(
            RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(
                dummyTv.size
            )
        )
    }

    @Test
    fun loadDetailTv() {
        onView(withText("TV")).perform(click())
        onView(withId(R.id.rvTv)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_title_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_title_tv)).check(matches(withText(dummyDetailTV.name)))
        onView(withId(R.id.tv_releasedate_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_releasedate_tv)).check(matches(withText(dummyDetailTV.firstAirDate)))
        onView(withId(R.id.tv_category_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_category_tv)).check(matches(withText(dummyDetailTV.genres?.get(0)?.name)))
        onView(withId(R.id.tv_episode_tv)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_episode_tv)).check(matches(withText(dummyDetailTV.numberOfSeasons.toString())))
        onView(withId(R.id.tv_overview)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_overview)).check(matches(withText(dummyDetailTV.overview)))
        onView(withId(R.id.img_poster)).check(matches(isDisplayed()))
        onView(withId(R.id.rvEpisode)).check(matches(isDisplayed()))
    }
}