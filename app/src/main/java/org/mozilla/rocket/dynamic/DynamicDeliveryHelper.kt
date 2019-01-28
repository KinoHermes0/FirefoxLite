package org.mozilla.rocket.dynamic

import android.content.Context
import android.util.Log
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest

class DynamicDeliveryHelper {

    companion object {
        @JvmStatic
        fun init(context: Context, success: () -> Int) {

            // Creates an instance of SplitInstallManager.
            val splitInstallManager = SplitInstallManagerFactory.create(context)

            // Creates a request to install a module.
            val request = SplitInstallRequest
                .newBuilder()
                // You can download multiple on demand modules per
                // request by invoking the following method for each
                // module you want to install.
                .addModule("history")
                .build()

            splitInstallManager
                .startInstall(request)
                .addOnSuccessListener { success() }
                .addOnFailureListener { toast("fail") }
        }

        private fun toast(s: String) {
//        Toast.makeText(context, s, Toast.LENGTH_LONG).show()
            Log.e("aaaa", "[$s]")
        }
    }
}