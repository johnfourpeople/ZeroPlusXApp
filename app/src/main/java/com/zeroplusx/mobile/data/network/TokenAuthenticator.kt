package com.zeroplusx.mobile.data.network

import com.zeroplusx.mobile.R
import com.zeroplusx.mobile.ui.application.ZeroPlusXApplication.Companion.applicationContext
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**Create your own string resource news_api_key with api key definition to get this working*/
object TokenAuthenticator : Authenticator {
    private const val API_KEY_HEADER = "X-Api-Key"

    override fun authenticate(route: Route?, response: Response): Request? {
        return if (response.priorResponse != null) {
            response.request
                .newBuilder()
                .addHeader(API_KEY_HEADER, applicationContext.getString(R.string.news_api_key))
                .build()
        } else {
            null
        }
    }
}
