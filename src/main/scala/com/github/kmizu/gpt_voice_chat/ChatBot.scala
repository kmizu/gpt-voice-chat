package com.github.kmizu.gpt_voice_chat

import com.theokanning.openai.OpenAiService
import com.theokanning.openai.completion.chat.{ChatCompletionRequest, ChatMessage}

import scala.jdk.javaapi.CollectionConverters.asJava

class ChatBot(val token: String, val systemSetting: Option[String] = None) {
  private[this] val service = new OpenAiService(token)
  private val logs = collection.mutable.ArrayBuffer.empty[(String, String)]

  private def generateReply(messages: List[(String, String)]): String = {
    val request = ChatCompletionRequest.builder()
      .model("gpt-3.5-turbo")
      .topP(0.5)
      .messages(asJava(
        messages.map { case (user, message) => new ChatMessage(user, message) },
      )).build()
    service.createChatCompletion(request).getChoices().get(0).getMessage().getContent
  }

  systemSetting.foreach{system => logs.append("system" -> system)}
  def chat(prompt: String): String = {
    logs.append("user" -> prompt)
    val answer = generateReply(logs.toList)
    logs.append("assistant" -> answer)
    answer
  }
}
