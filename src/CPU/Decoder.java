package CPU;

import CPU.Register.*;
import Memory.Memory;



/***
 * This is the class for Decoder.
 * Decoder will do Locate And Fetch Operand Data step,
 * Execute the Operation step
 * and Deposit Results step with separate functions
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
    }

    public int getOpcode() {
        return opcode;
    }
    //This function is used in Decoding
    public void decoding(InstructionRegister ir){
        String instruction = Integer.toBinaryString(ir.getValue());

        //process the instruction
        while(instruction.length()<16){
            instruction="0"+ instruction;
        }

        //decoding for the opcode
        this.opcode=Integer.parseInt(instruction.substring(0,6),2);

        switch (opcode){
            case 0 -> System.out.println("HALT Instruction");
            //decoding for Load/Store Instructions, Transfer Instructions, Arithmetic Instructions
            case 1,2,3,33,34,8,9,10,11,12,13,14,15,4,5,6,7 ->{
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
        }
    }

    //This function is used in Locate And Fetch Operand Data step
    public void fetching(ALU alu, Memory mem, MemoryAddressRegister mar, MemoryBufferRegister mbr, IndexRegister X1, IndexRegister X2, IndexRegister X3){
        switch (opcode) {
            //LDR, LDX, AMR, SMR
            case 1, 33, 4, 5 -> {
                alu.computeEA(this.IX, this.I, this.address,mem, X1, X2, X3);
                mar.setValue(alu.getIARValue());
                mbr.getFromMem(mar, mem);
            }
            // STR, STX
            case 2,34 ->{
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
            //RFS, MUL, DVD
            case 13,16,17 ->{}
            // using switch statement in case to add more opcode
        }
    }

    //This function is used in Execute the Operation step
    public void executing(ALU alu,ProgramCounter pc,MemoryBufferRegister mbr, GeneralPurposeRegister R0,GeneralPurposeRegister R1, GeneralPurposeRegister R2, GeneralPurposeRegister R3, IndexRegister X1,IndexRegister X2, IndexRegister X3,ConditionCode cc) {
        switch (this.opcode) {
            case -1 -> {
                //error
            }
            //LDR, LDA & LDX
            case 1, 3, 33 -> {
                alu.setIRR(mbr.getValue());
            }
            //STR
            case 2 -> {
                switch (this.R) {
                    case 0 -> alu.setIRR(R0.getValue());
                    case 1 -> alu.setIRR(R1.getValue());
                    case 2 -> alu.setIRR(R2.getValue());
                    case 3 -> alu.setIRR(R3.getValue());
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
                    case 0 -> alu.calculate(R0.getValue(), mbr.getValue(), 1);
                    case 1 -> alu.calculate(R1.getValue(), mbr.getValue(), 1);
                    case 2 -> alu.calculate(R2.getValue(), mbr.getValue(), 1);
                    case 3 -> alu.calculate(R3.getValue(), mbr.getValue(), 1);
                }
            }
            //SMR
            case 5 ->{
                switch (this.R) {
                    case 0 -> alu.calculate(R0.getValue(), mbr.getValue(), 2);
                    case 1 -> alu.calculate(R1.getValue(), mbr.getValue(), 2);
                    case 2 -> alu.calculate(R2.getValue(), mbr.getValue(), 2);
                    case 3 -> alu.calculate(R3.getValue(), mbr.getValue(), 2);
                }
            }
            //AIR
            case 6 ->{
                switch (this.R) {
                    case 0 -> alu.calculate(R0.getValue(), this.address, 1);
                    case 1 -> alu.calculate(R1.getValue(), this.address, 1);
                    case 2 -> alu.calculate(R2.getValue(), this.address, 1);
                    case 3 -> alu.calculate(R3.getValue(), this.address, 1);
                }
            }
            //SIR
            case 7 ->{
                switch (this.R) {
                    case 0 -> alu.calculate(R0.getValue(), this.address, 2);
                    case 1 -> alu.calculate(R1.getValue(), this.address, 2);
                    case 2 -> alu.calculate(R2.getValue(), this.address, 2);
                    case 3 -> alu.calculate(R3.getValue(), this.address, 2);
                }
            }
            //MUL
            case 16 -> {
                if (this.Rx == 0 && this.Ry == 2) {
                    alu.calculate(R0.getValue(), R2.getValue(), 3);
                    //process overflow
                    if(alu.getIRRValue() > 65535) {
                        //set cc overflow
                        cc.setOverflow(1);

                        String res = Integer.toBinaryString(alu.getIRRValue());
                        while (res.length() < 32) {
                            res = "0" + res;
                        }
                        R0.setValue(Integer.parseInt(res.substring(0,15),2));
                        R1.setValue(Integer.parseInt(res.substring(16,31),2));
                    }
                    else{
                        cc.setOverflow(0);
                        R1.setValue(alu.getIRRValue());
                    }
                }
                else if (this.Rx == 2 && this.Ry == 0) {
                    alu.calculate(R2.getValue(), R0.getValue(), 3);
                    //process overflow
                    if(alu.getIRRValue() > 65535) {
                        //set cc overflow
                        cc.setOverflow(1);
                        String res = Integer.toBinaryString(alu.getIRRValue());
                        while (res.length() < 32) {
                            res = "0" + res;
                        }
                        R2.setValue(Integer.parseInt(res.substring(0,15),2));
                        R3.setValue(Integer.parseInt(res.substring(16,31),2));
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
                        alu.calculate(R0.getValue(), R2.getValue(), 4);
                        R0.setValue(alu.getIRRValue());
                        alu.calculate(R0.getValue(), R2.getValue(), 5);
                        R1.setValue(alu.getIRRValue());
                    }
                }
                else if(this.Rx==2&&this.Ry==0){
                    //process divide zero
                    if(R0.getValue() == 0){
                        cc.setDivZero(1);
                    }
                    else {
                        cc.setDivZero(0);
                        alu.calculate(R2.getValue(), R0.getValue(), 4);
                        R2.setValue(alu.getIRRValue());
                        alu.calculate(R2.getValue(), R0.getValue(), 5);
                        R3.setValue(alu.getIRRValue());
                    }
                }
                else
                    System.out.println("invalid register for multiplication!");
            }

        }
    }

    //This function is used in Deposit Results step
    public void depositing(ALU alu,ProgramCounter pc,Memory mem,MemoryAddressRegister mar,MemoryBufferRegister mbr, GeneralPurposeRegister R0,GeneralPurposeRegister R1, GeneralPurposeRegister R2, GeneralPurposeRegister R3, IndexRegister X1,IndexRegister X2, IndexRegister X3, ConditionCode cc){
        switch (this.opcode){
            case -1 ->{
                //error
            }
            //LDR, LDA
            case 1,3 ->{
                //process underflow
                if(alu.getIRRValue()<0){
                    cc.setUnderflow(1);
                }
                switch (this.R){
                    case 0 -> R0.setValue(alu.getIRRValue());
                    case 1 -> R1.setValue(alu.getIRRValue());
                    case 2 -> R2.setValue(alu.getIRRValue());
                    case 3 -> R3.setValue(alu.getIRRValue());
                }
            }
            //AMR, AIR
            case 4,6 ->{
                if(alu.getIRRValue()>65535){
                    cc.setOverflow(1);
                }
                else cc.setOverflow(0);
                switch (this.R){
                    case 0 -> R0.setValue(alu.getIRRValue());
                    case 1 -> R1.setValue(alu.getIRRValue());
                    case 2 -> R2.setValue(alu.getIRRValue());
                    case 3 -> R3.setValue(alu.getIRRValue());
                }
            }
            //SMR, SIR
            case 5,7 ->{
                if(alu.getIRRValue()<0){
                    cc.setUnderflow(1);
                }
                else cc.setOverflow(0);
                switch (this.R){
                    case 0 -> R0.setValue(alu.getIRRValue());
                    case 1 -> R1.setValue(alu.getIRRValue());
                    case 2 -> R2.setValue(alu.getIRRValue());
                    case 3 -> R3.setValue(alu.getIRRValue());
                }
            }
            //STR, STX
            case 2,34 ->{
                mbr.setValue(alu.getIRRValue());
                mbr.storeToMem(mar,mem);
            }
            //LDX
            case 33 ->{
                switch (IX){
                    case 1 -> X1.setValue(alu.getIRRValue());
                    case 2 -> X2.setValue(alu.getIRRValue());
                    case 3 -> X3.setValue(alu.getIRRValue());
                }
            }
            //JZ, JNE, JCC, JMA, SOB, JGE, MUL, DVD
            case 8,9,10,11,14,15,16,17 ->{}
            //JSR
            case 12 ->{
                R3.setValue(pc.getValue());
            }
            //RFS
            case 13 ->{
                R0.setValue(this.address);
            }
        }
    }

    //This is used in Determining Next Instruction step
    public void nextInstruction(ProgramCounter pc,ALU alu,ConditionCode cc,GeneralPurposeRegister R0, GeneralPurposeRegister R1, GeneralPurposeRegister R2,GeneralPurposeRegister R3){
        switch (this.opcode){
            //LDR, STR, LDA, LDX, STX, AMR, SMR, AIR, SIR, MUL, DVD
            case 1,2,3,33,34,4,5,6,7,16,17 ->{
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
                if(cc.getValue()==1){
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
        }
    }

}
