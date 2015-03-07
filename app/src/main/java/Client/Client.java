package Client;


import com.example.michal.smssync.MainActivity;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import dojson.JSONBroker;
import dojson.Tester;

public class Client extends WebSocketClient {

    public static String address = "apsync.ddns.net/SMSCore/anth";//"89.177.175.113/SMSCore/anth";

    private static Client inst = null;

    private static MainActivity ac;

    public Client(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public void setActivity(MainActivity ac){
        Client.ac = ac;
    }

    public static Client getInst(){

        if(Client.inst == null){
            try {
                Client.inst = new Client(new URI("ws://"+address), new Draft_10());
                Client.inst.connect();
                System.out.println("inst.connect");
            } catch (URISyntaxException e) {
                System.out.println(e.getMessage());
            }
        }


        return Client.inst;
    }


    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        Client.ac.setOpen(true);
    }

    @Override
    public void onMessage(String s) {
       // Client.ac.message(s);
        System.out.println("OnCreate: "+s);

        JSONBroker broker = new JSONBroker();
        Tester tester = new Tester(Client.ac);
        if (s.matches(".*\\{.*")) {
            tester.recognize(s,broker );
        }

       /*
        System.out.println("OnMessage: "+s);
        if(s.charAt(0)=='{'){
            Client.ac.parser(s);
        }*/

    }

    @Override
    public void onClose(int i, String s, boolean b) {
        Client.ac.setOpen(false);
       // Client.ac.close();
            }

    @Override
    public void onError(Exception e) {
        System.out.println("onError"+e.getMessage());
    }
}
