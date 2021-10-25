package CPU.Register;

/***
 * This is the class for Condition Code Register
 * CC just has 4 bits in length
 *
 * @author Charles
 */

public class ConditionCode extends Register{
    private int cc0;
    private int cc1;
    private int cc2;
    private int cc3;

    public ConditionCode(){
        super(4,0,"CC");
        this.cc0 = 0;
        this.cc1 = 0;
        this.cc2 = 0;
        this.cc3 = 0;
    }

    public void setOverflow(int x){
        this.cc0=x;
        this.setCCValue();
    }

    public void setUnderflow(int x){
        this.cc1=x;
        this.setCCValue();
    }

    public void setDivZero(int x){
        this.cc2=x;
        this.setCCValue();
    }

    public void setEqualOrNot(int x){
        this.cc3=x;
        this.setCCValue();
    }

    public void refreshCC(){
        this.cc0=0;
        this.cc1=0;
        this.cc2=0;
        this.cc3=0;
        this.setValue(0);
    }

    private void setCCValue(){
        this.setValue(this.cc0*8+this.cc1*4+this.cc2*2+this.cc3);
    }

    public int getCC(int bit){
        switch (bit) {
            case 0 -> {
                return this.cc0;
            }
            case 1 -> {
                return this.cc1;
            }
            case 2 -> {
                return this.cc2;
            }
            case 3 -> {
                return this.cc3;
            }
        }
        return -1;
    }


}
