import java.util.List;
import java.util.Map;

/**
 * Class Div.
 * represents a division between two expressions.
 */
public class Div extends BinaryExpression {
    /**
     * div constructor.
     * @param leftExpression the numerator.
     * @param rightExpression the denominator.
     */
    public Div(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression, "/");
    }
    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double leftNumber = super.leftExpression.evaluate(assignment);
        double rightNumber = super.rightExpression.evaluate(assignment);
        if (rightNumber == 0) {
            throw new RuntimeException("you cant divide a number with zero");
        }
        return leftNumber / rightNumber;
    }

    @Override
    public double evaluate() throws Exception {
        double leftNumber = super.leftExpression.evaluate();
        double rightNumber = super.rightExpression.evaluate();
        if (rightNumber == 0) {
            throw new RuntimeException("you cant divide a number with zero");
        }
        return leftNumber / rightNumber;
    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Div(super.leftExpression.assign(var, expression), super.rightExpression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        // f`
        Expression leftDifferentiate = super.leftExpression.differentiate(var);
        // g`
        Expression rightDifferentiate = super.rightExpression.differentiate(var);
        // f` * g
        Expression firstPart = new Mult(leftDifferentiate, super.rightExpression);
        // g` * f
        Expression secondPart = new Mult(super.leftExpression, rightDifferentiate);
        // (f` * g) - (g` - f)
        Expression numerator = new Minus(firstPart, secondPart);
        // g^2
        Expression denominator = new Pow(super.rightExpression, new Num(2));
        // ((f` * g) - (g` - f)) / g^2
        return new Div(numerator, denominator);
    }

    @Override
    public Expression simplify() {
        Expression leftExpression = super.leftExpression.simplify();
        Expression rightExpression = super.rightExpression.simplify();
        // checking the two expression are numbers.
        if (leftExpression instanceof Num && rightExpression instanceof Num) {
            try {
                if (rightExpression.evaluate() == 0) {
                    throw new RuntimeException("you cant divide a number with zero");
                }
                double numerator = leftExpression.evaluate();
                double denominator = rightExpression.evaluate();
                return new Num(numerator / denominator);
            } catch (Exception exception) {
                return new Div(leftExpression, rightExpression);
            }
            // checking if the denominator was a number.
        } else if (rightExpression instanceof Num) {
            try {
                if (rightExpression.evaluate() == 1) {
                    return leftExpression;
                } else if (rightExpression.evaluate() == 0) {
                    throw new RuntimeException("you cant divide a number with zero");
                }
            } catch (Exception exception) {
                return new Div(leftExpression, rightExpression);
            }
            // checking if the numerator was a nunmber.
        } else if (leftExpression instanceof Num) {
            try {
                if (leftExpression.evaluate() == 0) {
                    return new Num(0);
                }
            } catch (Exception exception) {
                return new Div(leftExpression, rightExpression);
            }
        }
        try {
            Expression yo = new Div(leftExpression, rightExpression);
            if (yo.equals()) {
                return new Num(1);
            }
        } catch (Exception exception) {
            return new Div(leftExpression, rightExpression);
        }
        return new Div(leftExpression, rightExpression);
    }
}
