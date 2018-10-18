package com.daniily000.android.hackyou.gamelogic

class HackableWpaWps : HackableBase() {

    override val name = "WPA-PSK + WPS"
    override val reward = 100
    override var hacksMap: MutableMap<HackBase, Int> = HashMap()

    init {
        hacksMap.put(HackHandshakeCapture(), 10)
        hacksMap.put(HackPixieDust(), 3)
    }

}