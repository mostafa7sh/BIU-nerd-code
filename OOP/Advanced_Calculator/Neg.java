import java.util.List;
import java.util.Map;

/**
 * Class Neg.
 * represents negative of an expression.
 */
public class Neg extends UnaryExpression {
    /**
     * neg constructor.
     * 
     * @param expression the expression.
     */
    public Neg(Expression expression) {
        super(expression, "-");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return -expression.evaluate(assignment);
    }

    @Override
    public double evaluate() throws Exception {
        return -expression.evaluate();
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Neg(super.expression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        return new Neg(expression.differentiate(var));
    }

    @Override
    public Expression simplify() {
        Expression simplified = expression.simplify();

        try {
            if (simplified instanceof Num) {
                double val = simplified.evaluate();
                return new Num(-val);
            }

            // --x => x
            if (simplified instanceof Neg) {
                return ((Neg) simplified).expression;
            }

        } catch (Exception e) {}

        return new Neg(simplified);
    }

    @Override
    public String toString() {
        return "(" + super.operator + super.expression + ")";
    }
}
