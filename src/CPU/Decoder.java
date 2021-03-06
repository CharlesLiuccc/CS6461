package CPU;

import CPU.Register.*;
import Memory.Memory;

//packages for file reading
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;



/***
 * This is the class for Decoder.
 * Decoder will do Decode Instruction step
 * Locate And Fetch Operand Data step,
 * Execute the Operation step
 * Deposit Results step
 * and Find Next Instruction step using separate functions
 *
 * @author Charles
 */

public class Decoder {
    private String label;
    //component for Load/Store, Transfer and part of Arithmetic and Logic Instructions
    private int opcode;
    private int R;
    private int IX;
    private int I;
    private int address;

    //component for part of Arithmetic and Logic Instructions
    private int Rx;
    private int Ry;

    //component for Shift and Rotate Operation Instructions
    private int A_L;
    private int L_R;
    private int count;

    //Component for I/O Instructions
    private int dev_id;
    private int out_value;

    //component for TRAP Instruction
    private int trap_code;


    public Decoder(){
        this.label="Decoder";
        this.opcode=-1;
        this.R = -1;
        this.IX=-1;
        this.I=-1;
        this.address=-1;
        this.Rx=-1;
        this.Ry=-1;
        this.A_L=-1;
        this.L_R=-1;
        this.count=-1;
        this.dev_id=-1;
        this.out_value=-1;
        this.trap_code = -1;
    }

    public int getOpcode() {
        return opcode;
    }
    //Get value to print out
    public int getOut_value(){
        return out_value;
    }
    //This function is used in Decoding
    public void decoding(InstructionRegister ir){
        StringBuilder instruction = new StringBuilder(Integer.toBinaryString(ir.getValue()));

        //process the instruction
        while(instruction.length()<16){
            instruction.insert(0, "0");
        }
        System.out.println("Instruction:"+instruction);

        //decoding for the opcode
        this.opcode=Integer.parseInt(instruction.substring(0,6),2);
        //System.out.println("opCode:"+opcode);

        switch (opcode){
            case 0 -> System.out.println("HALT Instruction");
            //decoding for Load/Store Instructions, Transfer Instructions, Arithmetic Instructions
            case 1,2,3,33,34,8,9,10,11,12,13,14,15,4,5,6,7,40,41,27,28,29,30,31 ->{
                this.R = Integer.parseInt(instruction.substring(6,8),2);
                this.IX = Integer.parseInt(instruction.substring(8,10),2);
                this.I = Integer.parseInt(instruction.substring(10,11),2);
                this.address = Integer.parseInt(instruction.substring(11,16),2);
            }
            //decoding for Arithmetic and Logic Instructions
            case 16,17,18,19,20,21 ->{
                this.Rx=Integer.parseInt(instruction.substring(6,8),2);
                this.Ry=Integer.parseInt(instruction.substring(8,10),2);
            }
            //decoding for Shift/Rotate Instructions
            case 25,26 ->{
                this.R = Integer.parseInt(instruction.substring(6,8),2);
                this.A_L = Integer.parseInt(instruction.substring(8,9),2);
                this.L_R = Integer.parseInt(instruction.substring(9,10),2);
                this.count = Integer.parseInt(instruction.substring(12,16),2);
            }
            //decoding for I/O Instructions
            case 49,50 -> {
                this.R = Integer.parseInt(instruction.substring(6,8),2);
                this.dev_id = Integer.parseInt(instruction.substring(11,16),2);
            }
            //decoding for Trap Instruction
            case 24 ->{
                this.trap_code = Integer.parseInt(instruction.substring(11,16),2);
            }
        }
    }

    //This function is used in Locate And Fetch Operand Data step
    public void fetching(ALU alu, Memory mem, MemoryAddressRegister mar, MemoryBufferRegister mbr, IndexRegister X1, IndexRegister X2, IndexRegister X3){
        switch (opcode) {
            //LDR, LDX, AMR, SMR, LDFR, FADD, FSUB, VADD,VSUB,CNVRT
            case 1, 33, 4, 5, 40, 27, 28, 29, 30, 31 -> {
                alu.computeEA(this.IX, this.I, this.address,mem, X1, X2, X3);
                mar.setValue(alu.getIARValue());
                mbr.getFromMem(mar, mem);
            }
            // STR, STX, STFR
            case 2, 34, 41 ->{
                alu.computeEA(this.IX, this.I, this.address,mem, X1, X2, X3);
                mar.setValue(alu.getIARValue());
            }
            //LDA
            case 3 ->{
                alu.computeEA(this.IX,this.I,this.address,mem,X1,X2,X3);
                mbr.setValue(alu.getIARValue());
            }
            //JZ, JNE, JCC, JMA, JSR, SOB,JGE
            case 8,9,10,11,12,14,15 ->{
                alu.computeEA(this.IX,this.I,this.address,mem,X1,X2,X3);
            }
            //AIR, SIR, RFS, MUL, DVD, TRR, AND, ORR, NOT, SRC, RRC, IN, OUT, TRAP
            case 6,7,13,16,17,18,19,20,21,25,26,49,50, 24 ->{}
            // using switch statement in case to add more opcode
        }
    }

