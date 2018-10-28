package com.daniily000.android.hackyou.gamelogic

enum class HackMethod {

    PTW {
        override val readableName = "Pychkine-Tews-Weinmann WEP attack"
    },
    HNDSHK_CAP {
        override val readableName = "Handshake capture, decrypt and brutforce WPA/WPA2 PSK attack"
    },
    PIXIE_DUST {
        override val readableName = "Pixie Dust WPS attack"
    };

    /** The name of this hack */
    abstract val readableName: String

    override fun toString(): String {
        return this.javaClass.name
    }
}