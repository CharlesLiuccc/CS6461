import CPU.ALU;
import CPU.Decoder;
import CPU.Register.*;
import Memory.Memory;

public class InsPipe {
    private MemoryBufferRegister mbr;
    private MemoryAddressRegister mar;
    private InstructionRegister ir;
    private ConditionCode cc;
    private Decoder decoder;
    private ALU alu;

    public InsPipe() {
        mbr = new MemoryBufferRegister();
        mar = new MemoryAddressRegister();
        ir = new InstructionRegister();
        decoder = new Decoder();
        alu = new ALU();
    }

    public void set_mar(ProgramCounter pc){
        mar.getFromPC(pc);
    }

    public MemoryAddressRegister get_mar(){
        return mar;
    }

    public void set_mbr(MemoryAddressRegister mar, Memory mem)
    {
        mbr.getFromMem(mar, mem);
    }

    public MemoryBufferRegister get_mbr() { return mbr; }

    public void set_ir(MemoryBufferRegister mbr)
    {
        ir.getFromMBR(mbr);
    }

    public InstructionRegister get_ir() { return ir; }

    public void set_decoder(InstructionRegister ir)
    {
        decoder.decoding(ir);
    }

    public int get_decoder_opcode() {return decoder.getOpcode(); }

    public int get_decoder_out_value() { return decoder.getOut_value(); }

    public void decoder_fetch(Memory mem, IndexRegister x1, IndexRegister x2, IndexRegister x3){
        decoder.fetching(alu, mem, mar, mbr, x1, x2, x3);
    }

    public void decoder_execute(ProgramCounter pc, Memory mem, GeneralPurposeRegister gpr0, GeneralPurposeRegister gpr1, GeneralPurposeRegister gpr2, GeneralPurposeRegister gpr3, IndexRegister x1, IndexRegister x2, IndexRegister x3, int in_value){
        decoder.executing(alu, pc, mem, mbr, gpr0, gpr1, gpr2, gpr3, x1, x2, x3, cc, in_value);
    }

    public void decoder_depositing(ProgramCounter pc, Memory mem, GeneralPurposeRegister gpr0, GeneralPurposeRegister gpr1, GeneralPurposeRegister gpr2, GeneralPurposeRegister gpr3, IndexRegister x1, IndexRegister x2, IndexRegister x3 ){
        decoder.depositing(alu, pc, mem, mar, mbr, gpr0, gpr1, gpr2, gpr3, x1, x2, x3, cc);
    }

    public void decoder_nextInstruction(ProgramCounter pc, GeneralPurposeRegister gpr0, GeneralPurposeRegister gpr1, GeneralPurposeRegister gpr2, GeneralPurposeRegister gpr3){
        decoder.nextInstruction(pc, alu, cc, gpr0, gpr1, gpr2, gpr3);
    }
}
