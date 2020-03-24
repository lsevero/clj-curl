import com.sun.jna.Structure;
import com.sun.jna.Pointer;
import java.util.List;
import java.util.Arrays;

public class CurlSlist extends Structure {
    public static class ByReference extends CurlSlist implements Structure.ByReference {
        public ByReference(String data){
            super();
            this.data = data;
        }

        public ByReference(String data, ByReference next) {
            super();
            this.data = data;
            this.next = next;
        }

        public void setData(String data) {
            this.data = data;
        }

        public void setNext(ByReference next) {
            this.next = next;
        }

    };

    public String data;
    public CurlSlist.ByReference next = null;

    public CurlSlist() {
        super();
    }

    @Override
    protected List<String> getFieldOrder() {
        return Arrays.asList(new String[]{"data", "next"});
    }
        
}
