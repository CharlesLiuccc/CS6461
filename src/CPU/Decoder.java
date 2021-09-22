package CPU;

import CPU.Register.*;

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
    private int opcode;
    private int R;
    private int IX;
    private int I;
    private int address;

    public Decoder(){
        this.label="Decoder";
        this.opcode=-1;
        this.R = -1;
        this.IX=-1;
        this.I=-1;
        this.address=-1;
    }

    //This function is used in Locate And Fetch Operand Data step
    public void decoding(ALU alu, InstructionRegister ir, MemoryAddressRegister mar, MemoryBufferRegister mbr, IndexRegister X1, IndexRegister X2, IndexRegister X3){
        String instruction = Integer.toBinaryString(ir.getValue());

        while(instruction.length()<16){
            instruction="0"+ instruction;
        }

        this.opcode=Integer.parseInt(instruction.substring(0,6));
        this.R = Integer.parseInt(instruction.substring(6,8));
        this.IX = Integer.parseInt(instruction.substring(8,10));
        this.I = Integer.parseInt(instruction.substring(10,11));
        this.address = Integer.parseInt(instruction.substring(11,16));

        alu.setIAR(this.address);
        alu.computeEA(this.IX,this.I,X1,X2,X3);
        mar.setValue(alu.getIARValue());

        switch (opcode){
            //LDR
            case 1 -> mbr.getFromMem(mar.getValue());
            //LDX
            case 41 -> mbr.getFromMem(mar.getValue());
            // STR, LDA, STX won't react with memory at this step
            // using switch statement in case to add more opcode
        }
    }

    //This function is used in Execute the Operation step
    public void executing(ALU alu,MemoryAddressRegister mar, MemoryBufferRegister mbr, GeneralPurposeRegister R0,GeneralPurposeRegister R1, GeneralPurposeRegister R2, GeneralPurposeRegister R3, IndexRegister X0,IndexRegister X1, IndexRegister X2){
        switch (this.opcode) {
            case -1 -> {
                //error
            }
            //LDR & LDX
            case 1, 41 -> {
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
            //LDA
            case 3 -> {
                alu.setIRR(mar.getValue());
            }
            //STX
            case 42 -> {
                switch (IX) {
                    case 0 -> alu.setIRR(X0.getValue());
                    case 1 -> alu.setIRR(X1.getValue());
                    case 2 -> alu.setIRR(X2.getValue());
                }
            }
        }

    }

    //This function is used in Deposit Results step
    public void depositing(ALU alu, GeneralPurposeRegister R0,GeneralPurposeRegister R1, GeneralPurposeRegister R2, GeneralPurposeRegister R3, IndexRegister X0,IndexRegister X1, IndexRegister X2){
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
            //STR
            case 2 ->{
                //set value of IRR to MEM[EA]
                //EA is stored in IAR in ALU
            }
            //LDX
            case 41 ->{
                switch (IX){
                    case 0 -> X0.setValue(alu.getIRRValue());
                    case 1 -> X1.setValue(alu.getIRRValue());
                    case 2 -> X2.setValue(alu.getIRRValue());
                }
            }
            //STX
             case 42 ->{
                 //set value of IRR to MEM[EA]
                 //EA is stored in IAR in ALU
             }
        }
    }

}
