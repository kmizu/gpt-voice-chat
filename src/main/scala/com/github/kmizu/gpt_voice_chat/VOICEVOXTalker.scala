package com.github.kmizu.gpt_voice_chat

import org.apache.http.client.utils.URLEncodedUtils

import java.io.File
import java.net.URLEncoder
import scala.sys.process.{Process, stringSeqToProcess}

class VOICEVOXTalker(val speakerId: Int = 0) extends Talker {
  override protected def generateVoice(answer: String, output: String): Boolean = {
    val encodedAnswer = URLEncoder.encode(answer, "UTF-8")
    println(encodedAnswer)
    val succeed: Boolean = (Process(
      "curl.exe", Seq("-X", "POST", "-H", "accept: application/json", "-d", "", s"http://localhost:50021/audio_query?text=${encodedAnswer}&speaker=${speakerId}")
    ) #> new File("query.json")).! == 0
    if (!succeed) {
      return false
    }
    (Process(
      "curl.exe",
      Seq(
        "-s", "-H", "Content-Type: application/json", "-X", "POST", "-d", "@query.json",
        s"localhost:50021/synthesis?speaker=${speakerId}"
      )
    ) #> new File(output)).! == 0
  }
}
