import java.util.List;
import java.util.Map;

/**
 * Interface Expression.
 * give me collection of joint methods that every operation must have.
 */
public interface Expression {
    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result.
     * If the expression contains a variable which is not in the assignment,
     * an exception is thrown.
     * @param assignment the thing we want to put inside the expression.
     * @return a number (if there were no exceptions).
     * @throws Exception if we cant provide a number result.
     */
    double evaluate(Map<String, Double> assignment) throws Exception;

    /**
     * Evaluate the expression using the variable values provided in the assignment, and return the result.
     * If the expression contains a variable which is not in the assignment,
     * an exception is thrown. (the only difference that we don't receive anything).
     * @return a number (if there were no exceptions).
     * @throws Exception if we cant provide a number result.
     */
    double evaluate() throws Exception;

    /**
     * @return a list of all the variables in the expression.
     */
    List<String> getVariables();

    /**
     * @return the expression as string.
     */
    String toString();

    /**
     * here we replace a full expression with another one.
     * @param var the variable that we want to change.
     * @param expression the new expression we want to replace with.
     * @return a new expression after the modification.
     */
    Expression assign(String var, Expression expression);

    /**
     * simply we derive the expression.
     * @param var the variable in the expression.
     * @return the expression after the derivation.
     */
    Expression differentiate(String var);

    /**
     * simply simplify the expression form repetitive or unnecessary expressions.
     * @return a simplified expression.
     */
    Expression simplify();

    /**
     * check if two expression are equals.
     * @return true if two expressions are equal, false otherwise.
     */
    boolean equals();
}