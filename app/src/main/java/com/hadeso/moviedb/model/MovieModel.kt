package com.hadeso.moviedb.model

import com.squareup.moshi.Json

data class MovieModel(
        val id: Int = 0,
        val video: Boolean = false,
        val budget: Int = 0,
        val overview: String = "",
        val title: String = "",
        val revenue: Int = 0,
        val genres: List<GenresModel>?,
        val runtime: Int = 0,
        val popularity: Double = 0.0,
        val tagline: String = "",
        val adult: Boolean = false,
        val homepage: String = "",
        val status: String = "",
        @Json(name = "original_language") val originalLanguage: String = "",
        @Json(name = "imdb_id") val imdbId: String = "",
        @Json(name = "backdrop_path") val backdropPath: String = "",
        @Json(name = "production_countries") val productionCountries: List<ProductionCountriesModel>?,
        @Json(name = "vote_count") val voteCount: Int = 0,
        @Json(name = "original_title") val originalTitle: String = "",
        @Json(name = "poster_path") val posterPath: String = "",
        @Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguagesModel>?,
        @Json(name = "production_companies") val productionCompanies: List<ProductionCompaniesModel>?,
        @Json(name = "release_date") val releaseDate: String = "",
        @Json(name = "vote_average") val voteAverage: Double = 0.0,
        @Json(name = "belongs_to_collection") val belongsToCollection: BelongsToCollectionModel
)