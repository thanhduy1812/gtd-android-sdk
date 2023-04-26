package com.gotadi.AndroidGotadiSDK

import android.content.Context
import android.os.Bundle
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.MethodChannel
import java.util.logging.Level
import java.util.logging.Logger

class GotadiSearchBookActivity: FlutterActivity() {
    private val CHANNEL = "samples.flutter.dev/battery"
    private var cachedEngine: FlutterEngine? = null
//    lateinit var paymentCallback: (result: String?) -> Unit
//    private lateinit var callback: GotadiCallback
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun provideFlutterEngine(context: Context): FlutterEngine? {
        // If the cached engine is null, create a new one and cache it
        cachedEngine = FlutterEngineCache.getInstance().get("gotadi_sdk")
        if (cachedEngine == null) {

            cachedEngine = FlutterEngine(context.applicationContext)
            cachedEngine!!.navigationChannel.setInitialRoute("/homeVIB")
        }
        return cachedEngine
    }
    override fun configureFlutterEngine(flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)
        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, CHANNEL).setMethodCallHandler { call, result ->
            // This method is invoked on the main thread
            // TODO
            if (call.method == "getBatteryLevel") {
                Logger.getLogger("FlutterActivity").log(Level.INFO,"getBatteryLevel")

            } else {
                result.notImplemented()
            }
        }

        flutterEngine.navigationChannel.setMethodCallHandler { call, result ->
            if (call.method == "pop.partner.home") {
                finish()
            }
            else if (call.method == "push.partner.payment") {
                val bookingNumber = call.arguments as? String
                bookingNumber?.let {
                    val actionHandler = GotadiActionHandler.instance
                    actionHandler.paymentCallback(bookingNumber)
                    actionHandler.bookkingResultCallback?.onCallback(bookingNumber)
                    actionHandler.bookkingResultCallback?.onCallPayment(gotadiActivity = context, bookingNumber = bookingNumber)
                }
//                println(bookingNumber)
//                println("Start activity Payment")
            }
        }
    }

    fun handleReceiveBooking() : (result: String?) -> Unit {
        return fun(result: String?) {
            println(result)
        }
    }


}