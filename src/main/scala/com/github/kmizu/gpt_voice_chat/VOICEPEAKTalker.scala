package com.github.kmizu.gpt_voice_chat

import scala.sys.process.Process

class VOICEPEAKTalker(val narrator: String = "Asumi Shuo") extends Talker {
  protected override def generateVoice(answer: String, output: String): Boolean = {
    Process("C:\\dev\\VOICEPEAK\\voicepeak.exe", Seq("-n", narrator, "-s", answer, "-e", s"happy=300", "--pitch", "100", "-o", output)).! == 0
  }
}
