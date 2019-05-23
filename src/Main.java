import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Main {
    private static final String[] OPERATORS = new String[]{" ", "+", "-", "*", "/", "(", ")", "^"};

    private static final String TEMPLATE = "%s1%s2%s3%s4%s5%s6%s7%s8%s9%s";

    public static void main(String[] args) {

        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");
        final int operatorsInTemplateCount = occurrences(TEMPLATE, "%s");
        System.out.println("operatorsInTemplateCount = " + operatorsInTemplateCount);
        final int operatorsCount = OPERATORS.length;
        System.out.println("operatorsCount = " + operatorsCount);

        List<Integer> operatorsForIterationIndexes = new ArrayList<>();
        String[] operatorsForIteration = new String[operatorsInTemplateCount];

        final long iterations = (long) Math.pow(operatorsCount, operatorsInTemplateCount);
        System.out.println("iterations = " + iterations);

        TreeMap<Long, String> solutions = new TreeMap<>();

        long iteration = 0;
        do {
            operatorsForIterationIndexes.clear();
            long iterationOffset = iteration;
            for (int i = 0; i < operatorsInTemplateCount; i++) {
                int operatorIndex = (int) iterationOffset % operatorsCount;
                iterationOffset = iterationOffset / operatorsCount;
                operatorsForIterationIndexes.add(operatorIndex);
                operatorsForIteration[i] = OPERATORS[operatorIndex];
            }
//            System.out.println("operatorsForIterationIndexes = " + operatorsForIterationIndexes);

            String expression = String.format(TEMPLATE, operatorsForIteration).replace(" ", "");
//            System.out.println("expression " + expression);
            try {
                Object result = engine.eval(expression);
                if (result instanceof Integer || result instanceof Long) {
                    long resultValue = result instanceof Integer ? (Integer) result : (Long) result;

                    if (resultValue >= 0 && resultValue <= 11111) {
//                        System.out.println("Solution " + result + " = " + expression);
                        if (resultValue == 10580) {
                            System.out.println("Solution " + resultValue + " = " + expression);
                        } else if (resultValue == 10958) {
                            System.out.println("Solution " + resultValue + " = " + expression);
                        }

                        solutions.put(resultValue, expression);
                    }
                }
            } catch (ScriptException se) {
                // NO OP
            }
            iteration++;
        } while (iteration < iterations);

        System.out.println(solutions);
    }

    private static int occurrences(String template, String sequence) {
        int result = 0;
        while (true) {
            if (template.indexOf(sequence) != -1) {
                result++;
                template = template.replaceFirst(sequence, "");
            } else {
                return result;
            }
        }
    }
}
