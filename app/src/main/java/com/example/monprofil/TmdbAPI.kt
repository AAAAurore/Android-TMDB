package com.example.monprofil

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.Query

GET("trending/movie/week")
suspend fun DerniersFilms(@Query("api_key") api_key: String): Model

val retrofit = Retrofit.Builder()
    .baseUrl("https://api.themoviedb.org/3/trending/movie/week?api_key=api_key&language=fr-FR")
    .addConverterFactory(MoshiConverterFactory.create())
    .build();

val service = retrofit.create(TmdbAPI::class.java)