package main;

import javax.swing.*;

public class View extends JFrame {
    private JPanel contentPanel;
    private JTextField ipTf;
    private JTextArea clientTa;
    private JTextArea server1Ta;
    private JTextArea server2Ta;
    private JLabel titleLbl;
    private JPanel centerPanel;
    private JPanel clientPanel;
    private JPanel server1Panel;
    private JPanel server2Panel;
    private JLabel ipLbl;
    private JLabel titleClient;
    private JLabel titleServer1;
    private JLabel titleServer2;

    public View() {
        super("Coordinator");
        setContentPane(contentPanel);
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public JTextField getIpTf() {
        return ipTf;
    }

    public void setIpTf(JTextField ipTf) {
        this.ipTf = ipTf;
    }

    public JTextArea getClientTa() {
        return clientTa;
    }

    public void setClientTa(JTextArea clientTa) {
        this.clientTa = clientTa;
    }

    public JTextArea getServer1Ta() {
        return server1Ta;
    }

    public void setServer1Ta(JTextArea server1Ta) {
        this.server1Ta = server1Ta;
    }

    public JTextArea getServer2Ta() {
        return server2Ta;
    }

    public void setServer2Ta(JTextArea server2Ta) {
        this.server2Ta = server2Ta;
    }
}
