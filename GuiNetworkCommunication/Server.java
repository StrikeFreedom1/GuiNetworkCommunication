package GuiNetworkCommunication;

import javax.swing.*;
import java.io.*;
import java.net.ConnectException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Author:ZengHao
 * CreateTime:2022-10-03-10:45
 * Description:Simple introduction of the code
 */
public class Server extends Thread{
    //并不是直接启动服务器，是通过事件创建服务器对象启动
    int port;
    JTextArea message;
    ServerSocket serverSocket;
    Socket clientSocket;
    BufferedReader br;
    BufferedWriter bw;
    Server(){};

    Server(int port,JTextArea message){
        this.port = port;
        this.message = message;
        //启动服务器
        this.start();
    }

    @Override
    public void run(){
        super.run();
        try {
            serverSocket = new ServerSocket(port);
            //获取监听事件
            clientSocket = serverSocket.accept();
            message.append("Client connected...\n");
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            bw = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            //使用循环来不断接收客户端的消息
            String line;
            while((line = br.readLine())!=null) {
                message.append(">> " + line + '\n');
            }
        } catch (IOException e) {
            e.printStackTrace();
            //异常判断
            if(e instanceof ConnectException){
                message.append("- 服务器启动失败，请重试或更换端口。\n");
            }
            else{
                message.append("- 服务器与客户端断开连接\n");
            }
        }finally {
            //最终要关闭服务器
            try {
                serverSocket.close();
                //TODO
                clientSocket.close();
                br.close();
                bw.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    //服务器向客户端发送消息
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
