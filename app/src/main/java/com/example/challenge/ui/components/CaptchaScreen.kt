package com.example.challenge.ui.components

import android.annotation.SuppressLint
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@SuppressLint("SetJavaScriptEnabled")
@Composable
fun CaptchaScreen(captchaUrl: String, onCaptchaComplete: () -> Unit) {

    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            WebView(context).apply {
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                loadUrl(captchaUrl)

                webViewClient = object : WebViewClient() {
                    override fun onPageFinished(view: WebView?, url: String?) {
                        view?.evaluateJavascript(
                            """
                            (function() {
                                var successElement = document.querySelector('.recaptcha-success');
                                return successElement ? successElement.textContent.trim() : null;
                            })();
                            """.trimIndent()
                        ) { result ->
                            result?.let {
                                if (it != "null") {
                                    onCaptchaComplete()
                                }
                            }
                        }
                    }
                }
            }
        }
    )
}