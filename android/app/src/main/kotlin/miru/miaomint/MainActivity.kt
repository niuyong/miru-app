package miru.miaomint

import io.flutter.embedding.android.FlutterActivity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.webkit.DownloadListener
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.annotation.Nullable

class MainActivity: FlutterActivity() {
    private lateinit var webView: WebView

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        webView = WebView(this)
//        setContentView(webView)
//
//        webView.webViewClient = object : WebViewClient() {
//            override fun shouldOverrideUrlLoading(view: WebView?, request: android.webkit.WebResourceRequest?): Boolean {
//                val url = request?.url?.toString()
//                if (url?.endsWith(".apk") == true) {
//                    downloadApk(url)
//                    return true
//                }
//                return false
//            }
//        }
//
//        webView.setDownloadListener { url, userAgent, contentDisposition, mimetype, contentLength ->
//            downloadApk(
//                url
//            )
//        }
//
//        webView.loadUrl("https://0x0x.top/apks/Jump-release-v2023.11.28.apk") // 你的下载页面或APK链接
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
