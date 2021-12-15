package CPU;

import CPU.Register.*;
import Memory.Memory;
import java.lang.Math;

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
    public static FloatingPointRegister fr0 = new FloatingPointRegister(0);
    public static FloatingPointRegister fr1 = new FloatingPointRegister(0);
    public static ConditionCode cc = new ConditionCode();
    public static Memory mem = new Memory();
    public static Decoder decoder = new Decoder();
    public static ALU alu = new ALU();

    public static void main(String[] args) {
         mem.setToMemory(6,40980);
         mem.setToMemory(7,27669);
         mem.setToMemory(8,42006);
         mem.setToMemory(9,31766);
         mem.setToMemory(10,29726);

         mem.setToMemory(20,513);
         mem.setToMemory(21,1);

         mem.setToMemory(30,1000);
         mem.setToMemory(31,200);

         mem.setToMemory(1000,1);
         mem.setToMemory(1001,2);
         mem.setToMemory(1002,3);
         mem.setToMemory(1003,4);
         mem.setToMemory(1004,5);
         mem.setToMemory(1005,6);

         mem.setToMemory(200,1);
         mem.setToMemory(201,1);
         mem.setToMemory(202,1);
         mem.setToMemory(203,1);
         mem.setToMemory(204,1);
         mem.setToMemory(205,1);

//         x1.setValue(1);
//         gpr1.setValue(1);
//         gpr3.setValue(65533);


         pc.setValue(6);
         while(pc.getValue()!=11) {
             mar.getFromPC(pc);
             mbr.getFromMem(mar, mem);
             ir.getFromMBR(mbr);
             decoder.decoding(ir);
             decoder.fetching(alu, mem, mar, mbr, x1, x2, x3);
             decoder.executing(alu, pc, mem, mar, mbr, gpr0, gpr1, gpr2, gpr3, fr0, fr1, x1, x2, x3, cc, -1);
             decoder.depositing(alu, pc, mem, mar, mbr, gpr0, gpr1, gpr2, gpr3, fr0, fr1, x1, x2, x3, cc);
             decoder.nextInstruction(pc, alu, cc, gpr0, gpr1, gpr2, gpr3);
         }

//         System.out.println(gpr0.getValue());
//         System.out.println(fr0.getValue());
//         System.out.println(mem.getFromMemory(22));
         System.out.println(mem.getFromMemory(1000));
        System.out.println(mem.getFromMemory(1001));
        System.out.println(mem.getFromMemory(1002));
        System.out.println(mem.getFromMemory(1003));
        System.out.println(mem.getFromMemory(1004));
        System.out.println(mem.getFromMemory(1005));



    }
}
