package CPU;

import CPU.Register.GeneralPurposeRegister;
import CPU.Register.IndexRegister;
import CPU.Register.Register;

/***
 * This is the class for ALU.
 * ALU has 2 register inside: IAR and IRR.
 * ALU will do the effective address computation
 *
 * @author Charles


**/
public class ALU {
    private Register IAR;
    private Register IRR;
    public String label;

    public ALU()
    {
        this.IAR = new Register(12,0,"IAR");
        this.IRR = new Register(16,0,"IRR");
        this.label="ALU";
    }

    public int getFromMem(int address){
        return 0;
    }

    public int getFromIXR(IndexRegister IX){
        return IX.getValue();
    }

    public void setIAR(int address){
        this.IAR.setValue(address);
    }

    public int getIARValue() {
        return IAR.getValue();
    }

    public void setIRR(int value){
        this.IRR.setValue(value);
    }

    public int getIRRValue() {
        return IRR.getValue();
    }

    //this function is used to compute the Effective address and store it to IAR
    public void computeEA(int IX, int I,IndexRegister X1,IndexRegister X2,IndexRegister X3){
        int EA = 0;
        if(I==0){
            switch (IX) {
                case 0 -> EA = this.IAR.getValue();
                case 1 -> EA = getFromIXR(X1) + this.IAR.getValue();
                case 2 -> EA = getFromIXR(X2) + this.IAR.getValue();
                case 3 -> EA = getFromIXR(X3) + this.IAR.getValue();
            }
        }
        else if(I==1){
            switch (IX){
                case 0 -> EA = getFromMem(this.IAR.getValue());
                case 1 -> EA = getFromMem(getFromIXR(X1)+this.IAR.getValue());
                case 2 -> EA = getFromMem(getFromIXR(X2)+this.IAR.getValue());
                case 3 -> EA = getFromMem(getFromIXR(X3)+this.IAR.getValue());
            }
        }
        else{
            //error handling
            this.setIAR(2^12);
        }
        this.IAR.setValue(EA);
    }

}
