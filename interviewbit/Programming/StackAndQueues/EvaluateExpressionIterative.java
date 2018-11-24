package interviewbit.Programming.StackAndQueues

import java.util.*;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toSet;

// Difficulty: trivial. Just implemented an iterative version for completeness. Interviewbit is meant for placing
//             you in interview conditions, and I would realistically not have risked using Scala/functional style
//             in a real interview (takes me more time to decide the nicest way to write things functionally than
//             iteratively).

// https://www.interviewbit.com/problems/evaluate-expression/
public class EvaluateExpressionIterative {
    public static void main(String[] args) {
        System.out.println(evalRPN(Arrays.asList("2", "1", "+", "3", "*"))); // 9
        System.out.println(evalRPN(Arrays.asList("4", "13", "5", "/", "+"))); // 6
    }

    private static final Set<Character> OPERATORS = Stream.of('+', '-', '*', '/').collect(toSet());

    public static int evalRPN(List<String> expr) {
        Deque<String> stack = new ArrayDeque<>();

        int successiveInts = 0;
        int i = expr.size() - 1;
        while (i >= 0 || stack.size() > 1) {
            if (successiveInts == 2) {
                // a bit of a shame to have to parse every time but the alternative is to create one object
                // per token to store the parsed result. Not sure what's faster in practice.
                int left = Integer.parseInt(stack.pop());
                int right = Integer.parseInt(stack.pop());
                String op = stack.pop();
                String next = stack.peek();

                stack.push(String.valueOf(eval(left, op, right)));

                successiveInts = 1;
                if (next != null && !isOperator(next)) successiveInts++;
            } else {
                String token = expr.get(i);
                stack.push(token);
                successiveInts = isOperator(token) ? 0 : successiveInts + 1;
                i--;
            }
        }

        return Integer.parseInt(stack.pop());
    }

    private static int eval(int left, String op, int right) {
        switch (op.charAt(0)) {
            case '+': return left + right;
            case '-': return left - right;
            case '*': return left * right;
            case '/': return left / right;
            default : throw new IllegalArgumentException("Unknown operator: " + op);
        }
    }

    private static boolean isOperator(String s) {
        return s.length() == 1 && OPERATORS.contains(s.charAt(0));
    }
}
