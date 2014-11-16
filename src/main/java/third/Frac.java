package third;

import com.sun.istack.internal.NotNull;

import java.math.BigInteger;

public class Frac implements MyNumber<Frac>, Comparable<Frac> {

    private final BigInteger nom;
    private final BigInteger denom;

    public Frac(BigInteger nom, BigInteger denom) throws ArithmeticException {
        if (denom.equals(BigInteger.ZERO)) {
            throw new ArithmeticException("/ by 0");
        }

        BigInteger gcd = nom.gcd(denom);

        this.nom = nom.divide(gcd);
        this.denom = denom.divide(gcd);
    }

    public Frac(int nom, int denom) {
        this(BigInteger.valueOf(nom), BigInteger.valueOf(denom));
    }

    @Override
    public MyNumber add(Frac that) {

        BigInteger productOne = nom.multiply(that.denom);
        BigInteger productTwo = denom.multiply(that.nom);
        BigInteger newNom = productOne.add(productTwo);

        BigInteger newDenom = denom.multiply(that.denom);

        return new Frac(newNom, newDenom);
    }

    @Override
    public MyNumber subtract(Frac that) {

        BigInteger productOne = nom.multiply(that.denom);
        BigInteger productTwo = denom.multiply(that.nom);
        BigInteger newNom = productOne.subtract(productTwo);

        BigInteger newDenom = denom.multiply(that.denom);

        return new Frac(newNom, newDenom);
    }

    @Override
    public MyNumber multiply(Frac that) {

        BigInteger newNom = nom.multiply(that.nom);

        BigInteger newDenom = denom.multiply(that.denom);

        return new Frac(newNom, newDenom);
    }

    @Override
    public MyNumber divide(Frac that) throws ArithmeticException {

        BigInteger newNom = nom.multiply(that.denom);

        BigInteger newDenom = denom.multiply(that.nom);

        return new Frac(newNom, newDenom);
    }

    @Override
    public int compareTo(@NotNull Frac that) {

        int leftNomSign = nom.signum();
        int leftDenomSign = denom.signum();
        boolean leftPositive = leftNomSign * leftDenomSign >= 0;

        int rightNomSign = that.nom.signum();
        int rightDenomSign = that.denom.signum();
        boolean rightPositive = rightNomSign * rightDenomSign >= 0;

        if (!leftPositive && rightPositive) {
            return -1;
        }

        if (leftPositive && !rightPositive) {
            return 1;
        }

        BigInteger leftNom = nom.multiply(that.denom).abs();
        BigInteger rightNom = that.nom.multiply(denom).abs();

        if (leftPositive && rightPositive) {
            return leftNom.compareTo(rightNom);
        }

        if (!leftPositive && !rightPositive) {
            return -leftNom.compareTo(rightNom);
        }

        return 0;
    }

    @Override
    public String toString() {
        return nom + "/" + denom;
    }
}
