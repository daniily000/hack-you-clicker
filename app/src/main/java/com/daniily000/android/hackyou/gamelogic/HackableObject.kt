package com.daniily000.android.hackyou.gamelogic

enum class HackableObject {

    WEP {
        val CLICKS = 4
        override val displayName = "WEP"
        override val reward = 40
        override var hacksMap: MutableMap<HackMethod, Int> = object : HashMap<HackMethod, Int>() {
            init {
                put(HackMethod.PTW, CLICKS)
            }
        }
    },
    WPA {
        val CLICKS = 10
        override val displayName = "WPA-PSK"
        override val reward = 100
        override var hacksMap: MutableMap<HackMethod, Int> = object : HashMap<HackMethod, Int>() {
            init {
                put(HackMethod.HNDSHK_CAP, CLICKS)
            }
        }
    },
    WPA_WPS {
        val CLICKS_HNDSHK_CAP = 10
        val CLICKS_PIXIE_DUST = 3
        override val displayName = "WPA-PSK + WPS"
        override val reward = 100
        override var hacksMap: MutableMap<HackMethod, Int> = object : HashMap<HackMethod, Int>() {
            init {
                put(HackMethod.HNDSHK_CAP, CLICKS_HNDSHK_CAP)
                put(HackMethod.PIXIE_DUST, CLICKS_PIXIE_DUST)
            }
        }
    };

    companion object {

        private var objectArray: Array<HackableObject> = HackableObject.values()

        fun generate(): HackableObject {
            return HackableObject.objectArray.get((Math.random() * objectArray.size).toInt())
        }

    }

    /** The name of the hackable object */
    abstract val displayName: String

    /** The reward of pts a player gets on successful hack */
    abstract val reward: Int

    /** Stores an info about what can hack this object and how many clicks it needs */
    abstract var hacksMap: MutableMap<HackMethod, Int>

    /**
     * Returns how many hacks this object needs if able to hack, otherwise returns null
     * @param method HackableBase method
     * @return Int if hack is appropriate or null
     */
    fun hack(method: HackMethod): Int? {
        return hacksMap.get(method)
    }
}