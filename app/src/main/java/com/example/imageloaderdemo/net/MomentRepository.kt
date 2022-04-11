package com.example.imageloaderdemo.net

import android.util.Log
import com.example.imageloaderdemo.net.bean.Tweet
import com.example.imageloaderdemo.net.bean.UserInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MomentRepository(
    private val momentService: MomentService =
        ServiceFactory.createApiService(MomentService::class.java)
) {
    /**
     * remote data source:
     * https://tw-mobile-xian.github.io/moments-data/user.json
     * https://tw-mobile-xian.github.io/moments-data/tweets.json
     */

    private val fakeUserStr =
        "{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar.png\",\"nick\":\"Huan Huan\",\"profile-image\":\"https://tw-mobile-xian.github.io/moments-data/images/user/profile-image.jpeg\",\"username\":\"hengzeng\"}"
    private val fakeTweetList =
        "[{\"comments\":[{\"content\":\"Good.\",\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/002.jpeg\",\"nick\":\"Lei Huang\",\"username\":\"leihuang\"}},{\"content\":\"Like it too\",\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/003.jpeg\",\"nick\":\"WeiDong Gu\",\"username\":\"weidong\"}}],\"content\":\"沙发！\",\"images\":[{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/001.jpeg\"},{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/002.jpeg\"},{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/003.jpeg\"}],\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/001.jpeg\",\"nick\":\"Cheng Yao\",\"username\":\"cyao\"}},{\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/000.jpeg\",\"nick\":\"Xin Ge\",\"username\":\"xinge\"}},{\"images\":[{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/004.jpeg\"}],\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/004.jpeg\",\"nick\":\"Yang Luo\",\"username\":\"yangluo\"}},{\"images\":[{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/005.jpeg\"}],\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/005.jpeg\",\"nick\":\"Jianing Zheng\",\"username\":\"jianing\"}},{},{\"content\":\"Unlike many proprietary big data processing platform different, Spark is built on a unified abstract RDD, making it possible to deal with different ways consistent with large data processing scenarios, including MapReduce, Streaming, SQL, Machine Learning and Graph so on. To understand the Spark, you have to understand the RDD. \",\"images\":[{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/006.jpeg\"}],\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/006.jpeg\",\"nick\":\"Wei Fan\",\"username\":\"weifan\"}},{\"comments\":[{\"content\":\"Good.\",\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/008.jpeg\",\"nick\":\"Yanzi Li\",\"username\":\"yanzili\"}},{\"content\":\"Like it too\",\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/001.jpeg\",\"nick\":\"Jing Zhao\",\"username\":\"jingzhao\"}}],\"content\":\"这是第二页第一条\",\"images\":[{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/007.jpeg\"},{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/008.jpeg\"},{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/009.jpeg\"},{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/010.jpeg\"}],\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/007.jpeg\",\"nick\":\"Xin Guo\",\"username\":\"xinguo\"}},{\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/000.jpeg\",\"nick\":\"Niang Niang\",\"username\":\"hengzeng\"}},{\"images\":[{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/011.jpeg\"}],\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/002.jpeg\",\"nick\":\"Jian Zhang\",\"username\":\"jizhang\"}},{\"images\":[{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/012.jpeg\"}],\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/003.jpeg\",\"nick\":\"Yanzi Li\",\"username\":\"yanzi\"}},{},{},{},{\"comments\":[{\"content\":\"Good.\",\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/005.jpeg\",\"nick\":\"Cheng Yao\",\"username\":\"cyao\"}}],\"content\":\"As a programmer, we should as far as possible away from the Windows system. However, the most a professional programmer, and very difficult to bypass Windows this wretched existence but in fact very real, then how to quickly build a simple set of available windows based test environment? See Qiu Juntao\\u0027s blog. \",\"images\":[{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/013.jpeg\"}],\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/004.jpeg\",\"nick\":\"Jianing Zheng\",\"username\":\"jianing\"}},{\"comments\":[],\"content\":\"第9条！\",\"images\":[{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/014.jpeg\"},{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/015.jpeg\"},{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/016.jpeg\"},{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/016.jpeg\"},{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/017.jpeg\"},{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/018.jpeg\"},{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/019.jpeg\"},{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/020.jpeg\"},{\"url\":\"https://tw-mobile-xian.github.io/moments-data/images/tweets/021.jpeg\"}],\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/006.jpeg\",\"nick\":\"Jianing Zheng\",\"username\":\"jianing\"}},{\"comments\":[],\"content\":\"第10条！\",\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/007.jpeg\",\"nick\":\"Xin Guo\",\"username\":\"xinguo\"}},{\"content\":\"楼下保持队形，第11条\",\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/008.jpeg\",\"nick\":\"Yanzi Li\",\"username\":\"yanzi\"}},{\"content\":\"第12条\",\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/001.jpeg\",\"nick\":\"Xin Guo\",\"username\":\"xinguo\"}},{\"content\":\"第13条\",\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/002.jpeg\",\"nick\":\"Yanzi Li\",\"username\":\"yanzili\"}},{\"content\":\"第14条\",\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/003.jpeg\",\"nick\":\"Xin Guo\",\"username\":\"xinguo\"}},{},{\"content\":\"- The data json will be hosted in https://tw-mobile-xian.github.io/moments-data/ - Nibs or storyboards are prohibited- Implement cache mechanism by you own if needed- Unit tests is appreciated.- Functional programming is appreciated- Deployment Target should be 7.0- Utilise Git for source control, for git log is the directly evidence of the development process- Utilise GCD for multi-thread operation- Only binary, framework or cocopods dependency is allowed. do not copy other developer\\u0027s source code(*.h, *.m) into your project- Keep your code clean as much as possible\",\"sender\":{\"avatar\":\"https://tw-mobile-xian.github.io/moments-data/images/user/avatar/004.jpeg\",\"nick\":\"Huan Huan\",\"username\":\"hengzeng\"}}]"


    fun getLocalUserInfo(): UserInfo {
        return Gson().fromJson(fakeUserStr, UserInfo::class.java)
    }

    fun getLocalTweets(): List<Tweet> {
        val typeToken = object : TypeToken<List<Tweet>>() {}.type
        return Gson().fromJson(fakeTweetList, typeToken)
    }

    suspend fun getUserInfo(): UserInfo {
        Log.e("TAG", "step 2-1")
        return momentService.getUserInfo()
    }

    suspend fun getTweets(): List<Tweet> {
        Log.e("TAG", "step 2-2")
        return momentService.getTweets()
    }

}