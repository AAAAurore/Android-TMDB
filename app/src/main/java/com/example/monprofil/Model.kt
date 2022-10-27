package com.example.monprofil

//Films
data class ModelFilms(
    val page: Int = 0,
    val results: List<ModelFilm> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)

data class ModelFilm(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val genre_ids: List<Int> = listOf(),
    val id: Int = 0,
    val media_type: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)

//Séries
data class ModelSeries(
    val page: Int = 0,
    val results: List<ModelSerie> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)

data class ModelSerie(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val first_air_date: String = "",
    val genre_ids: List<Int> = listOf(),
    val id: Int = 0,
    val media_type: String = "",
    val name: String = "",
    val origin_country: List<String> = listOf(),
    val original_language: String = "",
    val original_name: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)

//Personnalités
data class ModelPersonnalites(
    val page: Int = 0,
    val results: List<ModelPersonnalite> = listOf(),
    val total_pages: Int = 0,
    val total_results: Int = 0
)

data class ModelPersonnalite(
    val adult: Boolean = false,
    val gender: Int = 0,
    val id: Int = 0,
    val known_for: List<KnownForP> = listOf(),
    val known_for_department: String = "",
    val media_type: String = "",
    val name: String = "",
    val original_name: String = "",
    val popularity: Double = 0.0,
    val profile_path: String = ""
)

data class KnownForP(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val genre_ids: List<Int> = listOf(),
    val id: Int = 0,
    val media_type: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val release_date: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)

//DistributionFilm
data class ModelDistributionFilm(
    val cast: List<ModelCastDistributionFilm> = listOf(),
    val crew: List<ModelCrewDistributionFilm> = listOf(),
    val id: Int = 0
)

data class ModelCastDistributionFilm(
    val adult: Boolean = false,
    val cast_id: Int = 0,
    val character: String = "",
    val credit_id: String = "",
    val gender: Int = 0,
    val id: Int = 0,
    val known_for_department: String = "",
    val name: String = "",
    val order: Int = 0,
    val original_name: String = "",
    val popularity: Double = 0.0,
    val profile_path: String = ""
)

data class ModelCrewDistributionFilm(
    val adult: Boolean = false,
    val credit_id: String = "",
    val department: String = "",
    val gender: Int = 0,
    val id: Int = 0,
    val job: String = "",
    val known_for_department: String = "",
    val name: String = "",
    val original_name: String = "",
    val popularity: Double = 0.0,
    val profile_path: String = ""
)

//DistributionSerie
data class ModelDistributionSerie(
    val cast: List<ModelCastDistributionSerie> = listOf(),
    val crew: List<ModelCrewDistributionSerie> = listOf(),
    val id: Int = 0
)

data class ModelCastDistributionSerie(
    val adult: Boolean = false,
    val character: String = "",
    val credit_id: String = "",
    val gender: Int = 0,
    val id: Int = 0,
    val known_for_department: String = "",
    val name: String = "",
    val order: Int = 0,
    val original_name: String = "",
    val popularity: Double = 0.0,
    val profile_path: String = ""
)

data class ModelCrewDistributionSerie(
    val adult: Boolean = false,
    val credit_id: String = "",
    val department: String = "",
    val gender: Int = 0,
    val id: Int = 0,
    val job: String = "",
    val known_for_department: String = "",
    val name: String = "",
    val original_name: String = "",
    val popularity: Double = 0.0,
    val profile_path: String = ""
)

//Film
data class ModelFilmOnly(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val belongs_to_collection: Any = Any(),
    val budget: Int = 0,
    val genres: List<GenreFilmOnly> = listOf(),
    val homepage: String = "",
    val id: Int = 0,
    val imdb_id: String = "",
    val original_language: String = "",
    val original_title: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val production_companies: List<ProductionCompanyFilmOnly> = listOf(),
    val production_countries: List<ProductionCountryFilmOnly> = listOf(),
    val release_date: String = "",
    val revenue: Int = 0,
    val runtime: Int = 0,
    val spoken_languages: List<SpokenLanguageFilmOnly> = listOf(),
    val status: String = "",
    val tagline: String = "",
    val title: String = "",
    val video: Boolean = false,
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)

