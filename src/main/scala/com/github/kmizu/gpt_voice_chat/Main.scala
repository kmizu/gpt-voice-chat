package com.github.kmizu.gpt_voice_chat

import java.nio.file.{Files, Paths}
import java.io.{File, FileInputStream}

import scala.sys.process._
import javax.sound.sampled.AudioSystem
import javax.sound.sampled.DataLine.Info
import javax.sound.sampled.AudioInputStream
import javax.sound.sampled.SourceDataLine
import scala.io.StdIn.readLine

object Main {
  def main(args: Array[String]): Unit = {
    //execute process
    val token = Files.readString(Paths.get("api_key.txt")).strip()
    val bot = new ChatBot(token = token, system = Some(
      Files.readString(Paths.get("profile.txt")).strip()
    ))
    println("チャット開始です")
    val talker = if(args.length == 0) {
      new VOICEPEAKTalker()
    } else if(args(0) == "voicepeak") {
      new VOICEPEAKTalker()
    } else if(args(0) == "voicevox") {
      new VOICEVOXTalker()
    } else {
      stderr.println(s"unknown engine: ${args(0)}")
      sys.exit(-1)
    }
    while(true) {
      SoundRecorder.record(5, "input.wav") {
        print(".")
      }
      val prompt = Whisper.transcribeFile("input.wav")
      println(s"私> ${prompt}")
      val (answer, emotion) = bot.chat(prompt)
      println("あい> " + answer)
      talker.talk(answer, emotion)
    }
  }
}
