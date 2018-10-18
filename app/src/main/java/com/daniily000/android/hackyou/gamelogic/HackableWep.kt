package com.daniily000.android.hackyou.gamelogic

class HackableWep : HackableBase() {
    override val name = "WEP"
    override val reward = 40
    override var hacksMap: MutableMap<HackBase, Int> = HashMap()


    init {
        hacksMap.put(HackPtw(), 4)
    }

}