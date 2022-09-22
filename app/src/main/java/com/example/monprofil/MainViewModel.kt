package com.example.monprofil

import kotlinx.coroutines.flow.MutableStateFlow

val movies = MutableStateFlow<List<TmdbMovie>>(listOf())

fun getMovies() {
    viewModelScope.launch {
        movies.value = service.DerniersFilms(api_key)
    }
}