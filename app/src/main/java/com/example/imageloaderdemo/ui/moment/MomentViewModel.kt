package com.example.imageloaderdemo.ui.moment

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.imageloaderdemo.net.MomentRepository
import com.example.imageloaderdemo.net.bean.Tweet
import com.example.imageloaderdemo.net.bean.UserInfo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MomentViewModel(private val momentRepository: MomentRepository = MomentRepository()) :
    ViewModel() {
    var viewState by mutableStateOf(MomentsState())
        private set

    init {
        initData()
    }

    private fun initData() {
        viewModelScope.launch(CoroutineExceptionHandler { context, throwable ->
            Log.e("TAG", throwable.message ?: "unknown error")
        }) {
            val userInfoDiffer = async { momentRepository.getUserInfo() }
            val tweetsDiffer = async { momentRepository.getTweets() }
            viewState = MomentsState(userInfo = userInfoDiffer.await(), tweets = tweetsDiffer.await())
        }
    }

    data class MomentsState(
        var userInfo: UserInfo? = null,
        var tweets: List<Tweet> = emptyList(),
    )
}