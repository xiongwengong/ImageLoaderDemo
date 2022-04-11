package com.example.imageloaderdemo

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.imageloaderdemo.net.bean.*
import com.example.imageloaderdemo.ui.moment.components.UserInfoHeader
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class UserInfoHeaderTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var userInfo: UserInfo

    @Before
    fun setUp() {
        userInfo = getFakeUser()
    }

    @Test
    fun given_data_when_compose_then_nick_profileBg_profile_exist() {
        composeTestRule.setContent {
            UserInfoHeader(userInfo = userInfo)
        }
        composeTestRule.onRoot(useUnmergedTree = true).printToLog("TAG-HEAD")
        composeTestRule.onNodeWithText("Huan Huan").assertExists()
        composeTestRule.onNodeWithContentDescription("profile icon").assertExists()
        composeTestRule.onNodeWithTag("tag-profileBg").assertExists()
    }


}