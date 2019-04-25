package com.hadeso.moviedb.ui.search

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class GenreViewItem (
  val id : Int,
  val name: String
): Parcelable
