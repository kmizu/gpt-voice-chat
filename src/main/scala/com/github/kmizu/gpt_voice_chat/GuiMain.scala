package com.github.kmizu.gpt_voice_chat

import java.io.FileInputStream
import java.nio.file.{Files, Paths}
import javax.swing._
import scala.sys.process.stderr

object GuiMain {
  private val title = "ChatGPT Voice Chat"
  private var categoryListBox: JList[String] = _
  private var targetListBox: JList[String] = _
  private var descriptionTextArea: JTextArea = _
  private var promptTextArea: JTextArea = _
  private var chatButton: JButton = _
  private var outputTextArea: JTextArea = _

  private def configureFrame(frame: JFrame, title: String): Unit = {
    frame.setSize(800, 600)
    frame.setTitle(title)
    frame.getContentPane.setLayout(new BoxLayout(frame.getContentPane, BoxLayout.Y_AXIS))

    promptTextArea = new JTextArea()

    outputTextArea = new JTextArea()

    chatButton = new JButton("喋る")

    frame.getContentPane.add(promptTextArea)
    frame.getContentPane.add(chatButton)
    frame.getContentPane.add(outputTextArea)

    frame.setVisible(true)
  }
  def main(args: Array[String]): Unit = {
    val token = Files.readString(Paths.get("api_key.txt")).strip()
    val bot = new ChatBot(token = token, systemSetting = Some(
      Files.readString(Paths.get("profile.txt")).strip()
    ))
    val talker = if (args.length == 0) {
      new VOICEVOXTalker()
    } else if (args(0) == "voicepeak") {
      new VOICEPEAKTalker()
    } else if (args(0) == "voicevox") {
      new VOICEVOXTalker()
    } else {
      stderr.println(s"unknown engine: ${args(0)}")
      sys.exit(-1)
    }
    SwingUtilities.invokeLater{() =>
      val frame = new JFrame()
      configureFrame(frame, title)
      chatButton.addActionListener{event =>
        new Thread(() => {
          while(true) {
            SwingUtilities.invokeAndWait { () => {
              promptTextArea.setText("録音中……")
            }}
            SoundRecorder.record(5, "input.wav")
            val prompt = Whisper.transcribeFile("input.wav")
            SwingUtilities.invokeAndWait { () => {
              outputTextArea.append("私: " + prompt + "\n")
              promptTextArea.setText("返答待ち中……")
            }}
            val answer = bot.chat(prompt)
            SwingUtilities.invokeAndWait { () => {
              outputTextArea.append("AI： " + answer + "\n")
            }}
            talker.talk(answer)
          }
        }).start()
      }
    }
  }
}
