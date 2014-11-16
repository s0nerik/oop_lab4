import third.Frac;
import third.MyComplex;
import third.MyNumber;

public class ComplexTester {

    static void testAPlusBSquare(MyNumber a, MyNumber b) {
        System.out.println("=== Starting testing (a+b)^2=a^2+2ab+b^2 with a = "
                + a + ", b = " + b + " ===");
        MyNumber aPlusB = a.add(b);
        System.out.println("a = " + a);
        System.out.println("b = " + b);
        System.out.println("(a + b) = " + aPlusB);
        System.out.println("(a+b)^2 = " + aPlusB.multiply(aPlusB));
        System.out.println(" = = = ");
        MyNumber curr = a.multiply(a);
        System.out.println("a^2 = " + curr);
        MyNumber wholeRightPart = curr;
        curr = a.multiply(b); // ab
        curr = curr.add(curr); // ab + ab = 2ab
        // A.F.A.I.K., it's impossible to create factor "2" in more elegant way,
        // without even knowing how MyNumber is implemented
        System.out.println("2*a*b = " + curr);
        wholeRightPart = wholeRightPart.add(curr);
        curr = b.multiply(b);
        System.out.println("b^2 = " + curr);
        wholeRightPart = wholeRightPart.add(curr);
        System.out.println("a^2+2ab+b^2 = " + wholeRightPart);
        System.out.println("=== Finishing testing (a+b)^2=a^2+2ab+b^2 with a = "
                + a + ", b = " + b + " ===");
    }

    static void testMayBeDivByZero(MyNumber a, MyNumber b, MyNumber c) {
        System.out.println("=== Starting testing testMayBeDivByZero with a = "
                + a + ", b = " + b + ", c = " + c + " ===");
        MyNumber bPlusC = b.add(c);
        System.out.println("b + c = " + bPlusC);
        try {
            System.out.println("a/(b+c) = " + a.divide(bPlusC) + " (successfully)");
        } catch (ArithmeticException e) {
            System.out.println("An ArithmeticException occured, re-check your formulas!");
        }
        System.out.println("=== Finishing testing testMayBeDivByZero with a = "
                + a + ", b = " + b + ", c = " + c + " ===");
    }

    public static void main(String[] args) {
        Frac f1 = new Frac(1, 3);
        Frac f2 = new Frac(1, 6);
        testAPlusBSquare(f1, f2);
        MyComplex c1 = new MyComplex(1, 3);
        MyComplex c2 = new MyComplex(1, 6);
        testAPlusBSquare(c1, c2);
        Frac f3 = new Frac(-1, 6);
        MyComplex c3 = new MyComplex(-1, 6);
        testMayBeDivByZero(f1, f2, f3);
        testMayBeDivByZero(f3, f1, f2);
        testMayBeDivByZero(c1, c2, c3);
        testMayBeDivByZero(c3, c1, c2);
    }

}
