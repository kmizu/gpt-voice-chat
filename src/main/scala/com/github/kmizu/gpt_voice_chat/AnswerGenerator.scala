package com.github.kmizu.gpt_voice_chat

import com.theokanning.openai.OpenAiService
import com.theokanning.openai.completion.CompletionRequest
import com.theokanning.openai.completion.chat.{ChatCompletionRequest, ChatMessage}

import scala.collection.convert.ImplicitConversions.`collection AsScalaIterable`
import scala.jdk.javaapi.CollectionConverters._

class AnswerGenerator (
  token: String, model: String = "gpt-3.5-turbo"
) {
  private[this] val service = new OpenAiService(token)
  def generate(messages: List[(String, String)]): String = {
    val request = ChatCompletionRequest.builder()
      .model(model)
      .topP(0.5)
      .messages(asJava(
        messages.map{case (user, message) => new ChatMessage(user, message)},
      )).build()

    service.createChatCompletion(request).getChoices().get(0).getMessage().getContent
  }
}
