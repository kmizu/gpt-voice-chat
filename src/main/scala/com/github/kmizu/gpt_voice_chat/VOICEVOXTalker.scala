package com.github.kmizu.gpt_voice_chat

import java.io.File
import java.net.URLEncoder
import scala.sys.process.{Process, stringSeqToProcess}

class VOICEVOXTalker(val speakerId: Int = 0) extends Talker {

  def generateVoiceOnWindows(answer: String, output: String): Boolean = {
    val succeed: Boolean = (Process(
      "curl.exe", Seq("-X", "POST", "-H", "accept: application/json", "-d", "", s"http://localhost:50021/audio_query?text=${answer}&speaker=${speakerId}")
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
    succeed
  }

  def generateVoiceOnLinux(answer: String, output: String): Boolean = {
    val succeed: Boolean = (Process(
      "curl", Seq("-X", "POST", "-H", "accept: application/json", "-d", "", s"http://localhost:50021/audio_query?text=${answer}&speaker=${speakerId}")
    ) #> new File("query.json")).! == 0
    if(!succeed) {
      return false
    }
    (Process(
      "curl",
      Seq(
        "-s", "-H", "Content-Type: application/json", "-X", "POST", "-d", "@query.json",
        s"localhost:50021/synthesis?speaker=${speakerId}"
      )
    ) #> new File(output)).! == 0
    succeed
  }

  def generateVoiceOnMac(answer: String, output: String): Boolean = {
    val succeed: Boolean = (Process(
      "curl", Seq("-X", "POST", "-H", "accept: application/json", "-d", "", s"http://localhost:50021/audio_query?text=${answer}&speaker=${speakerId}")
    ) #> new File("query.json")).! == 0
    if(!succeed) {
      return false
    }
    (Process(
      "curl",
      Seq(
        "-s", "-H", "Content-Type: application/json", "-X", "POST", "-d", "@query.json",
        s"localhost:50021/synthesis?speaker=${speakerId}"
      )
    ) #> new File(output)).! == 0
    succeed
  }

  override protected def generateVoice(answer: String, output: String): Boolean = {
    val encodedAnswer = URLEncoder.encode(answer, "UTF-8")
    val os = System.getProperty("os.name").toLowerCase()
    val osVersion = System.getProperty("os.version").toLowerCase()
    val succeed: Boolean = if(os.contains("windows") || osVersion.contains("wsl")) {
      generateVoiceOnWindows(encodedAnswer, output)
    } else if (os.contains("linux")) {
      generateVoiceOnLinux(encodedAnswer, output)
    } else if (os.contains("mac")) {
      generateVoiceOnMac(encodedAnswer, output)
    } else {
      throw new RuntimeException(s"Unsupported OS: ${os}")
    }
    succeed
  }
}
