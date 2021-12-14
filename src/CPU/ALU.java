package CPU;

import CPU.Register.*;
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
        //System.out.println("EA:"+EA);
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
    //this function is used to do the floating point calculation
    public void fpCalculate(FloatingPointRegister fr, int operand, int operation){
        int A_S = fr.getS();
        int A_Ex = fr.getExponent();
        int A_Ma = fr.getMantissa();
        if(A_S == 1){
            A_Ma = -A_Ma;
        }
        //convert operand
        StringBuilder content = new StringBuilder(Integer.toBinaryString(operand));
        while(content.length()<16){
            content.insert(0, "0");
        }
        int B_Ex = Integer.parseInt(content.substring(2,8),2);;
        int B_S=Integer.parseInt(content.substring(0,1),2);
        int B_S_Ex = Integer.parseInt(content.substring(1,2),2);
        if(B_S_Ex == 1){
            B_Ex = -B_Ex;
        }
        int B_Ma = Integer.parseInt(content.substring(8,16),2);
        if(B_S ==1){
            B_Ma=-B_Ma;
        }

        while(A_Ex!=B_Ex){
            if(A_Ex>B_Ex){
                if(A_Ma>127){
                    B_Ex=A_Ex;
                    B_Ma=0;
                    break;
                }
                A_Ex--;
                A_Ma*=2;
            }
            else{
                if(B_Ma>127){
                    A_Ex=B_Ex;
                    A_Ma=0;
                    break;
                }
                B_Ex--;
                B_Ma*=2;
            }
        }
//        System.out.println(A_Ex);
//        System.out.println(A_Ma);
//        System.out.println(B_Ex);
//        System.out.println(B_Ma);

        String res_S = "0";
        //the Mantissa of result
        int Ma;
        if(operation == 0){
            //do the FADD
            Ma = A_Ma+B_Ma;
        }
        else
            Ma = A_Ma-B_Ma;
        if(Ma<0){
            res_S = "1";
            Ma=-Ma;
        }

        if(Ma > 255){
            Ma=Ma/2;
            A_Ex++;
        }
        StringBuilder res_Ma = new StringBuilder(Integer.toBinaryString(Ma));
        while(res_Ma.length()<8){
            res_Ma.insert(0,"0");
        }

        //the exponent of result
        StringBuilder res_Ex;
        res_Ex = new StringBuilder();
        if(A_Ex<0){
            res_Ex.insert(0,1);
            A_Ex = -A_Ex;
        }
        else res_Ex.insert(0,0);
        res_Ex.insert(1,Integer.toBinaryString(A_Ex));
        while(res_Ex.length()<7){
            res_Ex.insert(1, "0");
        }

//        System.out.println(res_Ex);
//        System.out.println(res_Ma);
        int res_value = Integer.parseInt(res_S+res_Ex+res_Ma,2);
        this.IRR.setValue(res_value);
//        System.out.println(res_value);
    }

    public void convert(int F,int target){
        if(F == 0){
//            System.out.println("Alu:"+target);
            this.IRR.setValue(target);
        }
        else if(F == 1){
            int Ex = 0;
            while(target > 255){
                target = target/2;
                Ex++;
            }
            String S="0";
            StringBuilder res_Ex = new StringBuilder(Integer.toBinaryString(Ex));
            StringBuilder res_Ma = new StringBuilder(Integer.toBinaryString(target));
            while(res_Ex.length()<7){res_Ex.insert(0,0);}
            while(res_Ma.length()<8){res_Ma.insert(0,0);}
            this.IRR.setValue(Integer.parseInt(S+res_Ex+res_Ma,2));
        }
    }

    public void vectorOperation(int addr,int fr,int operation,Memory mem){
        StringBuilder content = new StringBuilder(Integer.toBinaryString(fr));
        while(content.length()<16){
            content.insert(0,0);
        }
        String S = content.substring(1,2);
        int Ex = Integer.parseInt(content.substring(2,8),2);
        int Ma = Integer.parseInt(content.substring(8,16),2);
//        System.out.println(Ex);
//        System.out.println(Ma);
        double length;
        if(S == "0"){
            length=Ma;
            while(Ex!=0){
                length=length*2;
                Ex--;
            }
        }else {
            length=Ma;
            while(Ex!=0){
                length=length/2;
                Ex--;
            }
        }
        int vec1 =mem.getFromMemory(addr);
        int vec2 = mem.getFromMemory(addr+1);
//        System.out.println(length);
        if(operation == 0) {
            for (int i = 0; i < length; i++) {
                mem.setToMemory(vec1+i, mem.getFromMemory(vec1 + i) + mem.getFromMemory(vec2 + i));
            }
        }
        else if(operation ==1){
            for (int i = 0; i < length; i++) {
                mem.setToMemory(vec1, mem.getFromMemory(vec1 + i) - mem.getFromMemory(vec2 + i));
            }
        }
    }
}
