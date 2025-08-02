import java.util.List;
import java.util.Map;

/**
 * Class Cos.
 * represents cos function.
 */
public class Cos extends UnaryExpression {
    /**
     * cos constructor.
     * @param expression the expression inside the cos.
     */
    public Cos(Expression expression) {
        super(expression, "cos");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return Math.cos(super.expression.evaluate(assignment));
    }

    @Override
    public double evaluate() throws Exception {
        return Math.cos(super.expression.evaluate());
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Cos(super.expression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        Expression internalDerivative = super.expression.differentiate(var);
        Expression toSin = new Sin(super.expression);
        Expression toMinus = new Neg(toSin);
        return new Mult(toMinus, internalDerivative);
    }

    @Override
    public Expression simplify() {
        Expression single = super.expression.simplify();
        if (single instanceof Num) {
            try {
                return new Num(Math.cos(Math.toRadians(single.evaluate())));
            } catch (Exception exception) {
                return new Cos(single);
            }
        }
        return new Cos(single);
    }
}
