import java.util.List;
import java.util.Map;

/**
 * Class Log.
 * represents log function.
 */
public class Log extends BinaryExpression {
    /**
     * log constructor.
     * @param leftExpression the base.
     * @param rightExpression the anti-logarithm.
     */
    public Log(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression, "log");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double base = super.leftExpression.evaluate(assignment);
        double antiLogarithm = super.rightExpression.evaluate(assignment);
        if (base <= 0 || base == 1 || antiLogarithm <= 0) {
            throw new RuntimeException("undefined logarithm");
        }
        return Math.log(antiLogarithm) / Math.log(base);
    }

    @Override
    public double evaluate() throws Exception {
        double base = super.leftExpression.evaluate();
        double antiLogarithm = super.rightExpression.evaluate();
        if (base <= 0 || base == 1 || antiLogarithm <= 0) {
            throw new RuntimeException("undefined logarithm");
        }
        return Math.log(antiLogarithm) / Math.log(base);
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Log(super.leftExpression.assign(var, expression), super.rightExpression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        Expression rightDifferentiate = super.rightExpression.differentiate(var);
        Expression denominator = new Mult(rightExpression, new Log(new Num(Math.E),leftExpression));
        return new Div(rightDifferentiate, denominator);
    }

    @Override
    public Expression simplify() {
        Expression base = super.leftExpression.simplify();
        Expression antiLogarithm = super.rightExpression.simplify();
        if (antiLogarithm instanceof Var) {
            try {
                if (antiLogarithm.toString().equals("e")) {
                    antiLogarithm = new Num(Math.E);
                }
            } catch (Exception exception) {
                antiLogarithm = super.rightExpression.simplify();
            }
        }
        if (base instanceof Var) {
            try {
                if (base.toString().equals("e")) {
                    base = new Num(Math.E);
                }
            } catch (Exception exception) {
                base = super.leftExpression.simplify();
            }
        }
        if (base instanceof Num && antiLogarithm instanceof Num) {
            try {
                double baseNumber = base.evaluate();
                double antiLogarithmNumber = antiLogarithm.evaluate();
                if (baseNumber <= 0 || baseNumber == 1 ||antiLogarithmNumber <= 0) {
                    throw new RuntimeException("undefined logarithm");
                }
                if (baseNumber == antiLogarithmNumber) {
                    return new Num(1);
                }
                return new Num(Math.log(antiLogarithmNumber) / Math.log(baseNumber));
            } catch (Exception exception) {
                return new Log(base, antiLogarithm);
            }
        }
        Expression yo = new Log(base, antiLogarithm);
        try {
            if (yo.equals()) {
                return new Num(1);
            }
        } catch (Exception exception) {
            return new Log(base, antiLogarithm);
        }
        return new Log(base, antiLogarithm);
    }

    /**
     * @return string of the log formula.
     */
    public String toString() {
        if (super.leftExpression instanceof Num) {
            try {
                if (super.leftExpression.evaluate() == Math.E) {
                    return "log(e, " + super.rightExpression + ")";
                }
            } catch (Exception exception) {
                return "log(" + super.leftExpression + ", " + super.rightExpression + ")";
            }
        }
        return "log(" + super.leftExpression + ", " + super.rightExpression + ")";
    }
}
