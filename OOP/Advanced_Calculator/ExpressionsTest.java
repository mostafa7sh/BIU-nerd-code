import java.util.Map;
import java.util.TreeMap;

/**
 * Class ExpressionsTest.
 * a simple test to my great code.
 */
public class ExpressionsTest {
    /**
     * your test sir.
     * @param args nothing for now.
     * @throws Exception throws an error, if occurs.
     */
    public static void main(String[] args) throws Exception {
        Expression twoX = new Mult(new Num(2), new Var("x"));
        Expression ePowX = new Pow(new Var("e"), new Var("x"));
        Expression sin4Y = new Sin(new Mult(new Num(4), new Var("y")));
        Expression toSubmit = new Plus(twoX,new Plus(sin4Y, ePowX));
        System.out.println(toSubmit);
        Map<String, Double> assignment = new TreeMap<String, Double>();
        assignment.put("x", 2.0);
        assignment.put("y", 0.25);
        assignment.put("e", 2.71);
        double value = toSubmit.evaluate(assignment);
        System.out.println(value);
        Expression toSubmitDerivative = toSubmit.differentiate("x");
        System.out.println(toSubmitDerivative);
        Expression simplified = toSubmitDerivative.simplify();
        System.out.println(simplified);
    }
}