data class GenreFilmOnly(
    val id: Int = 0,
    val name: String = ""
)

data class ProductionCompanyFilmOnly(
    val id: Int = 0,
    val logo_path: String = "",
    val name: String = "",
    val origin_country: String = ""
)

data class ProductionCountryFilmOnly(
    val iso_3166_1: String = "",
    val name: String = ""
)

data class SpokenLanguageFilmOnly(
    val english_name: String = "",
    val iso_639_1: String = "",
    val name: String = ""
)

//Série
data class ModelSerieOnly(
    val adult: Boolean = false,
    val backdrop_path: String = "",
    val created_by: List<CreatedBySerieOnly> = listOf(),
    val episode_run_time: List<Int> = listOf(),
    val first_air_date: String = "",
    val genres: List<GenreSerieOnly> = listOf(),
    val homepage: String = "",
    val id: Int = 0,
    val in_production: Boolean = false,
    val languages: List<String> = listOf(),
    val last_air_date: String = "",
    val last_episode_to_air: LastEpisodeToAirSerieOnly = LastEpisodeToAirSerieOnly(),
    val name: String = "",
    val networks: List<NetworksSerieOnly> = listOf(),
    val next_episode_to_air: Any = Any(),
    val number_of_episodes: Int = 0,
    val number_of_seasons: Int = 0,
    val origin_country: List<String> = listOf(),
    val original_language: String = "",
    val original_name: String = "",
    val overview: String = "",
    val popularity: Double = 0.0,
    val poster_path: String = "",
    val production_companies: List<ProductionCompaniesSerieOnly> = listOf(),
    val production_countries: List<ProductionCountriesSerieOnly> = listOf(),
    val seasons: List<SeasonSerieOnly> = listOf(),
    val spoken_languages: List<SpokenLanguageSerieOnly> = listOf(),
    val status: String = "",
    val tagline: String = "",
    val type: String = "",
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)

data class GenreSerieOnly(
    val id: Int = 0,
    val name: String = ""
)

data class CreatedBySerieOnly(
    val credit_id: String = "",
    val gender: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val profile_path: String = ""
)

data class LastEpisodeToAirSerieOnly(
    val air_date: String = "",
    val episode_number: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val overview: String = "",
    val production_code: String = "",
    val runtime: Int = 0,
    val season_number: Int = 0,
    val show_id: Int = 0,
    val still_path: String = "",
    val vote_average: Double = 0.0,
    val vote_count: Int = 0
)

data class NetworksSerieOnly(
    val name: String = "",
    val id: Int,
    val logo_path: String = "",
    val origin_country: String = ""
)

data class ProductionCompaniesSerieOnly(
    val id: Int = 0,
    val logo_path: String = "",
    val name: String = "",
    val origin_country: String = ""
)

data class ProductionCountriesSerieOnly(
    val iso_3166_1: String = "",
    val name: String = ""
)

data class SeasonSerieOnly(
    val air_date: String = "",
    val episode_count: Int = 0,
    val id: Int = 0,
    val name: String = "",
    val overview: String = "",
    val poster_path: String = "",
    val season_number: Int = 0
)

data class SpokenLanguageSerieOnly(
    val english_name: String = "",
    val iso_639_1: String = "",
    val name: String = ""
)

//Acteur
data class ModelActeurOnly(
    val adult: Boolean = false,
    val also_known_as: List<String> = listOf(),
    val biography: String = "",
    val birthday: String = "",
    val deathday: String = "",
    val gender: Int = 0,
    val homepage: String = "",
    val id: Int = 0,
    val imdb_id: String = "",
    val known_for_department: String = "",
    val name: String = "",
    val place_of_birth: String = "",
    val popularity: Double = 0.0,
    val profile_path: String = ""
)