package com.daniily000.android.hackyou.gamedata

import com.daniily000.android.hackyou.gamelogic.HackableObject

class Level internal constructor(val time: Int, val points: Int, hackableObjects: Array<HackableObject>) {

    class Builder(val time: Int, val points: Int) {

        val hackableObjects = ArrayList<HackableObject>()

        fun addObject(hackableObject: HackableObject): Builder {
            hackableObjects.add(hackableObject)
            return this
        }

        fun addObject(hackableObject: String): Builder {
            addObject(HackableObject.valueOf(hackableObject))
            return this
        }

        fun build(): Level {
            return Level(time, points, hackableObjects.toTypedArray())
        }
    }

    override fun toString(): String {
        return "Level(time=$time, points=$points)"
    }
}