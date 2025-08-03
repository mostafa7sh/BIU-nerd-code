import java.util.List;
import java.util.Map;

/**
 * Class BaseExpression.
 * represents almost everything in the project.
 */
public class BaseExpression implements Expression {
    /**
     * the operator.
     */
    protected String operator;

    /**
     * expression constructor.
     * 
     * @param operator an operator.
     */
    public BaseExpression(String operator) {
        this.operator = operator;
    }

    @Override
    public double evaluate(Map<String, Double> assignment) throws Exception {
        return 0;
    }

    @Override
    public double evaluate() throws Exception {
        return 0;
    }

    @Override
    public List<String> getVariables() {
        return null;
    }

    @Override
    public Expression assign(String var, Expression expression) {
        return null;
    }

    @Override
    public Expression differentiate(String var) {
        return null;
    }

    @Override
    public Expression simplify() {
        return null;
    }

    @Override
    public boolean equals() {
        return true;
    }
}
