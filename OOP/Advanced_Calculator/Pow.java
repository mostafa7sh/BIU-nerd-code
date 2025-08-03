import java.util.List;
import java.util.Map;

public class Pow extends BinaryExpression {
    public Pow(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression, "^");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double base = leftExpression.evaluate(assignment);
        double exponent = rightExpression.evaluate(assignment);
        double result = Math.pow(base, exponent);
        if (Double.isNaN(result)) {
            throw new RuntimeException("undefined");
        }
        return result;
    }

    @Override
    public double evaluate() throws Exception {
        double base = leftExpression.evaluate();
        double exponent = rightExpression.evaluate();
        double result = Math.pow(base, exponent);
        if (Double.isNaN(result)) {
            throw new RuntimeException("undefined");
        }
        return result;
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Pow(leftExpression.assign(var, expression), rightExpression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        // if exponent is a constant
        if (rightExpression instanceof Num) {
            Expression n = rightExpression;
            Expression newExponent = new Minus(n, new Num(1));
            Expression baseToPower = new Pow(leftExpression, newExponent);
            return new Mult(new Mult(n, baseToPower), leftExpression.differentiate(var));
        }

        // general case: d(u^v) = u^v * (v' * ln(u) + v * u'/u)
        Expression u = leftExpression;
        Expression v = rightExpression;
        Expression lnU = new Log(new Num(Math.E), u);
        Expression vPrime = v.differentiate(var);
        Expression uPrime = u.differentiate(var);

        Expression firstTerm = new Mult(vPrime, lnU);
        Expression secondTerm = new Div(new Mult(v, uPrime), u);
        Expression combined = new Plus(firstTerm, secondTerm);

        return new Mult(new Pow(u, v), combined);
    }

    @Override
    public Expression simplify() {
        Expression left = leftExpression.simplify();
        Expression right = rightExpression.simplify();

        try {
            if (left instanceof Num && right instanceof Num) {
                double base = left.evaluate();
                double exp = right.evaluate();
                double result = Math.pow(base, exp);
                if (Double.isNaN(result)) {
                    throw new RuntimeException("undefined");
                }
                return new Num(result);
            }

            // x^1 → x
            if (right instanceof Num && right.evaluate() == 1) {
                return left;
            }

            // x^0 → 1
            if (right instanceof Num && right.evaluate() == 0) {
                return new Num(1);
            }

            // 1^x → 1
            if (left instanceof Num && left.evaluate() == 1) {
                return new Num(1);
            }

            // 0^x → 0 (if x > 0)
            if (left instanceof Num && left.evaluate() == 0 && right instanceof Num && right.evaluate() > 0) {
                return new Num(0);
            }
        } catch (Exception e) {}

        return new Pow(left, right);
    }

    @Override
    public String toString() {
        return "(" + super.leftExpression + super.operator + super.rightExpression + ")";
    }
}
