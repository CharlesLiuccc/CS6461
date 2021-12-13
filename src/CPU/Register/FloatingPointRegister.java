package CPU.Register;

/***
 * We will have 2 FRs in our simulator: fr1 and fr2
 * Each FR will have 16 bits in length
 *
 * @author Charles
 */

public class FloatingPointRegister extends Register{
    private int S;
    private int Exponent;
    private int Mantissa;
    public FloatingPointRegister(int value){
        super(16,value,"fr");
        StringBuilder content = new StringBuilder(Integer.toBinaryString(value));
        while(content.length()<16){
            content.insert(0, "0");
        }
        this.S=Integer.parseInt(content.substring(0,1),2);
        int s_Ex = Integer.parseInt(content.substring(1,2),2);
        if(s_Ex == 0){this.Exponent = Integer.parseInt(content.substring(2,8),2);}
        else this.Exponent = -Integer.parseInt(content.substring(2,8),2);
        this.Mantissa = Integer.parseInt(content.substring(8,16),2);
    }

    public void setFPValue(int value){
        this.setValue(value);
        StringBuilder content = new StringBuilder(Integer.toBinaryString(value));
        while(content.length()<16){
            content.insert(0, "0");
        }
        this.S=Integer.parseInt(content.substring(0,1),2);
        int s_Ex = Integer.parseInt(content.substring(1,2),2);
        if(s_Ex == 0){this.Exponent = Integer.parseInt(content.substring(2,8),2);}
        else this.Exponent = -Integer.parseInt(content.substring(2,8),2);
        this.Mantissa = Integer.parseInt(content.substring(8,16),2);
    }

    public int getS(){
        return this.S;
    }

    public int getExponent(){
        return this.Exponent;
    }

    public int getMantissa() {
        return this.Mantissa;
    }
}
