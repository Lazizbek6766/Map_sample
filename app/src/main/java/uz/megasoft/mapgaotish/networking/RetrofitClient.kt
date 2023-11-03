package uz.megasoft.mapgaotish.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient

object RetrofitClient {
    private const val GOOGLE_BASE_URL = "https://maps.googleapis.com/maps/api/"

    fun retrofitClient(): RetrofitCalls {
        val retrofit = Retrofit.Builder()
                .baseUrl(GOOGLE_BASE_URL)
                .client(OkHttpClient().newBuilder().build())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        return retrofit.create(RetrofitCalls::class.java)
    }
}