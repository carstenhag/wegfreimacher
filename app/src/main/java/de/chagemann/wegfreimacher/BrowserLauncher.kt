package de.chagemann.wegfreimacher

import android.content.Context
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import javax.inject.Inject

class BrowserLauncher @Inject constructor() {
    private companion object {
        const val wegliBaseUrl = "https://weg.li"
    }

    fun openNoticeDetails(context: Context, noticeToken: String) {
        val intent = CustomTabsIntent.Builder().build()
        val url = "${wegliBaseUrl}/notices/$noticeToken"
        intent.launchUrl(context, Uri.parse(url))
    }

    fun openUserProfile(context: Context) {
        val intent = CustomTabsIntent.Builder().build()
        val url = "$wegliBaseUrl/user"
        intent.launchUrl(context, Uri.parse(url))
    }
}