    //This function is used in Execute the Operation step
    public void executing(ALU alu,ProgramCounter pc,Memory mem,MemoryAddressRegister mar,MemoryBufferRegister mbr, GeneralPurposeRegister R0,GeneralPurposeRegister R1, GeneralPurposeRegister R2, GeneralPurposeRegister R3,FloatingPointRegister fr0,FloatingPointRegister fr1 ,IndexRegister X1,IndexRegister X2, IndexRegister X3,ConditionCode cc, int in_value) {
        switch (this.opcode) {
            case -1 -> {
                //error
            }
            //LDR, LDA, LDX, LDFR
            case 1, 3, 33, 40 -> {
                alu.setIRR(mbr.getValue());
            }
            //STR, OUT
            case 2,50 -> {
                switch (this.R) {
                    case 0 -> alu.setIRR(R0.getValue());
                    case 1 -> alu.setIRR(R1.getValue());
                    case 2 -> alu.setIRR(R2.getValue());
                    case 3 -> alu.setIRR(R3.getValue());
                }
            }
            //STFR
            case 41 ->{
                switch (this.R){
                    case 0 -> alu.setIRR(fr0.getValue());
                    case 1 -> alu.setIRR(fr1.getValue());
                }
            }
            //STX
            case 34 -> {
                switch (IX) {
                    case 1 -> alu.setIRR(X1.getValue());
                    case 2 -> alu.setIRR(X2.getValue());
                    case 3 -> alu.setIRR(X3.getValue());
                }
            }
            //JZ, JNE, JCC, JMA, RFS, JGE
            case 8, 9, 10, 11, 13, 15 -> {
            }
            //JSR
            case 12 -> {
                pc.nextProgram();
            }
            //SOB
            case 14 -> {
                switch (this.R) {
                    case 0 -> R0.setValue(R0.getValue() - 1);
                    case 1 -> R1.setValue(R1.getValue() - 1);
                    case 2 -> R2.setValue(R2.getValue() - 1);
                    case 3 -> R3.setValue(R3.getValue() - 1);
                }
            }
            //AMR
            case 4 -> {
                switch (this.R) {
                    case 0 -> alu.arithmeticCalculate(R0.getValue(), mbr.getValue(), 1);
                    case 1 -> alu.arithmeticCalculate(R1.getValue(), mbr.getValue(), 1);
                    case 2 -> alu.arithmeticCalculate(R2.getValue(), mbr.getValue(), 1);
                    case 3 -> alu.arithmeticCalculate(R3.getValue(), mbr.getValue(), 1);
                }
            }
            //SMR
            case 5 ->{
                switch (this.R) {
                    case 0 -> alu.arithmeticCalculate(R0.getValue(), mbr.getValue(), 2);
                    case 1 -> alu.arithmeticCalculate(R1.getValue(), mbr.getValue(), 2);
                    case 2 -> alu.arithmeticCalculate(R2.getValue(), mbr.getValue(), 2);
                    case 3 -> alu.arithmeticCalculate(R3.getValue(), mbr.getValue(), 2);
                }
            }
            //AIR
            case 6 ->{
                switch (this.R) {
                    case 0 -> alu.arithmeticCalculate(R0.getValue(), this.address, 1);
                    case 1 -> alu.arithmeticCalculate(R1.getValue(), this.address, 1);
                    case 2 -> alu.arithmeticCalculate(R2.getValue(), this.address, 1);
                    case 3 -> alu.arithmeticCalculate(R3.getValue(), this.address, 1);
                }
            }
            //SIR
            case 7 ->{
                switch (this.R) {
                    case 0 -> alu.arithmeticCalculate(R0.getValue(), this.address, 2);
                    case 1 -> alu.arithmeticCalculate(R1.getValue(), this.address, 2);
                    case 2 -> alu.arithmeticCalculate(R2.getValue(), this.address, 2);
                    case 3 -> alu.arithmeticCalculate(R3.getValue(), this.address, 2);
                }
            }
            //FADD
            case 27 ->{
                switch (this.R){
                    case 0 ->{alu.fpCalculate(fr0,mbr.getValue(),0);}
                    case 1 ->{alu.fpCalculate(fr1,mbr.getValue(),0);}
                }
            }
            //FSUB
            case 28 ->{
                switch (this.R){
                    case 0 ->{alu.fpCalculate(fr0, mbr.getValue(), 1);}
                    case 1 ->{alu.fpCalculate(fr1, mbr.getValue(), 1);}
                }
            }
            //VADD
            case 29 ->{
                switch (this.R){
                    case 0 ->{alu.vectorOperation(mar.getValue(),fr0.getValue(),0,mem);}
                    case 1 ->{alu.vectorOperation(mar.getValue(),fr1.getValue(),0,mem);}
                }
            }
            //VSUB
            case 30 ->{
                switch (this.R){
                    case 0 ->{alu.vectorOperation(mar.getValue(),fr0.getValue(),1,mem);}
                    case 1 ->{alu.vectorOperation(mar.getValue(),fr1.getValue(),1,mem);}
                }
            }
            //CNVRT
            case 31 ->{
                alu.convert(getGPR(this.R,R0,R1,R2,R3).getValue(), mbr.getValue());
            }
            //MUL
            case 16 -> {
                if (this.Rx == 0 && this.Ry == 2) {
                    alu.arithmeticCalculate(R0.getValue(), R2.getValue(), 3);
                    //process overflow
                    if(alu.getIRRValue() > 65535) {
                        //set cc overflow
                        cc.setOverflow(1);

                        StringBuilder res = new StringBuilder(Integer.toBinaryString(alu.getIRRValue()));
                        while (res.length() < 32) {
                            res.insert(0, "0");
                        }
                        R0.setValue(Integer.parseInt(res.substring(0,16),2));
                        R1.setValue(Integer.parseInt(res.substring(16,32),2));
                    }
                    else{
                        cc.setOverflow(0);
                        R1.setValue(alu.getIRRValue());
                    }
                }
                else if (this.Rx == 2 && this.Ry == 0) {
                    alu.arithmeticCalculate(R2.getValue(), R0.getValue(), 3);
                    //process overflow
                    if(alu.getIRRValue() > 65535) {
                        //set cc overflow
                        cc.setOverflow(1);
                        StringBuilder res = new StringBuilder(Integer.toBinaryString(alu.getIRRValue()));
                        while (res.length() < 32) {
                            res.insert(0, "0");
                        }
                        R2.setValue(Integer.parseInt(res.substring(0,16),2));
                        R3.setValue(Integer.parseInt(res.substring(16,32),2));
                    }
                    else{
                        cc.setOverflow(0);
                        R1.setValue(alu.getIRRValue());
                    }
                }
                else
                    System.out.println("invalid register for multiplication!");
            }
            //DVD
            case 17 ->{
                if(this.Rx == 0 && this.Ry==2){
                    //process divide zero
                    if(R2.getValue() == 0){
                        cc.setDivZero(1);
                    }
                    else {
                        cc.setDivZero(0);
                        alu.arithmeticCalculate(R0.getValue(), R2.getValue(), 5);
                        R1.setValue(alu.getIRRValue());
                        alu.arithmeticCalculate(R0.getValue(), R2.getValue(), 4);
                        R0.setValue(alu.getIRRValue());

                    }
                }
                else if(this.Rx==2&&this.Ry==0){
                    //process divide zero
                    if(R0.getValue() == 0){
                        cc.setDivZero(1);
                    }
                    else {
                        cc.setDivZero(0);
                        alu.arithmeticCalculate(R2.getValue(), R0.getValue(), 5);
                        R3.setValue(alu.getIRRValue());
                        alu.arithmeticCalculate(R2.getValue(), R0.getValue(), 4);
                        R2.setValue(alu.getIRRValue());
                    }
                }
                else
                    System.out.println("invalid register for multiplication!");
            }
            //TRR
            case 18 -> {
                if(getGPR(this.Rx,R0,R1,R2,R3).getValue() == getGPR(this.Ry,R0,R1,R2,R3).getValue()){
                    cc.setEqualOrNot(1);
                }
                else
                    cc.setEqualOrNot(0);
            }
            //AND, ORR, NOT
            case 19,20,21 ->{
                alu.logicCalculate(getGPR(this.Rx,R0,R1,R2,R3).getValue(),getGPR(this.Ry,R0,R1,R2,R3).getValue(),this.opcode-18);
            }
            //SRC
            case 25 ->{
                alu.shift(getGPR(this.R,R0,R1,R2,R3).getValue(),this.count,this.L_R,this.A_L,cc);
            }
            //RRC
            case 26 ->{
                alu.rotate(getGPR(this.R,R0,R1,R2,R3).getValue(),this.count,this.L_R,this.A_L,cc);
            }
            //IN
            case 49 -> {
                alu.setIRR(in_value);
            }
            //TRAP
            case 24 ->{
                mem.setToMemory(2,pc.getValue()+1);
                trapTable(trap_code,pc,mem);
            }
        }
    }

