package com.github.kmizu.gpt_voice_chat;

import scala.Option;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class ChatForm extends JFrame {
    private JPanel panel;
    private JComboBox categoryBox;
    private JComboBox targetBox;
    private JTextArea descriptionArea;
    private JLabel promptLabel;
    private JTextArea promptArea;
    private JTextArea answerArea;
    private JLabel answerLabel;
    private JButton generateTextButton;

    public ChatForm() {
        setContentPane(panel);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
