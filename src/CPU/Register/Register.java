package CPU.Register;

/***
 * This class is the super class of all kinds of register
 * This class has 3 Member Variables: size, value, label
 * This class has 2 Constructor and 4 Member Function
 *
 * @author Charles
 */
public class Register {
    private int size;
    private int value;
    public String label;

    private boolean checkOverflow(int value,int length){
        String temp = Integer.toBinaryString(value);
        return temp.length() <= length;
    }

    public Register(){
        this.size=0;
        this.value=0;
        this.label="";
    }

    public Register(int size, int value, String label){
        this.size=size;
        this.value=value;
        this.label=label;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSize() {
        return size;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if(!checkOverflow(value,this.size)) {
            this.value = value;
        }
        else
            this.value=2^this.size;
    }
}
