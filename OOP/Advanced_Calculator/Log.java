import java.util.List;
import java.util.Map;

/**
 * Class Log.
 * represents log function.
 */
public class Log extends BinaryExpression {
    /**
     * log constructor.
     * 
     * @param leftExpression  the base.
     * @param rightExpression the anti-logarithm.
     */
    public Log(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression, "log");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double base = leftExpression.evaluate(assignment);
        double arg = rightExpression.evaluate(assignment);
        if (base <= 0 || base == 1 || arg <= 0) {
            throw new RuntimeException("undefined logarithm");
        }
        return Math.log(arg) / Math.log(base);
    }

    @Override
    public double evaluate() throws Exception {
        double base = leftExpression.evaluate();
        double arg = rightExpression.evaluate();
        if (base <= 0 || base == 1 || arg <= 0) {
            throw new RuntimeException("undefined logarithm");
        }
        return Math.log(arg) / Math.log(base);
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Log(leftExpression.assign(var, expression), rightExpression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        Expression u = rightExpression;
        Expression du = u.differentiate(var);
        Expression lnBase = new Log(new Num(Math.E), leftExpression);
        return new Div(du, new Mult(u, lnBase));
    }

    @Override
    public Expression simplify() {
        Expression base = leftExpression.simplify();
        Expression arg = rightExpression.simplify();

        // replace "e" if used as a string
        if (base instanceof Var && base.toString().equals("e")) {
            base = new Num(Math.E);
        }
        if (arg instanceof Var && arg.toString().equals("e")) {
            arg = new Num(Math.E);
        }

        try {
            if (base instanceof Num && arg instanceof Num) {
                double b = base.evaluate();
                double a = arg.evaluate();
                if (b <= 0 || b == 1 || a <= 0) {
                    throw new RuntimeException("undefined logarithm");
                }
                if (Double.compare(b, a) == 0) {
                    return new Num(1);
                }
                return new Num(Math.log(a) / Math.log(b));
            }
        } catch (Exception e) {}

        if (base.equals(arg)) {
            return new Num(1);
        }

        return new Log(base, arg);
    }

    /**
     * @return string of the log formula.
     */
    public String toString() {
        try {
            if (leftExpression instanceof Num && leftExpression.evaluate() == Math.E) {
                return "ln(" + rightExpression + ")";
            }
        } catch (Exception e) {}
        return "log(" + leftExpression + ", " + rightExpression + ")";
    }
}
