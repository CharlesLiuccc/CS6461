package CPU.Register;

/***
 * This is the class for Instruction Register
 * IR just has 16 bits in length
 * IR has 1 more member function: getFromMBR(MBR)
 *
 * @author Charles
 */

public class InstructionRegister extends Register{
    public InstructionRegister(){
        super(16,0,"IR");
    }

    public void getFromMBR(MemoryBufferRegister MBR){
        this.setValue(MBR.getValue());
    }
}
