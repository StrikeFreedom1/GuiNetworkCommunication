package GuiNetworkCommunication;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author:ZengHao
 * CreateTime:2022-10-03-10:17
 * Description:Simple introduction of the code
 */
public class ClientUI {
    JFrame jf;

    //声明各个组件
    //1.声明上半部分组件
    JPanel clientSettingPanel;
    JLabel serverIpLabel;
    JTextField serverIpField;
    JLabel serverPortLabel;
    JTextField serverPortField;
    JButton connectButton;
    //2.中部gui的组件
    JPanel textPanel;
    JTextArea textArea;
    //3.下半部分的组件
    JPanel sendPanel;
    JLabel sayLabel;
    JTextField sayField;
    JButton sayButton;

    Client client;

    //构建方法实现对组件的初始化设置
    //初始化上部分组件
    private void initializeClientPanel(){
        clientSettingPanel = new JPanel();
        serverIpLabel = new JLabel("Server IP:");
        serverIpField = new JTextField(10);
        serverPortLabel = new JLabel("Server Port:");
        serverPortField = new JTextField(10);
        connectButton = new JButton("connect");
        //消除文本框
        connectButton.setFocusPainted(false);
        //设置JLabel和JButton的文字样式
        Font fnt = new Font("Microsoft YaHei",Font.BOLD,14);
        serverIpLabel.setFont(fnt);
        serverPortLabel.setFont(fnt);
        connectButton.setFont(fnt);
        //给Panel设置边框样式(设置刻蚀样式，高光，阴影的颜色），显示文字“服务器设置”几个字
        clientSettingPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED,new Color(176,224,230),new Color(211,211,211)),"客户机设置:"));
        //将组件加载到Panel组件上
        clientSettingPanel.add(serverIpLabel);
        clientSettingPanel.add(serverIpField);
        clientSettingPanel.add(serverPortLabel);
        clientSettingPanel.add(serverPortField);
        clientSettingPanel.add(connectButton);
    }
    //初始化中间的组件
    private void initializeTextPanel(){
        textPanel = new JPanel();
        textArea = new JTextArea(20,40);
        //设置字体和光标的颜色
        textArea.setFont(new Font("Microsoft YaHei",Font.BOLD,13));
        textArea.setCaretColor(Color.cyan);
        textArea.setBackground(new Color(245,255,255));
        //设置边框样式
        textPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED,new Color(128,128,128),new Color(211,211,211)));
        //设置文本框的边框
        textArea.setBorder(new EtchedBorder(EtchedBorder.RAISED,new Color(128,128,128),new Color(211,211,211)));
        //将文本框放在Panel上,并添加滚动条
        textPanel.add(new JScrollPane(textArea));
    }
    //初始化下部分的组件
    private void initializeSendPanel(){
        sendPanel = new JPanel();
        sayLabel = new JLabel("Say");
        sayField = new JTextField(30);
        sayButton = new JButton("Say");
        //消除文本框
        sayButton.setFocusPainted(false);
        //设置JLabel和JButton的文字样式
        Font fnt = new Font("Microsoft YaHei",Font.BOLD,14);
        sayLabel.setFont(fnt);
        sayButton.setFont(fnt);
        //将组件加载到Panel上
        sendPanel.add(sayLabel);
        sendPanel.add(sayField);
        sendPanel.add(sayButton);
    }

    //设置全局抗锯齿
    public void enableAntiAliasing(){
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext","true");
    }

    //绘制客户端的GUI界面
    public void init(){
        //设置界面的大小,使用了pack自动设置就会被覆盖
//        jf.setSize(500,300);
        //调用函数初始化组件
        initializeClientPanel();
        initializeTextPanel();
        initializeSendPanel();
        jf = new JFrame("客户端");
        //将每个panel组件放置到JFrame上
        jf.add(clientSettingPanel, BorderLayout.NORTH);
        jf.add(textPanel,BorderLayout.CENTER);
        jf.add(sendPanel,BorderLayout.SOUTH);

        //给connectButton添加事件监听器
        connectButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String ip = serverIpField.getText();
                    String serverPortFieldInput = serverPortField.getText();
                    //判断输入框是否为空，为空时给出警告
                    if(serverPortFieldInput.equals("")){
                        JOptionPane.showMessageDialog(jf,"端口号不能为空","警告",JOptionPane.WARNING_MESSAGE);
                    }else if(ip.equals("")){
                        JOptionPane.showMessageDialog(jf,"IP地址不能为空","警告",JOptionPane.WARNING_MESSAGE);
                    }else {
                        int port = Integer.parseInt(serverPortFieldInput);
                        textArea.append("Connect to server...\n");
                        client = new Client(ip,port,textArea);
                    }
                }catch (java.lang.NumberFormatException exception){
                    textArea.append("- IP错误或端口格式错误，请重新输入。\n");
                }
            }
        });

        sayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //使用Client的方法发送消息给服务器
                client.sendMessage(sayField.getText());
                //清空
                sayField.setText("");
            }
        });


        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        new ClientUI().init();
    }
}
