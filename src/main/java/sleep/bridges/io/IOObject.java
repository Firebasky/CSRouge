//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package sleep.bridges.io;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class IOObject {
    protected InputStreamReader readeru = null;
    protected DataInputStream readerb = null;
    protected BufferedInputStream reader = null;
    protected InputStream in = null;
    protected OutputStreamWriter writeru = null;
    protected DataOutputStream writerb = null;
    protected OutputStream out = null;

    public IOObject() {
    }
    public void openRead(InputStream var1) {
        this.in = var1;
        if (this.in != null) {
            this.reader = new BufferedInputStream(this.in, 8192);
            this.readerb = new DataInputStream(this.reader);
            this.readeru = new InputStreamReader(this.readerb);
        }

    }

    public void openWrite(OutputStream var1) {
        this.out = var1;
        if (this.out != null) {
            this.writerb = new DataOutputStream(this.out);
            this.writeru = new OutputStreamWriter(this.writerb);
        }

    }
}
