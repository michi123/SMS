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

    public static String address = "apsync.ddns.net/SMSCore/anth";
    private static Client inst = null;
    private static MainActivity ac;

    public Client(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public static Client getInst() {

        if (Client.inst == null) {
            try {
                Client.inst = new Client(new URI("ws://" + address), new Draft_10());
                Client.inst.connect();
            } catch (URISyntaxException e) {
                System.out.println(e.getMessage());
            }
        }
        return Client.inst;
    }

    public void setActivity(MainActivity ac) {
        Client.ac = ac;
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        Client.ac.setOpen(true);
    } //primo otevrit spojeni v Messengeru

    @Override
    public void onMessage(String s) {
        System.out.println("Komunikace : " + s);
        JSONBroker broker = new JSONBroker();
        Tester tester = new Tester(Client.ac);
        if (s.matches(".*\\{.*")) { //pokud komunikace obsahuje "{"
            tester.recognize(s, broker);
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        Client.ac.setOpen(false);
    }

    @Override
    public void onError(Exception e) {
        System.out.println("onError: " + e.getMessage());
    }
}
