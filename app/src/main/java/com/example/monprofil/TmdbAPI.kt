package com.example.monprofil

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    //https://api.themoviedb.org/3/trending/movie/week?api_key=b7a6cf89c9ecb4eb6d5e4d8849cc4d0d&language=fr-FR
    @GET("trending/movie/week")
    suspend fun GetDerniersFilms(@Query("api_key") api_key: String,
                              @Query("language") language: String): ModelDerniersFilms

    /*//https://api.themoviedb.org/3/trending/tv/week?api_key=b7a6cf89c9ecb4eb6d5e4d8849cc4d0d&language=fr-FR
    @GET("trending/tv/week")
    suspend fun GetDernieresSeries(@Query("api_key") api_key: String,
                              @Query("language") language: String): ModelDernieresSeries

    //https://api.themoviedb.org/3/trending/person/week?api_key=b7a6cf89c9ecb4eb6d5e4d8849cc4d0d&language=fr-FR
    @GET("trending/person/week")
    suspend fun GetDernieresPersonnalites(@Query("api_key") api_key: String,
                              @Query("language") language: String): ModelDernieresPersonnalites

    //https://api.themoviedb.org/3/movie/{idFilm}/credits?api_key=b7a6cf89c9ecb4eb6d5e4d8849cc4d0d&language=fr-FR
    GET("movie/{idFilm}/credits")
    suspend fun GetDistributionFilm(@Path("idFilm") idFilm: Int,
                             @Query("api_key") api_key: String,
                             @Query("language") language: String): ModelDistributionFilm

     //https://api.themoviedb.org/3/tv/{idSerie}/credits?api_key=b7a6cf89c9ecb4eb6d5e4d8849cc4d0d&language=fr_FR
    GET("tv/{idSerie}/credits")
    suspend fun GetDistributionSerie(@Path("idSerie") idSerie: Int,
                             @Query("api_key") api_key: String,
                             @Query("language") language: String): ModelDistributionSerie

    //https://api.themoviedb.org/3/person/{idActeur}?api_key=b7a6cf89c9ecb4eb6d5e4d8849cc4d0d&language=fr-FR
    GET("person/{idActeur}")
    suspend fun GetActeur(@Path("idActeur") idActeur: Int,
                       @Query("api_key") api_key: String,
                       @Query("language") language: String): ModelDateDeNaissance*/
}