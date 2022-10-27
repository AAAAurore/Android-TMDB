package com.example.monprofil

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbAPI {
    @GET("trending/movie/week")
    suspend fun GetFilms(@Query("api_key") api_key: String,
                              @Query("language") language: String): ModelFilms

    @GET("trending/tv/week")
    suspend fun GetSeries(@Query("api_key") api_key: String,
                              @Query("language") language: String): ModelSeries

    @GET("trending/person/week")
    suspend fun GetActeurs(@Query("api_key") api_key: String,
                              @Query("language") language: String): ModelPersonnalites

    @GET("movie/{idFilm}/credits")
    suspend fun GetDistributionFilm(@Path("idFilm") idFilm: Int,
                             @Query("api_key") api_key: String,
                             @Query("language") language: String): ModelDistributionFilm

    @GET("tv/{idSerie}/credits")
    suspend fun GetDistributionSerie(@Path("idSerie") idSerie: Int,
                             @Query("api_key") api_key: String,
                             @Query("language") language: String): ModelDistributionSerie

    @GET("movie/{idFilm}")
    suspend fun GetFilm(@Path("idFilm") idFilm: Int,
                        @Query("api_key") api_key: String,
                        @Query("language") language: String): ModelFilmOnly
    @GET("tv/{idSerie}")
    suspend fun GetSerie(@Path("idSerie") idSerie: Int,
                         @Query("api_key") api_key: String,
                         @Query("language") language: String): ModelSerieOnly

    @GET("person/{idActeur}")
    suspend fun GetActeur(@Path("idActeur") idActeur: Int,
                          @Query("api_key") api_key: String,
                          @Query("language") language: String): ModelActeurOnly

    @GET("person/{idActeur}")
    suspend fun GetActeurDateDeNaissance(@Path("idActeur") idActeur: Int,
                          @Query("api_key") api_key: String,
                          @Query("language") language: String): String

    @GET("search/movie")
    suspend fun GetFilmsSearch(@Query("query") query: String,
                        @Query("api_key") api_key: String,
                        @Query("language") language: String): ModelFilms
    @GET("search/tv")
    suspend fun GetSeriesSearch(@Query("query") query: String,
                         @Query("api_key") api_key: String,
                         @Query("language") language: String): ModelSeries

    @GET("search/person")
    suspend fun GetActeursSearch(@Query("query") query: String,
                          @Query("api_key") api_key: String,
                          @Query("language") language: String): ModelPersonnalites
}