package com.example.youtube.data.remote.model

import java.io.Serializable

data class Videos(
 val etag: String,
 val items: List<Item>,
 val kind: String,
 val pageInfo: PageInfo
):Serializable {
 data class Item(
  val contentDetails: ContentDetails,
  val etag: String,
  val id: String,
  val kind: String,
  val snippet: Snippet
 ):Serializable {
  data class ContentDetails(
   val caption: String,
   val contentRating: ContentRating,
   val definition: String,
   val dimension: String,
   val duration: String,
   val licensedContent: Boolean,
   val projection: String
  ):Serializable {
   class ContentRating
  }

  data class Snippet(
   val categoryId: String,
   val channelId: String,
   val channelTitle: String,
   val defaultAudioLanguage: String,
   val description: String,
   val liveBroadcastContent: String,
   val localized: Localized,
   val publishedAt: String,
   val tags: List<String>,
   val thumbnails: Thumbnails,
   val title: String
  ):Serializable {
   data class Localized(
    val description: String,
    val title: String
   ):Serializable

   data class Thumbnails(
    val default: Default,
    val high: High,
    val maxres: Maxres,
    val medium: Medium,
    val standard: Standard
   ):Serializable {
    data class Default(
     val height: Int,
     val url: String,
     val width: Int
    ):Serializable

    data class High(
     val height: Int,
     val url: String,
     val width: Int
    ):Serializable

    data class Maxres(
     val height: Int,
     val url: String,
     val width: Int
    ):Serializable

    data class Medium(
     val height: Int,
     val url: String,
     val width: Int
    ):Serializable

    data class Standard(
     val height: Int,
     val url: String,
     val width: Int
    ):Serializable
   }
  }
 }

 data class PageInfo(
  val resultsPerPage: Int,
  val totalResults: Int
 ):Serializable
}