import CPU.ALU;
import CPU.Decoder;
import CPU.Register.*;
import Memory.Memory;



public class Main {
    public static ProgramCounter pc = new ProgramCounter();
    public static MemoryBufferRegister mbr = new MemoryBufferRegister();
    public static MachineFaultRegister mfr = new MachineFaultRegister(0);
    public static InstructionRegister ir = new InstructionRegister();
    public static MemoryAddressRegister mar = new MemoryAddressRegister();
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
    public static Decoder decoder = new Decoder();
    public static ALU alu = new ALU();
    //Instantiate our instructions to be used in the pipeline
    public static InsPipe [] ins = new InsPipe[3];
    public static boolean [] exStage_bool = new boolean[3];
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
        ins[0] = new InsPipe();
        ins[1] = new InsPipe();
        ins[2] = new InsPipe();
        exStage_bool[0] = true;
        exStage_bool[1] = false;
        exStage_bool[2] = false;
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
        if(exStage_bool[0] || exStage_bool[1] || exStage_bool[2]) { //If we need to execute any of the pipeline

            if(exStage_bool[2]) {
                ExecuteStage3(ins[2]);
            }

            if(exStage_bool[1]) {
                ExecuteStage2(ins[1]);
            }

            if(exStage_bool[0])
            {
                ExecuteStage1(ins[0]);
            }

            if(exStage_bool[1]) { exStage_bool[2] = true;} //If we executed stage 2, we will need to execute stage 3 next
            else { exStage_bool[2] = false; }

            if(exStage_bool[0]) { exStage_bool[1] = true; } //If we executed stage 1, we will need to execute stage 2 next
            else {exStage_bool[1] = false; }


            ins[2] = ins[1]; //Move each instruction down the pipeline to be executed
            ins[1] = ins[0];
            ins[0] = new InsPipe();
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
        decoder.executing(alu,pc,mem,mar,mbr,gpr0,gpr1,gpr2,gpr3,fr0,fr1,x1,x2,x3,cc, in_value);
        decoder.depositing(alu,pc,mem,mar,mbr,gpr0,gpr1,gpr2,gpr3,fr0,fr1,x1,x2,x3,cc);
        //decoder.nextInstruction(pc);
    }

    public static void ExecuteStage1(InsPipe ins) {
        ins.set_mar(pc);
        ins.set_mbr(ins.get_mar(), mem);
        ins.set_ir(ins.get_mbr());
    }

    public static void ExecuteStage2(InsPipe ins) {
        ins.set_decoder(ins.get_ir());
        if (ins.get_decoder_opcode() == 0){
            exStage_bool[0] = false;
        }
    }

    public static void ExecuteStage3(InsPipe ins) {
        if (ins.get_decoder_opcode() == 49){
            in_value = theGui.In_Instruction();
        }

        ins.decoder_fetch(mem, x1, x2, x3);
        ins.decoder_execute(pc, mem, gpr0, gpr1, gpr2, gpr3,fr0,fr1 ,x1, x2,x3, in_value);
        ins.decoder_depositing(pc, mem, gpr0, gpr1, gpr2, gpr3,fr0,fr1,x1, x2, x3);
        ins.decoder_nextInstruction(pc, gpr0, gpr1, gpr2, gpr3);

        if (ins.get_decoder_opcode() == 50){
            out_value = out_value.concat(ins.get_decoder_out_value() + "\n");
        }
    }
}
