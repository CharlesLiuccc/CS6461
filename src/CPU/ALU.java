package CPU;

import CPU.Register.ConditionCode;
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
        //System.out.println(address);
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
        System.out.println("EA:"+EA);
    }

    //this function is used to calculate with 2 operand and store the value to IRR
    //operation: 1 for add, 2 for subtract
    //3 for multiply, 4 for divide, 5 for remainder
    public void arithmeticCalculate(int operandA,int operandB, int operation){
        switch(operation){
            case 1 ->{this.IRR.setValue(operandA + operandB);}
            case 2 ->{
                this.IRR.setValue(operandA - operandB);
                //System.out.println(operandA+"-"+operandB);
            }
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
                StringBuilder res = new StringBuilder();
                for(int i =0;i<a.length();i++){
                    if(a.charAt(i) == '0'){
                        res.append("1");
                    }
                    else {
                        res.append("0");
                    }
                }
                while(res.length()<16){
                    res.insert(0, "1");
                }
                this.IRR.setValue(Integer.parseInt(res.toString(),2));
            }
        }
    }
    //this function is used to calculate Shift and store the result in IRR
    public void shift(int content, int count, int L_R, int A_L, ConditionCode cc){
        StringBuilder s = new StringBuilder(Integer.toBinaryString(content));
        while (s.length()<16){
            s.insert(0, "0");
        }

        if(L_R==0 && A_L==0){
            //Right & Arithmetic
            char sign = s.charAt(0);
            if(sign == '1'){
                cc.setUnderflow(1);
            }
            else cc.setUnderflow(0);
            StringBuilder res = new StringBuilder(s.substring(1,16-count));
            while (res.length()<16){
                res.insert(0,sign);
            }
            this.IRR.setValue(Integer.parseInt(res.toString(),2));
        }
        else if(L_R==1 && A_L==0){
            //Left & Arithmetic
            char sign = s.charAt(0);
            if(sign == '1'){
                cc.setUnderflow(1);
            }
            else cc.setUnderflow(0);
            StringBuilder res = new StringBuilder(s.substring(1+count,16));
            while (res.length()<15){
                res.append(0);
            }
            res.insert(0,sign);
            this.IRR.setValue(Integer.parseInt(res.toString(),2));
        }
        else if(L_R==0 && A_L==1){
            //Right & Logic
            StringBuilder res = new StringBuilder(s.substring(0,16-count));
            while(res.length()<16){
                res.insert(0,0);
            }
            this.IRR.setValue(Integer.parseInt(res.toString(),2));
        }
        //else if(L_R==1 && A_L==1)
        else{
            //Left & Logic
            StringBuilder res = new StringBuilder(s.substring(count,16));
            while (res.length()<16){
                res.append(0);
            }
            this.IRR.setValue(Integer.parseInt(res.toString(),2));
        }
    }
    //this function is used to calculate Rotate and store the result in IRR
    public void rotate(int content,int count, int L_R, int A_L,ConditionCode cc){
        StringBuilder s = new StringBuilder(Integer.toBinaryString(content));
        while (s.length()<16){
            s.insert(0, "0");
        }
        if(A_L==0){
            //Arithmetic
            char sign = s.charAt(0);
            //Underflow
            if(sign == '1'){
                cc.setUnderflow(1);
            }
            else {
                cc.setUnderflow(0);
            }
            if(L_R==0){
                //Right
                StringBuilder res = new StringBuilder(s.substring(1,16-count));
                res.insert(0,s.substring(16-count,16));
                this.IRR.setValue(Integer.parseInt(res.insert(0,sign).toString(),2));
            }
            else{
                //Left
                StringBuilder res = new StringBuilder(s.substring(count+1,16));
                res.append(s.substring(1,count+1));
                this.IRR.setValue(Integer.parseInt(res.insert(0,sign).toString(),2));
            }
        }
        else{
            //Logic
            if(L_R==0){
                //Right
                StringBuilder res = new StringBuilder(s.substring(0,16-count));
                res.insert(0,s.substring(16-count,16));
                this.IRR.setValue(Integer.parseInt(res.toString(),2));
            }
            else{
                //Left
                StringBuilder res = new StringBuilder(s.substring(count,16));
                res.append(s.substring(0,count));
                this.IRR.setValue(Integer.parseInt(res.toString(),2));
            }
        }
    }
}
