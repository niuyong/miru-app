package miru.miaomint

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.hjq.toast.ToastUtils
import com.xuexiang.xupdate.XUpdate
import com.xuexiang.xupdate._XUpdate
import com.xuexiang.xupdate.service.OnFileDownloadListener
import com.xuexiang.xupdate.utils.FileUtils
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import java.io.File


class MainActivity: FlutterActivity() {
    var methodChannel: MethodChannel? = null

    @Override
    public override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine);
        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "channelName");
        methodChannel!!.setMethodCallHandler { call, result ->
            if (call.method.equals("downloadApk")) {
                XUpdate.newBuild(activity)
                    .updateUrl(mUpdateUrl)
                    .supportBackgroundUpdate(true)
                    .update();

                _XUpdate.startInstallApk(getContext(), FileUtils.getFileByPath(PathUtils.getFilePathByUri(getContext(), data.getData()))); //填写文件所在的路径
            } else {

            }
        }
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //https://0x0x.top/apks/Jump-release-v2023.11.28.apk
    }

    private fun downloadApk(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.setDataAndType(Uri.parse(url), "application/vnd.android.package-archive")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

        if (packageManager.canRequestPackageInstalls()) {
            startActivity(intent)
        } else {
            // 请求用户允许安装未知来源的APK
            startActivityForResult(
                Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES)
                    .setData(Uri.parse("package:$packageName")),
                REQUEST_INSTALL_PERMISSION
            )
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
