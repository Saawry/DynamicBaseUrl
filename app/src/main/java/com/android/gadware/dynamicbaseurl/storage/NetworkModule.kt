package com.android.gadware.dynamicbaseurl.storage

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.inject.Named
import javax.net.ssl.*


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {


    //@Singleton
    @Provides
    fun provideOkHttp() : OkHttpClient{
        return OkHttpClient.Builder()
            .build()
    }
    @Provides
    @Named("baseUrl")
    fun provideBaseUrl(): String {
        return "http://103.86.109.78:7001/"
    }




    @Provides
    //@Singleton
    @Qualifiers.EntryCloud
    fun provideRetrofit(okHttpClient: OkHttpClient,baseUrlManager: BaseUrlManager): Retrofit {
    //fun provideRetrofit(okHttpClient: OkHttpClient,@Named("baseUrl") baseUrl:String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrlManager.getBaseUrl())//            .baseUrl("https://imdb-api.com/en/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .client(okHttpClient)
            .build()
    }


    @Provides
    fun provideApiClient(@Qualifiers.EntryCloud retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }



    fun getUnsafeOkHttpClient(): OkHttpClient.Builder? {
        return try {
            val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<X509Certificate?>?, authType: String?) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            val sslContext = SSLContext.getInstance("TLS")
            sslContext.init(null, trustAllCerts, SecureRandom())

            val builder = OkHttpClient.Builder()
                .sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
                .hostnameVerifier { _, _ -> true }
            builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

}