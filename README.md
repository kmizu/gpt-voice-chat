# gpt-voice-chat

リアルに音声で対話できる、ChatGPTベースのチャットシステムです。

- [CUI版](https://github.com/kmizu/gpt-voice-chat/blob/master/src/main/scala/com/github/kmizu/gpt_voice_chat/Main.scala)と
- [GUI版(Swingベース）](https://github.com/kmizu/gpt-voice-chat/blob/master/src/main/scala/com/github/kmizu/gpt_voice_chat/GuiMain.scala)

があります。α版未満の品質ですが、コードがわかれば一応実行できると思います。

## 準備

### VOICEVOXのインストール

gpt-voice-chatは[VOICEVOX](https://github.com/VOICEVOX/voicevox)を内部的に利用しています。正確には[VOICEVOX ENGINE](https://github.com/VOICEVOX/voicevox_engine/)のAPIを叩いています。

そのため、実行のためにはVOICEVOXをインストールする必要があります（VOICEPEAKも実は使えるのですが、その辺りはおいておき）。

お使いのプラットフォームで適当にVOICEVOXをインストールして、事前に起動しておいてください。内部的に`curl`コマンドを叩いているので、LinuxあるいはMacOSを使うのが無難かもしれません（Windows + WSL2でもいけると言えばいけますが）。

### Javaのインストール

OpenJDK 17以上が必要です。sdkmanでインスト―するか、[こちら](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)からダウンロードするのが手っ取り早いです。

### sbtのインストール

このソフトウェアはScalaで書かれています。Scala処理系本体は必要ありませんが、ビルドのためにsbtが必要です。[こちら](https://www.scala-sbt.org/1.x/docs/ja/Setup.html)を参考にしてインストールしてください。

## 動作確認

```sh
$ sbt run
```

を実行すると、コードのコンパイルおよび実行が自動的に行われるので、CUI版（Main)かGUI版(GuiMain)のどちらかを選択して実行します。スクリーンショットなどはまた準備ができていないので割愛。

## その他

とりあえずサクッと公開してしまいましたが、ドキュメントも色々未整備だし、コードも汚いところてんこ盛りです。コントリビュートしてくれる方は大歓迎です（forkして適当にカスタマイズするのも）。
