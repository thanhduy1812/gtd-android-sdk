package com.gotadi.AndroidGotadiSDK

import android.content.Context
import android.content.Intent
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor



class AndroidGotadiSDK(context: Context, setting: GotadiPartnerSetting) {
    private lateinit var flutterEngine : FlutterEngine
    val actionHandler = GotadiActionHandler.instance

    var context = context
    init {
        // Cache the FlutterEngine to be used by FlutterActivity.
        var flutterEngineCached = FlutterEngineCache.getInstance().get("gotadi_sdk")
        if (flutterEngineCached == null) {
            // Instantiate a FlutterEngine.
            flutterEngine = FlutterEngine(context)
            // Initial router
            flutterEngine.navigationChannel.setInitialRoute("/homeVIB")
            // Start executing Dart code to pre-warm the FlutterEngine.
            flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
            )

            FlutterEngineCache
                .getInstance()
                .put("gotadi_sdk", flutterEngine)
        } else {
            flutterEngine = flutterEngineCached
        }

        // Config token and language and env uat/prod
        var arguments: HashMap<String, String> = HashMap()
        arguments.putIfAbsent("env", setting.env)
        arguments.putIfAbsent("partner", setting.partnerName)
        arguments.putIfAbsent("locale", setting.language)
        arguments.putIfAbsent("token", setting.token)
        println(arguments)
//        flutterEngine.localizationChannel.channel.invokeMethod("partner.app.scheme",arguments)
        flutterEngine.localizationChannel.channel.setMethodCallHandler { call, result ->
            if (call.method == "partner.app.scheme") {
                result.success(arguments)
            }
//            result.notImplemented()
        }

    }

    fun getGotadiIntent(): Intent? {
        val intent = FlutterActivity.withCachedEngine("gotadi_sdk").build(this.context)
        return intent
    }

    fun createGotadiIntent(): Intent {
        val intent = Intent(this.context, GotadiSearchBookActivity::class.java)
        return intent
    }

    fun restoreGotadiSDKRouter() {
        var flutterEngineCache = FlutterEngineCache.getInstance().get("gotadi_sdk")
        flutterEngineCache?.navigationChannel?.setInitialRoute("/homeVIB")
    }

    fun dispose() {
        var flutterEngineCache = FlutterEngineCache.getInstance().get("gotadi_sdk")
        flutterEngineCache?.destroy()
    }
}

