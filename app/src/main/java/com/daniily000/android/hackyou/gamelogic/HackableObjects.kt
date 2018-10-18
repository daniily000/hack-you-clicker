package com.daniily000.android.hackyou.gamelogic

class HackableObjects() {

    companion object {

        private var objectsArray: Array<HackableBase> = arrayOf(HackableWep(), HackableWpa(), HackableWpaWps())

        fun generate(): HackableBase {
            return HackableObjects.objectsArray.get((Math.random() * objectsArray.size).toInt())
        }

    }

}