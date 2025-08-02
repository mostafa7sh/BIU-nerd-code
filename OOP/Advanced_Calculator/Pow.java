import java.util.List;
import java.util.Map;

public class Pow extends BinaryExpression{
    public Pow(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression, "^");
    }
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double leftNumber = super.leftExpression.evaluate(assignment);
        double rightNumber = super.rightExpression.evaluate(assignment);
        if (Double.toString(Math.pow(leftNumber, rightNumber)).equals("NaN")) {
            throw new RuntimeException("undefined");
        }
        return Math.pow(leftNumber, rightNumber);
    }

    @Override
    public double evaluate() throws Exception {
        double leftNumber = super.leftExpression.evaluate();
        double rightNumber = super.rightExpression.evaluate();
        if (Double.toString(Math.pow(leftNumber, rightNumber)).equals("NaN")) {
            throw new RuntimeException("undefined");
        }
        return Math.pow(leftNumber, rightNumber);
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Pow(super.leftExpression.assign(var, expression), super.rightExpression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        if (super.rightExpression.simplify() instanceof Num) {
            Expression firstStep = new Mult(super.rightExpression,
                    new Pow(super.leftExpression, new Minus(super.rightExpression, new Num(1))));
            return new Mult(firstStep, leftExpression.differentiate(var));
        }
        Expression powDerivative = super.rightExpression.differentiate(var);
        Expression lnTheBase = new Log(new Num(Math.E), super.leftExpression);
        Expression toMultInLastStep = new Mult(powDerivative, lnTheBase);
        return new Mult(new Pow(super.leftExpression, super.rightExpression), toMultInLastStep);
    }

    @Override
    public Expression simplify() {
        Expression leftExpression = super.leftExpression.simplify();
        Expression rightExpression = super.rightExpression.simplify();
        if (leftExpression instanceof Num && rightExpression instanceof Num ) {
            try {
                double leftNumber = leftExpression.evaluate();
                double rightNumber = rightExpression.evaluate();
                String error = Double.toString(Math.pow(leftNumber, rightNumber));
                if (error.equals("NaN")) {
                    throw new RuntimeException("undefined");
                }
                return new Num(Math.pow(leftNumber, rightNumber));
            } catch (Exception exception) {
                throw new RuntimeException("undefined");
            }
        }
        if (rightExpression instanceof Num) {
            try {
                if (rightExpression.evaluate() == 1) {
                    return leftExpression;
                } else if (rightExpression.evaluate() == 0) {
                    return new Num(1);
                }
            } catch (Exception exception) {
                return new Pow(leftExpression, rightExpression);
            }
        }
        return new Pow(leftExpression, rightExpression);
    }

    @Override
    public String toString() {
        return "(" + super.leftExpression + super.operator + super.rightExpression + ")";
    }
}
