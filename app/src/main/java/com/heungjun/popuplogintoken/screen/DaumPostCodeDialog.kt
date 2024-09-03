package com.heungjun.popuplogintoken.screen

import android.annotation.SuppressLint
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun DaumPostCodeDialog(
    onDismissRequest: () -> Unit,
    onPostCodeSelected: (String, String) -> Unit // 우편번호와 주소를 반환
) {
    val context = LocalContext.current

    AlertDialog(
        onDismissRequest = onDismissRequest,
        text = {
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                webViewClient = WebViewClient()
                webChromeClient = WebChromeClient()

                addJavascriptInterface(object {
                    @JavascriptInterface
                    fun processDATA(data: String) {
                        // JavaScript에서 전달된 데이터를 분리합니다.
                        val parsedData = data.split(",")
                        if (parsedData.size == 2) {
                            val postcode = parsedData[0].trim()
                            val address = parsedData[1].trim()
                            onPostCodeSelected(postcode, address)
                        }
                        onDismissRequest() // 주소 선택 후 다이얼로그 닫기
                    }
                }, "Android")

                // Daum 우편번호 API 사용
                loadUrl("https://t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js")
                loadUrl("""
                    javascript:
                    (function() {
                        new daum.Postcode({
                            oncomplete: function(data) {
                                // 사용자가 주소를 선택했을 때의 콜백 함수.
                                Android.processDATA(data.zonecode + "," + data.address);
                            }
                        }).open();
                    })();
                """.trimIndent())
            }
        },
        confirmButton = {
            TextButton(onClick = onDismissRequest) {
                Text("닫기")
            }
        }
    )
}