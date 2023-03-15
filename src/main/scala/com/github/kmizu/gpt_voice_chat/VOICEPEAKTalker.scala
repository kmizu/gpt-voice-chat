package com.github.kmizu.gpt_voice_chat

import scala.sys.process.Process

class VOICEPEAKTalker(val narrator: String = "Asumi Shuo") extends Talker {
  def generateVoiceOnWindows(answer: String, output: String): Boolean = {
    Process("voicepeak.exe", Seq("-n", narrator, "-s", answer, "-e", s"happy=300", "--pitch", "100", "-o", output)).! == 0
  }

  def generateVoiceOnLinux(answer: String, output: String): Boolean = {
    Process("voicepeak", Seq("-n", narrator, "-s", answer, "-e", s"happy=300", "--pitch", "100", "-o", output)).! == 0
  }

  def generateVoiceOnMac(answer: String, output: String): Boolean = {
    Process("voicepeak", Seq("-n", narrator, "-s", answer, "-e", s"happy=300", "--pitch", "100", "-o", output)).! == 0
  }

  override protected def generateVoice(answer: String, output: String): Boolean = {
    val os = System.getProperty("os.name").toLowerCase()
    val osVersion = System.getProperty("os.version").toLowerCase()
    val succeed: Boolean = if (os.contains("windows") || (os.contains("linux") && osVersion.contains("wsl"))) {
      generateVoiceOnWindows(answer, output)
    } else if (os.contains("linux")) {
      generateVoiceOnLinux(answer, output)
    } else if (os.contains("mac")) {
      generateVoiceOnMac(answer, output)
    } else {
      throw new RuntimeException(s"Unsupported OS: ${os}")
    }
    succeed
  }
}
object VOICEPEAKTalker {
  def listNarrators(): List[String] = {
    val os = System.getProperty("os.name").toLowerCase()
    val osVersion = System.getProperty("os.version").toLowerCase()
    if (os.contains("windows") || (os.contains("linux") && osVersion.contains("wsl"))) {
      Process("voicepeak.exe", Seq("--list-narrator")).lazyLines.toList
    } else if (os.contains("linux")) {
      Process("voicepeak", Seq("--list-narrator")).lazyLines.toList
    } else if (os.contains("mac")) {
      Process("voicepeak", Seq("--list-narrator")).lazyLines.toList
    } else {
      throw new RuntimeException(s"Unsupported OS: ${os}")
    }
  }
}
