package CPU;

import CPU.Register.GeneralPurposeRegister;
import CPU.Register.IndexRegister;
import CPU.Register.Register;
import Memory.Memory;

/***
 * This is the class for ALU.
 * ALU has 2 register inside: IAR and IRR.
 * ALU will do the effective address computation, arithmetic calculation and logic calculation
 *
 * @author Charles
 */

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


    public int getFromMem(int address, Memory mem){
        return mem.getFromMemory(address);
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
    public void computeEA(int IX, int I,int address,Memory mem, IndexRegister X1,IndexRegister X2,IndexRegister X3){
        int EA = 0;
        this.setIAR(address);
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
                case 0 -> EA = getFromMem(this.IAR.getValue(),mem);
                case 1 -> EA = getFromMem(getFromIXR(X1)+this.IAR.getValue(),mem);
                case 2 -> EA = getFromMem(getFromIXR(X2)+this.IAR.getValue(),mem);
                case 3 -> EA = getFromMem(getFromIXR(X3)+this.IAR.getValue(),mem);
            }
        }
        else{
            //error handling
            this.setIAR(2^12);
        }
        this.IAR.setValue(EA);
//        System.out.println(EA);
    }

    //this function is used to calculate with 2 operand and store the value to IRR
    //operation: 1 for add, 2 for subtract
    //3 for multiply, 4 for divide, 5 for remainder
    public void arithmeticCalculate(int operandA,int operandB, int operation){
        switch(operation){
            case 1 ->{this.IRR.setValue(operandA + operandB);}
            case 2 ->{this.IRR.setValue(operandA - operandB);}
            case 3 ->{this.IRR.setValue(operandA * operandB);}
            case 4 ->{this.IRR.setValue(operandA / operandB);}
            case 5 ->{this.IRR.setValue(operandA % operandB);}
        }
    }
    //this function is used to do the logic calculation and store the value to IRR
    //operation: 1 for AND, 2 for ORR, 3 for NOT
    public void logicCalculate(int operandA,int operandB,int operation){
        switch (operation){
            case 1 ->{
                this.IRR.setValue(operandA&operandB);
            }
            case 2 ->{
                this.IRR.setValue(operandA|operandB);
            }
            case 3 ->{
                String a = Integer.toBinaryString(operandA);
                String res = "";
                for(int i =0;i<a.length();i++){
                    if(a.charAt(i) == '0'){
                        res = res + "1";
                    }
                    else {
                        res = res + "0";
                    }
                }
                while(res.length()<16){
                    res = "1"+res;
                }
                this.IRR.setValue(Integer.parseInt(res,2));
            }
        }
    }

}
