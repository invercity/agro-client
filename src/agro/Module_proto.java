package agro;

import java.net.*;
import java.io.*;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class Module_proto {

@SuppressWarnings({ "resource", "unchecked" })
public static void main(String[] args) throws IOException {
InetAddress addr = InetAddress.getByName("192.168.101.50");

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

   resultJson.put("mess_type","MS_AUTH"); 
   resultJson.put("name","disp.agro");
   resultJson.put("password","disp.agro");


   String json_str =  resultJson.toJSONString();  
   out.println(json_str); 
   out.flush();

   line = in.readLine();  
  try {
    Object obj = parser.parse(line);  //РїР°СЂСЃРёРј СЃС‚СЂРѕРєСѓ РІ РѕР±СЉРµРєС‚ Р”Р¶РЎРћРќ
    resultJson = (JSONObject) obj;   //

    String mess_type = (String) resultJson.get("mess_type"); //РїРѕР»СѓС‡РµРј С‚РёРї СЃРѕРѕР±С‰РµРЅРёСЏ
    if (mess_type.equals("MS_AUTH")){
        String message = (String) resultJson.get("message"); //РїРѕР»СѓС‡Р°РµРј РїРѕР»СЏ
        String status = (String) resultJson.get("status");
        System.out.println("Request status "+status+". Message "+message);

    }
} catch (ParseException e) {
    // TODO Auto-generated catch block
    System.out.println("Cant parse json string");
}

  //РїРѕР»СѓС‡РёС‚СЊ РёРЅС„РѕСЂРјР°С†РёСЋ Рѕ РјРѕРґСѓР»Рµ (ip adress)
  //С‚СѓС‚ РІСЃРµ Р°РЅР°Р»РѕРіРёС‡РЅРѕ, С‚РѕР»СЊРєРѕ С‚РёРї СЃРѕРѕР±С‰РµРЅРёСЏ MS_INFO
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
