package com.github.kmizu.gpt_voice_chat

import play.api.libs.json.{JsNumber, JsObject, JsString, Json}

import java.io.File
import javax.sound.sampled.DataLine.Info
import javax.sound.sampled.{AudioSystem, SourceDataLine}
import scala.sys.process.Process

trait Talker {
  val TemporaryAudioFille = "voice.wav"
  def talk(voice: String): Boolean = {
    val generated = generateVoice(voice, TemporaryAudioFille)
    if(!generated) return false
    playWaveFile(TemporaryAudioFille)
    true
  }

  protected def playWaveFile(filePath: String): Unit = {
    val audioIn = AudioSystem.getAudioInputStream(new File(filePath))
    val format = audioIn.getFormat()
    val info = new Info(classOf[SourceDataLine], format)
    val line = AudioSystem.getLine(info).asInstanceOf[SourceDataLine]

    line.open(format)
    line.start()

    val buffer = new Array[Byte](4096)
    var bytesRead = 0

    while (bytesRead != -1) {
      bytesRead = audioIn.read(buffer, 0, buffer.length)
      if (bytesRead >= 0) {
        line.write(buffer, 0, bytesRead)
      }
    }

    line.drain()
    line.stop()
    line.close()
    audioIn.close()
  }

  protected def generateVoice(answer: String, output: String): Boolean
}
