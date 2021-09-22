package CPU.Register;

/***
 * This is the class for Memory Address Register
 * MAR just has 12 bits in length
 * MAR has 1 more member function: getFromPC(PC)
 *
 * @author Charles
 */

public class MemoryAddressRegister extends Register{
    public MemoryAddressRegister(){
        super(12,0,"MAR");
    }

    public void getFromPC(ProgramCounter PC){
        this.setValue(PC.getValue());
    }

}
