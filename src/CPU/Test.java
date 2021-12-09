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
         mem.setToMemory(6,24576);
//         mem.setToMemory(16,20);
//         mem.setToMemory(20,3);
//         x1.setValue(1);
//         gpr1.setValue(7);
//         gpr3.setValue(65533);


         pc.setValue(6);
         mar.getFromPC(pc);
         mbr.getFromMem(mar,mem);
         ir.getFromMBR(mbr);
         decoder.decoding(ir);
         decoder.fetching(alu,mem,mar,mbr,x1,x2,x3);
         decoder.executing(alu,pc,mem,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3,cc,-1);
         decoder.depositing(alu,pc,mem,mar,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3,cc);
         decoder.nextInstruction(pc,alu,cc,gpr0,gpr1,gpr2,gpr3);

//         System.out.println(pc.getValue());
         System.out.println(mem.getFromMemory(1003));
//         System.out.println(gpr1.getValue());
//         System.out.println(Integer.toBinaryString(gpr1.getValue()));
//         System.out.println(cc.getCC(1));

    }
}
