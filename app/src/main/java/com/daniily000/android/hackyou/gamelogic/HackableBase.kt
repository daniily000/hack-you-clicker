package com.daniily000.android.hackyou.gamelogic

/**
 * This class represents the base for hackable objects. Every hackable should extend this class.
 * Moreover - every hackable should be declared in HackableObjects set if you want them in-game
 */
// TODO: REWORK THIS TO ENUM RAPIDLY!!!
abstract class HackableBase {

    /** The name of the hackable object */
    abstract val name: String

    /** The reward of pts a player gets on successful hack */
    abstract val reward: Int

    /** Stores an info about what can hack this object and how many clicks it needs */
    abstract var hacksMap: MutableMap<HackBase, Int>

    /**
     * Returns how many hacks this object needs if able to hack, otherwise returns null
     * @param method HackableBase method
     * @return Int if hack is appropriate or null
     */
    fun hack(method: HackBase): Int? {
        return hacksMap.get(method)
    }
}