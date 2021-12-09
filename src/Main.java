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
    public static int in_value = -1;
    public static String out_value = "Output Console\n";



    public static void main(String[] args) {
        theGui.CreateandShowGUI();

    }

    public static void setHALT(boolean HALT) {
        Main.HALT = HALT;
    }

    /***
     * This function will be bind to SS button
     * When the function is called, it will let cpu do one instruction
     *
     * @author Charles
     */
    public static void singleStep(){
        if(!HALT) {
            //instruction fetch
            mar.getFromPC(pc);
            System.out.println("PC:"+pc.getValue());
            mbr.getFromMem(mar, mem);
            ir.getFromMBR(mbr);
            //instruction decode & operand fetch
            decoder.decoding(ir);
            System.out.println("opcode:"+decoder.getOpcode());
            //when decoder get the HALT instruction, it won't continue to run
            if (decoder.getOpcode()==0) {
                Main.setHALT(true);
            }
            if(decoder.getOpcode() == 49){
                in_value = theGui.In_Instruction();
            }
            if(!HALT) {
                //Locate and fetch the operand data
                decoder.fetching(alu, mem, mar, mbr, x1, x2, x3);
                //Execute
                decoder.executing(alu, pc, mem, mbr, gpr0, gpr1, gpr2, gpr3, x1, x2, x3, cc, in_value);
                //Result store
                decoder.depositing(alu, pc, mem, mar, mbr, gpr0, gpr1, gpr2, gpr3, x1, x2, x3, cc);
                //Next instruction
                decoder.nextInstruction(pc,alu,cc,gpr0,gpr1,gpr2,gpr3);
                //pc.nextProgram();
            }
            if(decoder.getOpcode() == 50){
                out_value = out_value.concat(decoder.getOut_value() + "\n");
            }

        }
        System.out.println("\n");

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
        setHALT(false);
        ir.setValue(inst);
        decoder.decoding(ir);
        decoder.fetching(alu,mem,mar,mbr,x1,x2,x3);
        decoder.executing(alu,pc,mem,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3,cc, in_value);
        decoder.depositing(alu,pc,mem,mar,mbr,gpr0,gpr1,gpr2,gpr3,x1,x2,x3,cc);
        //decoder.nextInstruction(pc);
    }
}