    //This function is used in Deposit Results step
    public void depositing(ALU alu,ProgramCounter pc,Memory mem,MemoryAddressRegister mar,MemoryBufferRegister mbr, GeneralPurposeRegister R0,GeneralPurposeRegister R1, GeneralPurposeRegister R2,GeneralPurposeRegister R3,FloatingPointRegister fr0,FloatingPointRegister fr1, IndexRegister X1,IndexRegister X2, IndexRegister X3, ConditionCode cc){
        switch (this.opcode){
            case -1 ->{
                //error
            }
            //LDR, LDA, SRC, RRC, IN
            case 1,3,25,26,49 ->{
//                switch (this.R){
//                    case 0 -> R0.setValue(alu.getIRRValue());
//                    case 1 -> R1.setValue(alu.getIRRValue());
//                    case 2 -> R2.setValue(alu.getIRRValue());
//                    case 3 -> R3.setValue(alu.getIRRValue());
//                }
                getGPR(this.R,R0,R1,R2,R3).setValue(alu.getIRRValue());
            }
            //LDFR
            case 40 ->{
                switch (this.R){
                    case 0 ->{fr0.setFPValue(alu.getIRRValue());}
                    case 1 ->{fr1.setFPValue(alu.getIRRValue());}
                }
            }
            //AMR, SMR, AIR, SIR
            case 4,5,6,7 ->{
                if(alu.getIRRValue()>65535){
                    cc.setOverflow(1);
                }
                else cc.setOverflow(0);
                if(alu.getIRRValue()<0){
                    cc.setUnderflow(1);
                }
                else cc.setUnderflow(0);
//                switch (this.R){
//                    case 0 -> R0.setValue(alu.getIRRValue());
//                    case 1 -> R1.setValue(alu.getIRRValue());
//                    case 2 -> R2.setValue(alu.getIRRValue());
//                    case 3 -> R3.setValue(alu.getIRRValue());
//                }
                getGPR(this.R,R0,R1,R2,R3).setValue(alu.getIRRValue());
            }
            //FADD,FSUB
            case 27,28 ->{
                if(alu.getIRRValue()==-1){
                    cc.setOverflow(1);
                }
                else cc.setOverflow(0);
                if(alu.getIRRValue()==-2){
                    cc.setUnderflow(1);
                }
                else cc.setUnderflow(0);

                switch (this.R){
                    case 0 ->{fr0.setFPValue(alu.getIRRValue());}
                    case 1 ->{fr1.setFPValue(alu.getIRRValue());}
                }
            }
            //CNVRT
            case 31 ->{
                if(getGPR(this.R,R0,R1,R2,R3).getValue() == 0){
                    getGPR(this.R,R0,R1,R2,R3).setValue(alu.getIRRValue());
                }
                else{fr0.setFPValue(alu.getIRRValue());}
            }
//            //SMR, SIR
//            case 5,7 ->{
//                if(alu.getIRRValue()<0){
//                    cc.setUnderflow(1);
//                }
//                else cc.setOverflow(0);
////                switch (this.R){
////                    case 0 -> R0.setValue(alu.getIRRValue());
////                    case 1 -> R1.setValue(alu.getIRRValue());
////                    case 2 -> R2.setValue(alu.getIRRValue());
////                    case 3 -> R3.setValue(alu.getIRRValue());
////                }
//                getGPR(this.R,R0,R1,R2,R3).setValue(alu.getIRRValue());
//            }
            //STR, STX, STFR
            case 2,34,41 ->{
                mbr.setValue(alu.getIRRValue());
                mbr.setToMem(mar,mem);
            }
            //LDX
            case 33 ->{
                //System.out.println("IRR:"+alu.getIRRValue());
                //System.out.println("IX:"+IX);
                switch (IX){
                    case 1 -> X1.setValue(alu.getIRRValue());
                    case 2 -> X2.setValue(alu.getIRRValue());
                    case 3 -> X3.setValue(alu.getIRRValue());
                }
            }
            //JZ, JNE, JCC, JMA, SOB, JGE, MUL, DVD, TRR, TRAP, VADD, VSUB
            case 8,9,10,11,14,15,16,17,18,24,29,30 ->{}
            //JSR
            case 12 ->{
                R3.setValue(pc.getValue());
            }
            //RFS
            case 13 ->{
                R0.setValue(this.address);
            }
            //AND, ORR, NOT
            case 19,20,21 ->{
                getGPR(Rx,R0,R1,R2,R3).setValue(alu.getIRRValue());
            }
            //OUT
            case 50 ->{
                this.out_value = alu.getIRRValue();
            }

        }
    }

