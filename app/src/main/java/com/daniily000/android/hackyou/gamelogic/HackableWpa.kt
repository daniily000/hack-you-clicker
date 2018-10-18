package com.daniily000.android.hackyou.gamelogic

class HackableWpa : HackableBase() {
    override val name = "WPA-PSK"
    override val reward = 100
    override var hacksMap: MutableMap<HackBase, Int> = HashMap()


    init {
        hacksMap.put(HackHandshakeCapture(), 10)
    }

}