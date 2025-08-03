import java.util.List;
import java.util.Map;

/**
 * Class Cos.
 * represents cos function.
 */
public class Cos extends UnaryExpression {
    /**
     * cos constructor.
     * 
     * @param expression the expression inside the cos.
     */
    public Cos(Expression expression) {
        super(expression, "cos");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return Math.cos(expression.evaluate(assignment));
    }

    @Override
    public double evaluate() throws Exception {
        return Math.cos(expression.evaluate());
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Cos(expression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        // d/dx[cos(f(x))] = -sin(f(x)) * f'(x)
        Expression fPrime = expression.differentiate(var);
        return new Mult(new Neg(new Sin(expression)), fPrime);
    }

    @Override
    public Expression simplify() {
        Expression single = expression.simplify();
        if (single instanceof Num) {
            try {
                return new Num(Math.cos(Math.toRadians(single.evaluate())));
            } catch (Exception exception) {}
        }
        return new Cos(single);
    }
}
