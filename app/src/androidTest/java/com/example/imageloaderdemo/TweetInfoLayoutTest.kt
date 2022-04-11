package com.example.imageloaderdemo

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.imageloaderdemo.net.MomentRepository
import com.example.imageloaderdemo.net.bean.Comment
import com.example.imageloaderdemo.net.bean.Sender
import com.example.imageloaderdemo.net.bean.Tweet
import com.example.imageloaderdemo.net.bean.TweetImage
import com.example.imageloaderdemo.ui.moment.components.TweetInfoLayout
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TweetInfoLayoutTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var tweet: Tweet

    @Before
    fun setUp() {
        //given
        tweet = getValidFakeTweet()
    }

    @Test
    fun given_full_data_with_images_and_content_when_compose__then_show_component() {
        //when
        composeTestRule.setContent {
            TweetInfoLayout(tweet)
        }

        //then
        composeTestRule.onNodeWithText("Cheng Yao").assertExists()
        composeTestRule.onNodeWithText("沙发！").assertExists()
        composeTestRule.onNodeWithTag("sender-profile").assertExists()

        tweet.images?.size?.let {
            composeTestRule.onAllNodesWithTag("tweet-image").assertCountEquals(it)
        }
        composeTestRule.onRoot().printToLog("TAG")
    }

    @Test
    fun given_empty_Images_and_empty_comments_when_compose_moment_then_image_and_comment_not_exist() {
        // given
        tweet.images = emptyList()
        tweet.comments = emptyList()

        //when
        composeTestRule.setContent {
            TweetInfoLayout(tweet)
        }

        //then
        composeTestRule.onNodeWithTag("sender-profile").assertExists()
        composeTestRule.onAllNodesWithTag("tweet-image").assertCountEquals(0)
        composeTestRule.onAllNodesWithTag("tweet-comment").assertCountEquals(0)
    }

}