import CPU.Register.*;

public class Main {
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
    public static CiscGUI theGui = new CiscGUI();

    public static void main(String[] args) {
        theGui.CreateandShowGUI();

    }

    public static void Hello(){

    }
}
