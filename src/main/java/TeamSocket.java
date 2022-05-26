import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;


public class TeamSocket {
    protected String from;
    protected boolean connected = true;
    protected Socket client;
    protected OutputStream bout = null;

    public TeamSocket(Socket var1) throws Exception {
        this.client = var1;
        var1.setSoTimeout(0);
        this.from = var1.getInetAddress().getHostAddress();
    }

    public boolean isConnected() {
        synchronized(this) {
            return this.connected;
        }
    }

    public void writeObject(Object var1) {
        if (this.isConnected()) {
            try {
                synchronized(this.client) {
                    if (this.bout == null) {
                        this.bout = new BufferedOutputStream(this.client.getOutputStream(), 262144);
                    }
                    ObjectOutputStream var3 = new ObjectOutputStream(this.bout);
                    var3.writeUnshared(var1);
                    var3.flush();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
