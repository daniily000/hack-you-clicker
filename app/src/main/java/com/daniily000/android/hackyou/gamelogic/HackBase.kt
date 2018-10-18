package com.daniily000.android.hackyou.gamelogic


/**
 * This class represents the hacking methods. Every hacking method should extend this class
 */
// TODO: REWORK THIS TO ENUM RAPIDLY!!!
abstract class HackBase {

    /**
     * An id of a hack type. Should be unique for every class until I
     * implement the mechanism of setting it unique automatically
     */
    abstract val ID: Int

    /** The name of this hack */
    abstract val name: String

    override fun hashCode(): Int {
        return ID.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this::class.java == other!!::class.java) {
            return other.hashCode() == this.hashCode()
        }
        return false
    }

    override fun toString(): String {
        return this.javaClass.name
    }
}