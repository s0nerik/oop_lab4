package third;

public class MyComplex implements MyNumber<MyComplex> {

    public static final MyComplex ZERO = new MyComplex(0, 0);

    private final double re;
    private final double im;

    public MyComplex(double re, double im) {
        this.re = re;
        this.im = im;
    }

    @Override
    public MyNumber add(MyComplex that) {
        return new MyComplex(re + that.re, im + that.im);
    }

    @Override
    public MyNumber subtract(MyComplex that) {
        return new MyComplex(re - that.re, im - that.im);
    }

    @Override
    public MyNumber multiply(MyComplex that) {
        return new MyComplex(re * that.re - im * that.im, re * that.im + im * that.re);
    }

    @Override
    public MyNumber divide(MyComplex that) throws ArithmeticException {
        double real = (re * that.re + im * that.im) / (Math.pow(that.re, 2d) + Math.pow(that.im, 2d));
        double imaginary = (im * that.re - re * that.im) / (Math.pow(that.re, 2d) + Math.pow(that.im, 2d));
        return new MyComplex(real, imaginary);
    }

    @Override
    public String toString() {
        return re + "+" + im + "*i";
    }
}
