import CPU.ALU;
import CPU.Decoder;
import CPU.Register.*;
import Memory.Memory;

public class Main {
    //initialize all the component in CPU
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
    public static Decoder decoder = new Decoder();
    public static ALU alu = new ALU();
    //initialize GUI
    public static CiscGUI theGui = new CiscGUI();
    //initialize Memory
    public static Memory mem = new Memory();
    //initialize Flag
    public static boolean SingleStep = false;
    public static boolean HALT = false;

    public static void main(String[] args) {
        theGui.CreateandShowGUI();

    }

    /***
     * This function will be bind to SS button
     * When the function is called, it will let cpu do one instruction
     *
     * @author Charles
     */
    public static void singleStep(){
        //instruction fetch
        mar.getFromPC(pc);
        mbr.getFromMem(mar,mem);
        ir.getFromMBR(mbr);
        //instruction decode & operand fetch
        decoder.decoding(HALT,alu,mem,ir,mar,mbr,x1,x2,x3);

        //when decoder get the HALT instruction, it won't continue to run
        if(!HALT) {
            //Execute
            decoder.executing(alu, mar, mbr, gpr0, gpr1, gpr2, gpr3, x1, x2, x3);
            //Result store
            decoder.depositing(alu, mem, mar, mbr, gpr0, gpr1, gpr2, gpr3, x1, x2, x3);
            //Next instruction
            pc.nextProgram();
        }

    }

    /***
     * This function will be bind to load button and store button
     * When the function is called, it will let cpu do the instruction input by user
     *
     * The Gui need to judge whether instruction is legal
     *
     * @author Charles
     */
    public static void loadAndStore(int inst){
        HALT=false;
        ir.setValue(inst);
        decoder.decoding(HALT,alu,mem,ir,mar,mbr,x1,x2,x3);
        decoder.executing(alu,mar,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3);
        decoder.depositing(alu,mem,mar,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3);
    }

}
