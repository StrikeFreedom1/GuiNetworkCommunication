package GuiNetworkCommunication;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EtchedBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;
import javax.xml.ws.soap.Addressing;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Author:ZengHao
 * CreateTime:2022-10-03-1:10
 * Description:Simple introduction of the code
 */
public class ServerUI {
    JFrame jf;

    //声明各个组件
    //1.声明上半部分组件
    JPanel serverSettingPanel;
    JLabel portLabel;
    JTextField portField;
    JButton startButton;
    //2.中部gui的组件
    JPanel textPanel;
    JTextArea textArea;
    //3.下半部分的组件
    JPanel sendPanel;
    JLabel sayLabel;
    JTextField sayField;
    JButton sayButton;

    Server server;

    //构建方法实现对组件的初始化设置
    //初始化上部分组件
    private void initializeServerPanel() {
        serverSettingPanel = new JPanel();
        portLabel = new JLabel("Port:");
        portField = new JTextField(30);
        startButton = new JButton("Start");
        startButton.setFocusPainted(false);
        //设置JLabel和JButton的文字样式
        Font fnt = new Font("Microsoft YaHei", Font.BOLD, 14);
        portLabel.setFont(fnt);
        startButton.setFont(fnt);
        //给Panel设置边框样式(设置刻蚀样式，高光，阴影的颜色），显示文字“服务器设置”几个字
        serverSettingPanel.setBorder(new TitledBorder(new EtchedBorder(EtchedBorder.LOWERED, new Color(176, 224, 230), new Color(211, 211, 211)), "服务器设置："));
        //将组件加载到Panel组件上
        serverSettingPanel.add(portLabel);
        serverSettingPanel.add(portField);
        serverSettingPanel.add(startButton);
    }

    //初始化中间的组件
    private void initializeTextPanel() {
        textPanel = new JPanel();
        textArea = new JTextArea(20, 40);
        //设置字体和光标、背景的颜色
        textArea.setFont(new Font("Microsoft YaHei",Font.BOLD,13));
        textArea.setCaretColor(Color.cyan);
        textArea.setBackground(new Color(245,255,255));
        //设置边框样式
        textPanel.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(128, 128, 128), new Color(211, 211, 211)));
        //设置文本框的边框
        textArea.setBorder(new EtchedBorder(EtchedBorder.RAISED, new Color(128, 128, 128), new Color(211, 211, 211)));
        //将文本框放在Panel上,并添加滚动条
        textPanel.add(new JScrollPane(textArea));
    }

    //初始化下部分的组件
    private void initializeSendPanel() {
        sendPanel = new JPanel();
        sayLabel = new JLabel("Say");
        sayField = new JTextField(30);
        sayButton = new JButton("Say");
        //设置点击文字消除文本框
        sayButton.setFocusPainted(false);
        //设置JLabel和JButton的文字样式
        Font fnt = new Font("Microsoft YaHei", Font.BOLD, 14);
        sayLabel.setFont(fnt);
        sayButton.setFont(fnt);
        //将组件加载到JPanel上
        sendPanel.add(sayLabel);
        sendPanel.add(sayField);
        sendPanel.add(sayButton);
    }

    //设置全局抗锯齿
    static void enableAntiAliasing(){
        System.setProperty("awt.useSystemAAFontSettings","on");
        System.setProperty("swing.aatext","true");
    }

    //绘制客户端的GUI界面
    public void init() {
        //设置界面的大小,使用了pack自动设置就会被覆盖//jf.setSize(500,300);
        //调用函数初始化组件
        initializeServerPanel();
        initializeTextPanel();
        initializeSendPanel();
        jf = new JFrame("服务器");
        //将每个panel组件放置到JFrame上
        jf.add(serverSettingPanel, BorderLayout.NORTH);
        jf.add(textPanel, BorderLayout.CENTER);
        jf.add(sendPanel, BorderLayout.SOUTH);
        //设置start按钮事件监听器
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    //判断端口是否为空，对于空文本框给出提示
                    String portFieldInput = portField.getText();
                    if(portFieldInput.equals("")){
                        JOptionPane.showMessageDialog(jf,"端口号不能为空","警告",JOptionPane.WARNING_MESSAGE);
                    }else{
                        //获取端口
                        int port = Integer.parseInt(portFieldInput);
                        textArea.append("Server starting...\n");
                        server = new Server(port,textArea);
                    }
                }catch(java.lang.NumberFormatException exception){
                    textArea.append("- 端口格式错误，请重新输入。\n");
                }
            }
        });
        //设置Say按钮的事件监听器
        sayButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //调用Server的sendMessage方法
                server.sendMessage(sayField.getText());
                //清空textfield
                sayField.setText("");
            }
        });


        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.pack();
        jf.setVisible(true);
    }

    public static void main(String[] args) {
        new ServerUI().init();
    }
}
