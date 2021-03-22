package com.example.ktdeveloperslife.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.security.KeyStore
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.*

class NetworkServiceProvider {


    companion object {
        private var instance: Api? = null

        @Synchronized
        fun buildService(): Api {
            if (instance == null) {

                instance = Retrofit.Builder()
                        .client(getUnsafeOkHttpClient())
                        .baseUrl("https://developerslife.ru")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build().create(Api::class.java)

            }
                return instance as Api

        }

        private fun getUnsafeOkHttpClient(): OkHttpClient? {
            return try {
                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(
                        object : X509TrustManager {
                            @Throws(CertificateException::class)
                            override fun checkClientTrusted(
                                    chain: Array<X509Certificate?>?,
                                    authType: String?
                            ) {
                            }

                            @Throws(CertificateException::class)
                            override fun checkServerTrusted(
                                    chain: Array<X509Certificate?>?,
                                    authType: String?
                            ) {
                            }

                            override fun getAcceptedIssuers(): Array<X509Certificate?>? {
                                return arrayOf()
                            }
                        }
                )

                // Install the all-trusting trust manager
                val sslContext = SSLContext.getInstance("SSL")
                sslContext.init(null, trustAllCerts, SecureRandom())
                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory
                val trustManagerFactory: TrustManagerFactory =
                        TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm())
                trustManagerFactory.init(null as KeyStore?)
                val trustManagers: Array<TrustManager> =
                        trustManagerFactory.trustManagers
                check(!(trustManagers.size != 1 || trustManagers[0] !is X509TrustManager)) {
                    "Unexpected default trust managers:" + trustManagers.contentToString()
                }

                val trustManager =
                        trustManagers[0] as X509TrustManager


                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustManager)
                builder.hostnameVerifier(HostnameVerifier { _, _ -> true })
                builder.build()
            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }



    }

}