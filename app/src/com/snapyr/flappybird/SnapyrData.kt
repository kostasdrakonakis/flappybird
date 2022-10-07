package com.snapyr.flappybird
class SnapyrData private constructor() {


    fun destroy() {
        ourInstance = null
    }

    companion object {
        private var ourInstance: SnapyrData? = null

        val instance: SnapyrData
            get() {
                if (ourInstance == null)
                    ourInstance = SnapyrData()
                return ourInstance!!
            }
    }
        var env: String = "";
        var identifyKey: String = "";
        var identifyUserId: String = "";
        var identifyName: String = "";
        var identifyEmail: String = "";
        var identifyPhone: String = "";
}
