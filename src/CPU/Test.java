package CPU;

import CPU.Register.*;
import Memory.Memory;

public class Test {
    public static ProgramCounter pc = new ProgramCounter();
    public static MemoryBufferRegister mbr = new MemoryBufferRegister();
    public static MemoryAddressRegister mar = new MemoryAddressRegister();
    public static MachineFaultRegister mfr = new MachineFaultRegister(0);
    public static InstructionRegister ir = new InstructionRegister();
    public static IndexRegister x1 = new IndexRegister(0);
    public static IndexRegister x2 = new IndexRegister(0);
    public static IndexRegister x3 = new IndexRegister(0);
    public static GeneralPurposeRegister gpr0 = new GeneralPurposeRegister(0);
    public static GeneralPurposeRegister gpr1 = new GeneralPurposeRegister(0);
    public static GeneralPurposeRegister gpr2 = new GeneralPurposeRegister(0);
    public static GeneralPurposeRegister gpr3 = new GeneralPurposeRegister(0);
    public static ConditionCode cc = new ConditionCode();
    public static Memory mem = new Memory();
    public static Decoder decoder = new Decoder();
    public static ALU alu = new ALU();

    public static void main(String[] args) {
//        //STX Test success
//        mem.setToMemory(6,43948); //101010 11 10 1 01100
//
//        x2.setValue(8);
//        mem.setToMemory(20,14);
//
//        pc.setValue(6);

//        mar.getFromPC(pc);
//        mbr.getFromMem(mar,mem);
//        ir.getFromMBR(mbr);
//        decoder.decoding(alu,mem,ir,mar,mbr,x1,x2,x3);
//        decoder.executing(alu,mar,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3);
//        decoder.depositing(alu,mem,mar,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3);

//        System.out.println(mem.getFromMemory(14));


//        //LDX Test success
//        mem.setToMemory(6,42924);//101001 11 10 1 01100
//        mem.setToMemory(12,28);
//        mem.setToMemory(20,12);
//
//        x2.setValue(8);
//
//        pc.setValue(6);
//        mar.getFromPC(pc);
//        mbr.getFromMem(mar,mem);
//        ir.getFromMBR(mbr);
//        decoder.decoding(alu,mem,ir,mar,mbr,x1,x2,x3);
//        decoder.executing(alu,mar,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3);
//        decoder.depositing(alu,mem,mar,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3);
//        System.out.println(x3.getValue());




//        //LDA Test success
//        mem.setToMemory(6,3756);//000001 11 10 1 01100
//      //  mem.setToMemory(12,28);
//        mem.setToMemory(20,18);
//
//        x2.setValue(8);
//
//        pc.setValue(6);
//        mar.getFromPC(pc);
//        mbr.getFromMem(mar,mem);
////        System.out.println(mbr.getValue());
//        ir.getFromMBR(mbr);
//        decoder.decoding(alu,mem,ir,mar,mbr,x1,x2,x3);
//
////        System.out.println(alu.getIARValue());
////        System.out.println(mbr.getValue());
//        decoder.executing(alu,mar,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3);
//        decoder.depositing(alu,mem,mar,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3);
//        System.out.println(gpr2.getValue());


//        //STR Test success
//        mem.setToMemory(6,2572);
//        gpr2.setValue(512);
//        pc.setValue(6);

//        mar.getFromPC(pc);
//        mbr.getFromMem(mar,mem);
//        ir.getFromMBR(mbr);
//        decoder.decoding(alu,mem,ir,mar,mbr,x1,x2,x3);
//        decoder.executing(alu,mar,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3);
//        decoder.depositing(alu,mem,mar,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3);
//        System.out.println(mem.getFromMemory(12));



        // LDR Test success
//        mem.setToMemory(6,1964);//000001 11 10 1 01100
//        mem.setToMemory(12,28);
//        mem.setToMemory(20,12);
//
//        x2.setValue(8);
//
//        pc.setValue(6);
//        mar.getFromPC(pc);
//        mbr.getFromMem(mar,mem);
//        //System.out.println(mbr.getValue());
//        ir.getFromMBR(mbr);
//        decoder.decoding(alu,mem,ir,mar,mbr,x1,x2,x3);
////        System.out.println(alu.getIARValue());
////        System.out.println(mbr.getValue());
//        decoder.executing(alu,mar,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3);
//        decoder.depositing(alu,mem,mar,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3);
//        System.out.println(gpr3.getValue());

    }
}
