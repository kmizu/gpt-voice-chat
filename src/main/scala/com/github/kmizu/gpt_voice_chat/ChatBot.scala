package com.github.kmizu.gpt_voice_chat

import com.theokanning.openai.OpenAiService
import com.theokanning.openai.completion.chat.{ChatCompletionRequest, ChatMessage}
import play.api.libs.json.{JsValue, Json}

import scala.jdk.javaapi.CollectionConverters.asJava

class ChatBot(val token: String, val system: Option[String] = None) {
  private[this] val service = new OpenAiService(token)
  private val logs = collection.mutable.ArrayBuffer.empty[(String, String)]

  private def generateReply(messages: List[(String, String)]): String = {
    val request = ChatCompletionRequest.builder()
      .model("gpt-4")
      .topP(0.1)
      .messages(asJava(
        messages.map { case (user, message) => new ChatMessage(user, message) },
      )).build()
    service.createChatCompletion(request).getChoices().get(0).getMessage().getContent
  }

  system.foreach{sys =>
    logs.append("system" -> sys)
    logs.append("assistant" -> "わかったよ")
  }
  def chat(prompt: String): (String, Option[JsValue]) = {
    logs.append("user" -> prompt)
    val answer = generateReply(logs.toList)
    logs.append("assistant" -> answer)
    println(answer)
    val Array(body: String, emotion: Option[String]) = {
      val splitted = answer.split("(\r|\n|\r\n)+")
      if(splitted.length == 1) {
        Array(splitted(0), None)
      } else {
        Array(splitted(0), Some(splitted(1)))
      }
    }
    body -> emotion.map{e => Json.parse(e)}
  }
}
