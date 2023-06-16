package com.example.beansbay.network

import android.util.Log
import android.webkit.CookieManager
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    lateinit var cookieManager: CookieManager
    fun getApiService(): ApiService {
        val loggingInterceptor =
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        val client = OkHttpClient.Builder()
            .cookieJar(object : CookieJar {

                /**
                 * @param url
                 * @param cookies list of cookies get in api response
                 */
                override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {

                    cookieManager = CookieManager.getInstance()
                    for (cookie in cookies) {
                        cookieManager.setCookie(url.toString(), cookie.toString())
                        Log.e(
                            "ok",
                            "saveFromResponse :  Cookie url : " + url.toString() + cookie.toString()
                        )
                    }
                }

                /**
                 * @param url
                 *
                 * adding cookies with request
                 */
                override fun loadForRequest(url: HttpUrl): List<Cookie> {
                    cookieManager = CookieManager.getInstance()

                    val cookies: ArrayList<Cookie> = ArrayList()
                    if (cookieManager.getCookie(url.toString()) != null) {
                        val splitCookies =
                            cookieManager.getCookie(url.toString()).split("[,;]".toRegex())
                                .dropLastWhile { it.isEmpty() }.toTypedArray()
                        for (i in splitCookies.indices) {
                            cookies.add(Cookie.parse(url, splitCookies[i].trim { it <= ' ' })!!)
                            Log.e(
                                "ok",
                                "loadForRequest :Cookie.add ::  " + Cookie.parse(
                                    url,
                                    splitCookies[i].trim { it <= ' ' })!!
                            )
                        }
                    }
                    return cookies
                }
            })
            .addInterceptor(loggingInterceptor)
            .build()
        val retrofit = Retrofit.Builder()
            .baseUrl("https://capstone-4cffc.et.r.appspot.com/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(ApiService::class.java)
    }
}