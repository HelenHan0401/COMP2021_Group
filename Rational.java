public class Rational {
    private int numerator;
    private int denominator;

    // Constructor
    public Rational(int numerator, int denominator) {
        setNumerator(numerator);
        setDenominator(denominator);
    }

    // Setter for numerator
    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    // Setter for denominator
    public void setDenominator(int denominator) {
        if (denominator == 0) {
            throw new IllegalArgumentException("Denominator cannot be zero.");
        }
        this.denominator = denominator;
    }

    // Add method
    public Rational add(Rational other) {
        int newNumerator = this.numerator * other.denominator + other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Rational(newNumerator, newDenominator);
    }

    // Subtract method
    public Rational subtract(Rational other) {
        int newNumerator = this.numerator * other.denominator - other.numerator * this.denominator;
        int newDenominator = this.denominator * other.denominator;
        return new Rational(newNumerator, newDenominator);
    }

    // Multiply method
    public Rational multiply(Rational other) {
        int newNumerator = this.numerator * other.numerator;
        int newDenominator = this.denominator * other.denominator;
        return new Rational(newNumerator, newDenominator);
    }

    // Divide method
    public Rational divide(Rational other) {
        if (other.numerator == 0) {
            throw new IllegalArgumentException("Cannot divide by a rational number with a numerator of zero.");
        }
        int newNumerator = this.numerator * other.denominator;
        int newDenominator = this.denominator * other.numerator;
        return new Rational(newNumerator, newDenominator);
    }

    // Simplify method
    public Rational simplify() {
        int gcd = gcd(Math.abs(numerator), Math.abs(denominator));
        int simplifiedNumerator = numerator / gcd;
        int simplifiedDenominator = denominator / gcd;

        // Ensure denominator is positive
        if (simplifiedDenominator < 0) {
            simplifiedNumerator = -simplifiedNumerator;
            simplifiedDenominator = -simplifiedDenominator;
        }

        return new Rational(simplifiedNumerator, simplifiedDenominator);
    }

    // Method to find the greatest common divisor
    private int gcd(int a, int b) {
        while (b != 0) {
            int temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    // toString method
    @Override
    public String toString() {
        // Simplify before converting to string
        Rational simplified = this.simplify();
        return simplified.numerator + "/" + simplified.denominator;
    }

    // Main method for testing
    public static void main(String[] args) {
        Rational r1 = new Rational(12, 30);
        Rational r2 = new Rational(1, 2);

        System.out.println("R1: " + r1);
        System.out.println("R2: " + r2);
        System.out.println("R1 + R2: " + r1.add(r2));
        System.out.println("R1 - R2: " + r1.subtract(r2));
        System.out.println("R1 * R2: " + r1.multiply(r2));
        System.out.println("R1 / R2: " + r1.divide(r2));
        System.out.println("Simplified R1: " + r1.simplify());
    }
}