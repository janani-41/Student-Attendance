import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double a, b; String op;
        System.out.print("Enter first number: "); a = sc.nextDouble();
        System.out.print("Enter operator (+,-,*,/): "); op = sc.next();
        System.out.print("Enter second number: "); b = sc.nextDouble();
        switch (op) {
            case "+" -> System.out.println("Result: " + (a + b));
            case "-" -> System.out.println("Result: " + (a - b));
            case "*" -> System.out.println("Result: " + (a * b));
            case "/" -> System.out.println("Result: " + (b != 0 ? a / b : "Error: divide by zero"));
        }
    }
}
