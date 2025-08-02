import java.util.List;
import java.util.Map;

/**
 * Class Neg.
 * represents negative of an expression.
 */
public class Neg extends UnaryExpression {
    /**
     * neg constructor.
     * @param expression the expression.
     */
    public Neg(Expression expression) {
        super(expression, "-");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return -super.expression.evaluate(assignment);
    }

    @Override
    public double evaluate() throws Exception {
        return -super.expression.evaluate();
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
        return new Neg(super.expression.differentiate(var));
    }

    @Override
    public Expression simplify() {
        Expression single = super.expression.simplify();
        if (single instanceof Num) {
            try {
                if (single.evaluate() == 0) {
                    return new Num(0);
                } else if (single.evaluate() < 0) {
                    return new Num(-single.evaluate());
                }
            } catch (Exception exception) {
                return new Neg(single);
            }
        } else if (single instanceof Neg) {
            return new Neg(single);
        }
        return new Neg(single);
    }
    @Override
    public String toString() {
        return "(" + super.operator + super.expression + ")";
    }
}
