package com.github.kmizu.gpt_voice_chat

import com.theokanning.openai.OpenAiService
import scalaj.http.{Http, HttpResponse, MultiPart}
import play.api.libs.json._

import java.nio.file.{Files, Paths}

object Whisper {
  val apiKey = Files.readString(Paths.get("api_key.txt")).strip()

  def transcribeFile(filePath: String): String = {
    val service = new OpenAiService(apiKey)

    val url = s"https://api.openai.com/v1/audio/transcriptions"

    val bytes: Array[Byte] = Files.readAllBytes(Paths.get(filePath))

    // Send the API request and parse the response
    val response: HttpResponse[String] = Http(url).timeout(200000, 200000)
      .header("Content-Type", "multipart/form-data")
      .header("Authorization", s"Bearer $apiKey")
      .param("model", "whisper-1")
      .postMulti(
        MultiPart("file", filePath, "audio/wav", bytes)
      )
      .asString

    val jsonResponse = Json.parse(response.body)
    val result = (jsonResponse \ "text").as[JsString].value

    result
  }
}
