package com.zhongya666.library.constant;

interface Constant {

    object Sp {
        const val USER_NAME = "SP_USER_NAME"
        const val XIAN_HUA: String = "XIAN_HUA"
        const val VIDEO: String = "VIDEO"
    }

    object Bus{
        const val login_response: String = "login_response"
        const val app_event: String = "app_event"
        const val screen_snow: String = "screen_snow"
    }

    object Router {
        const val A_MAIN = "/app/MainActivity"
        const val F_MAIN = "/app/MainFragment"
        const val F_HOME = "/app/HomeFragment"

        const val E_MainActivity = "/example/MainActivity"
    }

    object Bundle {
        const val ArticleData = "ArticleData"
        const val BannerData = "BannerData"
        const val ProjectContent = "ProjectContent"
        const val ScoreResponse = "ScoreResponse"
        const val CollectResponse = "CollectResponse"
        const val SEARCH_HOT_KEY_LIST = "search_hot_key_list"
        const val To_Example = "To_Example"
    }

    companion object {
        const val TAG: String = "wzy"
        const val NAVIGATION_TO = "NAVIGATION_TO"
        const val KEY_PROJECT_ID = "KEY_PROJECT_ID"
    }
}
