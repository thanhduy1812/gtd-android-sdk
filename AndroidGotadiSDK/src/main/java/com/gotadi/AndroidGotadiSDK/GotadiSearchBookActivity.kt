package com.gotadi.AndroidGotadiSDK

import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import java.util.logging.Level
import java.util.logging.Logger

class GotadiSearchBookActivity: FlutterActivity() {
    private val CHANNEL = "samples.flutter.dev/battery"
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
    }
}