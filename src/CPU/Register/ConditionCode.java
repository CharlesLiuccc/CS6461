package CPU.Register;

/***
 * This is the class for Condition Code Register
 * CC just has 4 bits in length
 *
 * @author Charles
 */
public class ConditionCode extends Register{
    public ConditionCode(){
        super(4,1,"CC");
    }
}