    //This is used in Determining Next Instruction step
    public void nextInstruction(ProgramCounter pc,ALU alu,ConditionCode cc,GeneralPurposeRegister R0, GeneralPurposeRegister R1, GeneralPurposeRegister R2,GeneralPurposeRegister R3){
        switch (this.opcode){
            //LDR, STR, LDA, LDX, STX, AMR, SMR, AIR, SIR, MUL, DVD, TRR, AND, ORR, NOT, SRC, RRC, IN, OUT,LDFR,STFR,FADD,FSUB,VADD,VSUB,CNVRT
            case 1,2,3,33,34,4,5,6,7,16,17,18,19,20,21,25,26,49,50,40,41,27,28,29,30,31 ->{
                pc.nextProgram();
            }
            //JZ
            case 8 ->{
                int content = -1;
                switch (this.R){
                    case 0 -> content = R0.getValue();
                    case 1 -> content = R1.getValue();
                    case 2 -> content = R2.getValue();
                    case 3 -> content = R3.getValue();
                }
                if(content == 0){
                    pc.setValue(alu.getIARValue());
                }
                else pc.nextProgram();
            }
            //JNE
            case 9 ->{
                int content = -1;
                switch (this.R){
                    case 0 -> content = R0.getValue();
                    case 1 -> content = R1.getValue();
                    case 2 -> content = R2.getValue();
                    case 3 -> content = R3.getValue();
                }
                if(content != 0 ){
                    pc.setValue(alu.getIARValue());
                }
                else pc.nextProgram();

            }
            //JCC
            case 10 ->{
                System.out.println("CC:"+cc.getCC(this.R));
                if(cc.getCC(this.R)==1){
                    pc.setValue(alu.getIARValue());
                }
                else pc.nextProgram();
            }
            //JMA, JSR
            case 11,12 ->{
                pc.setValue(alu.getIARValue());
            }
            //RFS
            case 13 ->{
                pc.setValue(R3.getValue());
            }
            //SOB
            case 14 ->{
                int content = -1;
                switch (this.R){
                    case 0 -> content = R0.getValue();
                    case 1 -> content = R1.getValue();
                    case 2 -> content = R2.getValue();
                    case 3 -> content = R3.getValue();
                }
                if(content > 0 ){
                    pc.setValue(alu.getIARValue());
                }
                else pc.nextProgram();
            }
            //JGE
            case 15 ->{
                int content = -1;
                switch (this.R){
                    case 0 -> content = R0.getValue();
                    case 1 -> content = R1.getValue();
                    case 2 -> content = R2.getValue();
                    case 3 -> content = R3.getValue();
                }
                if(content >= 0 ){
                    pc.setValue(alu.getIARValue());
                }
                else pc.nextProgram();
            }
            //TRAP
            //Trap instruction will do nothing at this step
            //As the instructions in trap table will make pc to return
            case 24 ->{}
        }
    }

