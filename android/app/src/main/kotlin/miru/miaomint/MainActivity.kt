package miru.miaomint

import android.content.Intent
import android.util.Log
import androidx.annotation.NonNull
import com.hjq.toast.ToastUtils
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import miru.miaomint.update.UpdateManager

class MainActivity: FlutterActivity() {
    private var methodChannel: MethodChannel? = null
    private val TAG = "MainActivity";

    @Override
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "channelName");
        methodChannel!!.setMethodCallHandler { call, _ ->
            if (call.method.equals("downloadApk")) {
                ToastUtils.show("哈哈哈")
                context.filesDir.list().forEach { f ->
                    Log.v(TAG, "哈哈哈->" + f)
                }
                context.filesDir.listFiles().forEach { file ->
                    Log.v(TAG, "哈哈哈" + file.name)
                }

                UpdateManager(this, UpdateManager.CHECK_AUTO).checkUpdate()
            } else {

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_INSTALL_PERMISSION) {
            if (packageManager.canRequestPackageInstalls()) {
                // 用户允许了，重新尝试下载
                // 注意：这里应该重新触发下载逻辑，可能需要一些状态管理
            } else {
                // 用户拒绝了
            }
        }
    }

    companion object {
        private const val REQUEST_INSTALL_PERMISSION = 1
    }
}
