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
            //decoding for Load/Store Instructions
            case 1,2,3,33,34 ->{
                this.R = Integer.parseInt(instruction.substring(6,8),2);
                this.IX = Integer.parseInt(instruction.substring(8,10),2);
                this.I = Integer.parseInt(instruction.substring(10,11),2);
                this.address = Integer.parseInt(instruction.substring(11,16),2);
            }
        }
    }

    //This function is used in Locate And Fetch Operand Data step
    public void fetching(ALU alu, Memory mem, MemoryAddressRegister mar, MemoryBufferRegister mbr, IndexRegister X1, IndexRegister X2, IndexRegister X3){
        switch (opcode) {
            //LDR and LDX
            case 1, 33 -> {
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
            // using switch statement in case to add more opcode
        }
    }

    //This function is used in Execute the Operation step
    public void executing(ALU alu,MemoryAddressRegister mar, MemoryBufferRegister mbr, GeneralPurposeRegister R0,GeneralPurposeRegister R1, GeneralPurposeRegister R2, GeneralPurposeRegister R3, IndexRegister X1,IndexRegister X2, IndexRegister X3){
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
        }

    }

    //This function is used in Deposit Results step
    public void depositing(ALU alu,Memory mem,MemoryAddressRegister mar,MemoryBufferRegister mbr, GeneralPurposeRegister R0,GeneralPurposeRegister R1, GeneralPurposeRegister R2, GeneralPurposeRegister R3, IndexRegister X1,IndexRegister X2, IndexRegister X3){
        switch (this.opcode){
            case -1 ->{
                //error
            }
            //LDR & LDA
            case 1, 3 ->{
                switch (this.R){
                    case 0 -> R0.setValue(alu.getIRRValue());
                    case 1 -> R1.setValue(alu.getIRRValue());
                    case 2 -> R2.setValue(alu.getIRRValue());
                    case 3 -> R3.setValue(alu.getIRRValue());
                }
            }
            //STR & STX
            case 2, 34 ->{
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
        }
    }

    //This is used in Determining Next Instruction step
    public void nextInstruction(ProgramCounter pc){
        switch (this.opcode){
            case 1,2,3,33,34 ->{
                pc.nextProgram();
            }
        }
    }

}
