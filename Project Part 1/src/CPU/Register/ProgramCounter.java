package CPU.Register;

/***
 * This is the class for Program Counter
 * PC just has 12 bits in length
 * PC has 1 more member function: nextProgram()
 *
 * @author Charles
 */

public class ProgramCounter extends Register{
    public ProgramCounter(){
        super(12,0,"PC");
    }

    public void nextProgram(){
        this.setValue(this.getValue()+1);
    }
}
