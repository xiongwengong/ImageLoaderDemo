package com.example.imageloaderdemo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.imageloaderdemo.net.MomentRepository
import com.example.imageloaderdemo.ui.moment.MomentComponent
import com.example.imageloaderdemo.ui.moment.MomentViewModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MomentComponentTests {
    @get:Rule
    val composeTestRule = createComposeRule()

    private val mockRepository: MomentRepository = mockk(relaxed = true)
    private val viewModel: MomentViewModel = MomentViewModel(mockRepository)


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun given_fake_data_when_create_moment_view_then_show_component() = runTest {
        // given
        coEvery { mockRepository.getUserInfo() } returns getFakeUser()
        coEvery { mockRepository.getTweets() } returns getFakeTweets()

        //when
        composeTestRule.setContent {
            MomentComponent(momentViewModel = viewModel)
        }

        //then
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("TAG")
        composeTestRule.onNodeWithText("Lei Huang").assertExists()
        composeTestRule.onNodeWithContentDescription("Top stories for you").assertExists()
    }

}