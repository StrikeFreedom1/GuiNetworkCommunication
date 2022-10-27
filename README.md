# GuiNetworkCommunication
基于GUI的java网络通信设计，设计了客户端和服务端两个对象实现两者之间的双向通信。

1.设计一个基于GUI的客户-服务器的通信应用程序，如图1，图2所示

![image](https://user-images.githubusercontent.com/95091565/198265331-1061b9a3-eb44-4c18-81d2-82ed555384f0.png)
![image](https://user-images.githubusercontent.com/95091565/198265356-323ebb27-fd6b-495f-ad0b-0f2a9f2e8c5e.png)

2.图1为Socket通信服务器端界面，点击该界面中的【Start】按钮，启动服务器监听服务（在图1界面中间的多行文本区域显示“Server starting…”字样）。
  图2为Socket通信客户端界面，点击该界面中的【Connect】按钮与服务器建立链接，并在图2所示界面中间的多行文本区域显示“Connect to server…”字样，
  当服务器端监听到客户端的连接后，在图1界面中间的多行文本区域追加一行“Client connected…”字样，并与客户端建立Socket连接。
  
3.当图1所示的服务器端和图2所示的客户机端建立Socket连接后，编程实现服务端、客户端之间的“单向通信”：在客户端的输入界面发送消息，
  在服务端接收该消息，并将接收到对方的数据追加显示在多行文本框中。
