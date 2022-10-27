package GuiNetworkCommunication;

import javax.swing.*;
import java.io.*;
import java.net.*;

/**
 * Author:ZengHao
 * CreateTime:2022-10-03-10:55
 * Description:Simple introduction of the code
 */
public class Client extends Thread{
    //对服务器的程序进行重构
    int port;
    String ip;
    JTextArea message;
    Socket server;
    BufferedReader br;
    BufferedWriter bw;
    Client(){};

    Client(String ip,int port,JTextArea message){
        this.ip = ip;
        this.port = port;
        this.message = message;
        //启动客户端
        this.start();
    }

    @Override
    public void run(){
        super.run();
        try {
            server = new Socket(InetAddress.getByName(ip),port);
            message.append("Server connected...\n");
            br = new BufferedReader(new InputStreamReader(server.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(server.getOutputStream()));
            //使用循环来不断接收客户端的消息
            String line;
            while((line = br.readLine())!=null){
                message.append(">> "+line+'\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
            //异常判断
            if(e instanceof ConnectException){
                message.append("- 无法连接到服务器，请重试或检查地址和端口。\n");
            }
            else{
                message.append("- 客户端与服务器断开连接\n");
            }
        }
        finally {
            //TODO
            try {
                server.close();
                br.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //客户端向服务器发送消息
    public void sendMessage(String msg){
        try {
            bw.write(msg+'\n');
            bw.flush();
            message.append("<< "+msg+"\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
