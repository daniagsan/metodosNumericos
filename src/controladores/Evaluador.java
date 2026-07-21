package controladores;

public class Evaluador {

    public static double evaluar(String expresion, double x) {
        if (expresion == null || expresion.trim().isEmpty()) {
            throw new IllegalArgumentException("Expresion vacia");
        }
        return new Parser(expresion, x).parse();
    }

    public static double derivar(String expresion, double x) {
        double h = 1e-8;
        return (evaluar(expresion, x + h) - evaluar(expresion, x - h)) / (2 * h);
    }

    private static class Parser {
        private final String input;
        private final double x;
        private int pos = -1;
        private int ch;

        public Parser(String input, double x) {
            this.input = input;
            this.x = x;
            nextChar();
        }

        private void nextChar() {
            ch = (++pos < input.length()) ? input.charAt(pos) : -1;
        }

        private boolean eat(int charToEat) {
            while (ch == ' ') nextChar();
            if (ch == charToEat) {
                nextChar();
                return true;
            }
            return false;
        }

        private double parse() {
            double result = parseExpression();
            if (pos < input.length()) {
                throw new IllegalArgumentException("Caracter inesperado: " + (char) ch);
            }
            return result;
        }

        private double parseExpression() {
            double result = parseTerm();
            while (true) {
                if (eat('+')) result += parseTerm();
                else if (eat('-')) result -= parseTerm();
                else return result;
            }
        }

        private double parseTerm() {
            double result = parseFactor();
            while (true) {
                if (eat('*')) result *= parseFactor();
                else if (eat('/')) {
                    double denom = parseFactor();
                    if (denom == 0) throw new ArithmeticException("Division entre cero");
                    result /= denom;
                } else return result;
            }
        }

        private double parseFactor() {
            if (eat('+')) return parseFactor();
            if (eat('-')) return -parseFactor();

            double result;
            int startPos = this.pos;

            if (eat('(')) {
                result = parseExpression();
                eat(')');
            } else if (ch >= '0' && ch <= '9' || ch == '.') {
                StringBuilder sb = new StringBuilder();
                while (ch >= '0' && ch <= '9' || ch == '.') {
                    sb.append((char) ch);
                    nextChar();
                }
                result = Double.parseDouble(sb.toString());
            } else if (ch == 'x' || ch == 'X') {
                nextChar();
                result = this.x;
            } else if (ch >= 'a' && ch <= 'z') {
                StringBuilder sb = new StringBuilder();
                while (ch >= 'a' && ch <= 'z') {
                    sb.append((char) ch);
                    nextChar();
                }
                String func = sb.toString();
                if (eat('(')) {
                    double arg = parseExpression();
                    eat(')');
                    result = applyFunction(func, arg);
                } else if (func.equals("e")) {
                    result = Math.E;
                } else if (func.equals("pi")) {
                    result = Math.PI;
                } else {
                    throw new IllegalArgumentException("Funcion desconocida: " + func);
                }
            } else {
                throw new IllegalArgumentException("Caracter inesperado: " + (char) ch);
            }

            if (eat('^')) result = Math.pow(result, parseFactor());

            return result;
        }

        private double applyFunction(String func, double arg) {
            switch (func) {
                case "sin":   return Math.sin(arg);
                case "cos":   return Math.cos(arg);
                case "tan":   return Math.tan(arg);
                case "sqrt":  return Math.sqrt(arg);
                case "log":   return Math.log10(arg);
                case "ln":    return Math.log(arg);
                case "abs":   return Math.abs(arg);
                case "exp":   return Math.exp(arg);
                case "asin":  return Math.asin(arg);
                case "acos":  return Math.acos(arg);
                case "atan":  return Math.atan(arg);
                default:
                    throw new IllegalArgumentException("Funcion desconocida: " + func);
            }
        }
    }
}
