package com.springMVC.view;

import javax.swing.*;

public class ServerView extends JFrame {

  private JPanel contentPane;
  private JPanel headerPanel;
  private JPanel centerPanel;
  private JPanel leftPanel;
  private JPanel rightPanel;
  private JPanel clientPanel;
  private JPanel serverPanel;
  private JTextArea TxtClient;
  private JTextArea txtServer;

  public ServerView() {
    setContentPane(contentPane);
    setSize(800, 600);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public JTextArea getTxtClient() {
    return TxtClient;
  }

  public void setTxtClient(JTextArea txtClient) {
    TxtClient = txtClient;
  }

  public JTextArea getTxtServer() {
    return txtServer;
  }

  public void setTxtServer(JTextArea txtServer) {
    this.txtServer = txtServer;
  }
}
