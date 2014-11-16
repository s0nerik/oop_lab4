package third;

public interface MyNumber<MyNumberImpl> {
    MyNumber add(MyNumberImpl that);
    MyNumber subtract(MyNumberImpl that);
    MyNumber multiply(MyNumberImpl that);
    MyNumber divide(MyNumberImpl that) throws ArithmeticException;
}