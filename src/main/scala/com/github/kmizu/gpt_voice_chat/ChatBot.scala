package com.github.kmizu.gpt_voice_chat

class ChatBot(val token: String, val systemSetting: Option[String] = None) {
  private val generator = new AnswerGenerator(token)
  private val logs = collection.mutable.ArrayBuffer.empty[(String, String)]
  systemSetting.foreach{system => logs.append("system" -> system)}
  def chat(prompt: String): String = {
    logs.append("user" -> prompt)
    val answer = generator.generate(logs.toList)
    logs.append("assistant" -> answer)
    answer
  }
}
