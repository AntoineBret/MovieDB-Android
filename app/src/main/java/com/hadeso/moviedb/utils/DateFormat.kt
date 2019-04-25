package com.hadeso.moviedb.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateFormat {

  val SERVER_FORMAT = "yyyy-MM-dd"
  val LOCAL_FORMAT = "yyyy"

  fun getDateFormatted(dateString: String): String {
    var dateLocalString = ""

    val dfServer = SimpleDateFormat(SERVER_FORMAT, Locale.getDefault())

    try {
      val date = dfServer.parse(dateString)
      val dfLocal = SimpleDateFormat(LOCAL_FORMAT, Locale.getDefault())
      dateLocalString = dfLocal.format(date)
    } catch (e: ParseException) {
      e.printStackTrace()
    }

    return dateLocalString
  }
}
