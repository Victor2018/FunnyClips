package com.victor.clips.data

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: Direction.java
 * Author: Victor
 * Date: 2018/9/6 11:04
 * Description: Direction sealed class used as enum for defining directions
 * -----------------------------------------------------------------
 */
sealed class Direction {
    class LEFT : Direction()
    class RIGHT : Direction()
    class UP : Direction()
    class DOWN : Direction()
    class NONE : Direction()
}