    private Register getGPR(int num,GeneralPurposeRegister R0,GeneralPurposeRegister R1,GeneralPurposeRegister R2, GeneralPurposeRegister R3){
        switch (num){
            case 0 -> {
                return R0;
            }
            case 1 ->{
                return R1;
            }
            case 2 ->{
                return R2;
            }
            case 3 ->{
                return R3;
            }
        }
        return null;
    }

    private void trapTable(int trap_code,ProgramCounter pc,Memory mem){
        switch (trap_code){
            //read paragraph from txt
            case 0 ->{
                String root = System.getProperty("user.dir");
                String pathName = root+"\\paragraph.txt";
                System.out.println(pathName);
                try(FileReader reader = new FileReader(pathName); BufferedReader br =new BufferedReader(reader)){
                    String line = br.readLine();
                    int position = 1100;
//                    System.out.println(line);
                    while(line.length()!=0){
                        mem.setToMemory(position++,(int) line.charAt(0));
                        line = line.substring(1,line.length());
                    }
                } catch (IOException e){
                    e.printStackTrace();
                }
            }
            //extern the trap table by more cases
            //Attention: trap table is no more than 16 entries
            case 1,2,3,4,5,6,7,8,9,10,11,12,13,14,15 ->{}
        }
        pc.setValue(mem.getFromMemory(2));
    }
}
