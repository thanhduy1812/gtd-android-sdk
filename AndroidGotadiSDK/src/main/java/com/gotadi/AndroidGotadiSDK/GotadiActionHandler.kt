package com.gotadi.AndroidGotadiSDK

import android.content.Context

interface GotadiCallback {
    fun onCallback(data: String) {/* optional implementation */}
    fun onCallPayment(gotadiActivity: Context, bookingNumber: String) {/* optional implementation */}
}

class GotadiActionHandler private constructor(){
    var paymentCallback = handleReceiveBooking()
    var bookkingResultCallback: GotadiCallback? = null
    fun doPayment() {
        println("GotadiActionHandler Payment")
    }

    companion object {
        val instance by lazy { GotadiActionHandler() }
    }

    fun handleReceiveBooking() : (result: String?) -> Unit {
        return fun(result: String?) {
            println("GotadiActionHandler")
            println(result)
        }
    }
}