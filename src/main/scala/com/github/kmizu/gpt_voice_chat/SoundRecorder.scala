package com.github.kmizu.gpt_voice_chat

import javax.sound.sampled._

object SoundRecorder {
  def record(durationInSeconds: Int, outputFile: String): Unit = {
    val audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED, 44100, 16, 2, 4, 44100, false)
    val targetInfo = new DataLine.Info(classOf[TargetDataLine], audioFormat)

    if (!AudioSystem.isLineSupported(targetInfo)) {
      println("Target data line not supported")
      System.exit(0)
    }

    val targetLine = AudioSystem.getLine(targetInfo).asInstanceOf[TargetDataLine]
    targetLine.open(audioFormat)
    targetLine.start()

    val audioInputStream = new AudioInputStream(targetLine)
    val file = new java.io.File(outputFile)

    new Thread({()=>
      for (i <- 0 until durationInSeconds) {
        Thread.sleep(1000)
        print(".")
      }
      println()
      targetLine.stop()
      targetLine.close()
    }).start()

    AudioSystem.write(audioInputStream, AudioFileFormat.Type.WAVE, file)
  }
}