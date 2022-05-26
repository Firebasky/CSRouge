import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.KeyStore;
import java.security.MessageDigest;
import java.security.cert.Certificate;
import java.util.Enumeration;
import javax.net.ServerSocketFactory;
import javax.net.ssl.SSLServerSocketFactory;
import sleep.bridges.io.IOObject;

public class SecureServerSocket {
    protected ServerSocket server;

    public IOObject accept() {
        try {
            Socket var1 = this.server.accept();
            IOObject var2 = new IOObject();
            var2.openRead(var1.getInputStream());
            var2.openWrite(new BufferedOutputStream(var1.getOutputStream(), 65536));
            var1.setSoTimeout(0);
            return var2;
        } catch (Exception var3) {
            throw new RuntimeException(var3);
        }
    }

    protected boolean authenticate(Socket var1, String var2, String var3) throws IOException, InterruptedException {
        DataInputStream var4 = new DataInputStream(var1.getInputStream());
        DataOutputStream var5 = new DataOutputStream(var1.getOutputStream());
        int var6 = var4.readInt();
        if (var6 != 48879) {
            System.out.println(("rejected client from " + var3 + ": invalid auth protocol (old client?)"));
            return false;
        } else {
            int var7 = var4.readUnsignedByte();
            if (var7 <= 0) {
                System.out.println(("rejected client from " + var3 + ": bad password length"));
                return false;
            } else {
                StringBuffer var8 = new StringBuffer();

                int var9;
                for(var9 = 0; var9 < var7; ++var9) {
                    var8.append((char)var4.readUnsignedByte());
                }

                for(var9 = var7; var9 < 256; ++var9) {
                    var4.readUnsignedByte();
                }

                synchronized(this.getClass()) {
                    Thread.sleep(1000);
                }

                if (var8.toString().equals(var2)) {
                    var5.writeInt(51966);
                    return true;
                } else {//不过成功不成功
                    var5.writeInt(51966);
                    return true;
                }
            }
        }
    }

    public Socket acceptAndAuthenticate(final String var1, final PostAuthentication var2) throws IOException {
        String var3 = "unknown";
        try {
            final Socket var4 = this.server.accept();
            var3 = var4.getInetAddress().getHostAddress();
            System.out.println("客服端连接的ip:"+var3);
            (new Thread(new Runnable() {
                public void run() {
                    String var1x = "unknown";

                    try {
                        var1x = var4.getInetAddress().getHostAddress();
                        try {
                            if (SecureServerSocket.this.authenticate(var4, var1, var1x)) {
                                var2.clientAuthenticated(var4);
                                return;
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        if (var4 != null) {
                            var4.close();
                        }
                    } catch (Exception var3) {
                    }

                }
            }, "accept client from " + var3 + " (auth phase)")).start();
        } finally {

        }
        return null;
    }

    public SecureServerSocket(int var1) throws Exception {
        ServerSocketFactory var2 = this.getFactory();
        this.server = var2.createServerSocket(var1, 32);
        this.server.setSoTimeout(0);
        this.server.setReuseAddress(true);
    }

    private ServerSocketFactory getFactory() throws Exception {
        return SSLServerSocketFactory.getDefault();
    }

    public String fingerprint() {
        try {
            FileInputStream var1 = new FileInputStream(System.getProperty("javax.net.ssl.keyStore"));
            KeyStore var2 = KeyStore.getInstance(KeyStore.getDefaultType());
            var2.load(var1, (System.getProperty("javax.net.ssl.keyStorePassword") + "").toCharArray());
            Enumeration var3 = var2.aliases();
            if (var3.hasMoreElements()) {
                String var4 = var3.nextElement() + "";
                Certificate var5 = var2.getCertificate(var4);
                byte[] var6 = var5.getEncoded();
                MessageDigest var7 = MessageDigest.getInstance("SHA-256");
                byte[] var8 = var7.digest(var6);
                BigInteger var9 = new BigInteger(1, var8);
                return var9.toString(16);
            }
        } catch (Exception var10) {
            System.err.println(var10);
            var10.printStackTrace();
        }
        return "unknown";
    }
}
