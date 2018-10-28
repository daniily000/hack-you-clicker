package com.daniily000.android.hackyou.gamedata

import java.util.*

class Chapter internal constructor(val chapterName: String, val levels: Array<Level>) {

    internal class Builder(val chapterName: String) {

        var levels = ArrayList<Level>()

        fun addLevel(level: Level): Builder {
            levels.add(level)
            return this
        }

        fun build(): Chapter {
            return Chapter(chapterName, levels.toTypedArray())
        }
    }


}