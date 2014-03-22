package agro;

import java.net.*;
import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Module_proto {

@SuppressWarnings({ "resource", "unchecked" })
public static void main(String[] args) throws IOException {
InetAddress addr = InetAddress.getByName("192.34.12.12");
//подключаемся на 8181 порт
Socket socket = new Socket(addr, 8181);
   try {

   System.out.println("socket = " + socket);
   BufferedReader in = new BufferedReader(
       new InputStreamReader(
         socket.getInputStream()));

   PrintWriter out = new PrintWriter(
       new BufferedWriter(
         new OutputStreamWriter(
           socket.getOutputStream())), true);

   BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

   String line = null;
   JSONObject resultJson = new JSONObject();
   JSONParser parser = new JSONParser();
   //отсылаем сообщение с попыткой авторизоватся
   //формируем обьект JSON
   resultJson.put("mess_type","MS_AUTH"); 
   resultJson.put("name","test");
   resultJson.put("password","test");

   //приводим к строке. Именно в виде строки даные передуются по сокетам
   String json_str =  resultJson.toJSONString();  
   out.println(json_str); //пишем в буффер
   out.flush();

   //теперь попытаемся получить ответ от сервера
   //например данные о попытке авторизоватся 
   line = in.readLine();  //читаем строку с буффера
  try {
    Object obj = parser.parse(line);  //парсим строку в объект ДжСОН
    resultJson = (JSONObject) obj;   //

    String mess_type = (String) resultJson.get("mess_type"); //получем тип сообщения
    if (mess_type.equals("MS_AUTH")){
        String message = (String) resultJson.get("message"); //получаем поля
        String status = (String) resultJson.get("status");
        System.out.println("Request status "+status+". Message "+message);

    }
} catch (ParseException e) {
    // TODO Auto-generated catch block
    System.out.println("Cant parse json string");
}

  //получить информацию о модуле (ip adress)
  //тут все аналогично, только тип сообщения MS_INFO
  resultJson.put("mess_type","MS_INFO"); 
  resultJson.put("name","test");

  json_str =  resultJson.toJSONString();  
  out.println(json_str);
  out.flush();
  line = in.readLine();

 try {
    Object obj = parser.parse(line);
    resultJson = (JSONObject) obj;
    String mess_type = (String) resultJson.get("mess_type"); 
    if (mess_type.equals("MS_INFO")){
        String ip = (String) resultJson.get("ip");
        System.out.println("Ip adress of module "+ip);

    }
} catch (ParseException e) {
    // TODO Auto-generated catch block
    System.out.println("Cant parse json string");
}

   for(;;) {
       line = keyboard.readLine();
       out.println(line);
       line = in.readLine();
       System.out.println(line);
   }

//   out.println("END");
 } finally {
   System.out.println("closing...");
   //socket.close();
 }
}
}
