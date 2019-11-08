package com.victor.clips.util

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: Constant.kt
 * Author: Victor
 * Date: 2018/8/17 13:47
 * Description: 
 * -----------------------------------------------------------------
 */
class Constant {
    companion object {
        const val HEADER_HIDE_ANIM_DURATION         = 300
        const val BG_COLOR_MAX                      = 255f
        const val BG_COLOR_MIN                      = 232f
        const val INTENT_DATA_KEY                   = "INTENT_DATA_KEY"
        const val INTENT_ACTION_KEY                 = "INTENT_ACTION_KEY"
        const val CATEGORY_POSITION_KEY             = "CATEGORY_POSITION_KEY"
        const val MA_DATA = "madata"
        const val SHARE_TYPE                        = "text/plain";

    }

    class Msg {
        companion object {
            const val ENTER_HAPPY_COAST             = 0x1001
        }
    }
    class Action {
        companion object {
            const val CATEGORY                      = 0x2001
            const val WEEKLY_RANKING                = 0x2002
            const val MONTHLY_RANKING               = 0x2003
            const val TOTAL_RANKING                 = 0x2004
            const val FOLLOW                        = 0x1005
        }
    }

}