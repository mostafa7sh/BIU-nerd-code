import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Plus extends BinaryExpression{
    public Plus(Expression leftExpression, Expression rightExpression) {
        super(leftExpression, rightExpression, "+");
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        double leftNumber = super.leftExpression.evaluate(assignment);
        double rightNumber = super.rightExpression.evaluate(assignment);
        return leftNumber + rightNumber;
    }

    @Override
    public double evaluate() throws Exception {
        double leftNumber = super.leftExpression.evaluate();
        double rightNumber = super.rightExpression.evaluate();
        return leftNumber + rightNumber;    }

    @Override
    public List<String> getVariables() {
        return super.getVariables();
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return new Plus(super.leftExpression.assign(var, expression), super.rightExpression.assign(var, expression));
    }

    @Override
    public Expression differentiate(String var) {
        return new Plus(super.leftExpression.differentiate(var), super.rightExpression.differentiate(var));
    }

    @Override
    public Expression simplify() {
        Expression leftExpression = super.leftExpression.simplify();
        Expression rightExpression = super.rightExpression.simplify();
        if (leftExpression instanceof Num && rightExpression instanceof Num) {
            try {
                double newForm = rightExpression.evaluate() + leftExpression.evaluate();
                return new Num(newForm);
            } catch (Exception exception) {
                return new Plus(leftExpression, rightExpression);
            }
        } else if (leftExpression instanceof Num) {
            try {
                if (leftExpression.evaluate() == 0) {
                    return rightExpression;
                }
            } catch (Exception exception) {
                return new Plus(leftExpression, rightExpression);
            }
        } else if (rightExpression instanceof Num) {
            try {
                if (rightExpression.evaluate() == 0) {
                    return leftExpression;
                }
            } catch (Exception exception) {
                return new Plus(leftExpression, rightExpression);
            }
        }
        return new Plus(leftExpression, rightExpression);
    }
}
