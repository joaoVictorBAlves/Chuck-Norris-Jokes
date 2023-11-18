package com.example.chucknorrisv2

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

data class ChuckNorrisJoke(
    val id: String,
    val value: String
)

interface ChuckNorrisService {
    @GET("jokes/random")
    suspend fun getRandomJoke(): ChuckNorrisJoke
}

class MainActivity : AppCompatActivity() {

    private lateinit var jokeTxt : TextView
    private lateinit var retrofit: Retrofit
    private lateinit var chuckNorrisService: ChuckNorrisService
    private lateinit var generateButton: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        jokeTxt = findViewById(R.id.joke);

        generateButton = findViewById(R.id.button)

        retrofit = Retrofit.Builder()
            .baseUrl("https://api.chucknorris.io/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        chuckNorrisService = retrofit.create(ChuckNorrisService::class.java)

        fetchRandomJoke()

        generateButton.setOnClickListener(View.OnClickListener {
            fetchRandomJoke()
        })
    }




    private fun fetchRandomJoke() {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val joke = chuckNorrisService.getRandomJoke()
                withContext(Dispatchers.Main) {
                    jokeTxt.text = joke.value
                }
            } catch (e: Exception) {
                // Handle error
                e.printStackTrace()
            }
        }
    }
}