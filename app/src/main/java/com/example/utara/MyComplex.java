//package com.example.utara;
//
///*************************************************************************
// *  Compilation:  javac Complex.java
// *  Execution:    java Complex
// *
// *  Data type for complex numbers.
// *
// *  The data type is "immutable" so once you create and initialize
// *  a Complex object, you cannot change it. The "final" keyword
// *  when declaring re and im enforces this rule, making it a
// *  compile-time error to change the .re or .im fields after
// *  they've been initialized.
// *
// *  % java Complex
// *  a            = 5.0 + 6.0i
// *  b            = -3.0 + 4.0i
// *  Re(a)        = 5.0
// *  Im(a)        = 6.0
// *  b + a        = 2.0 + 10.0i
// *  a - b        = 8.0 + 2.0i
// *  a * b        = -39.0 + 2.0i
// *  b * a        = -39.0 + 2.0i
// *  a / b        = 0.36 - 1.52i
// *  (a / b) * b  = 5.0 + 6.0i
// *  conj(a)      = 5.0 - 6.0i
// *  |a|          = 7.810249675906654
// *  tan(a)       = -6.685231390246571E-6 + 1.0000103108981198i
// *
// *************************************************************************/
//
//public class MyComplex {
//    private final double re;   // the real part
//    private final double im;   // the imaginary part
//
//    // create a new object with the given real and imaginary parts
//    public MyComplex(double real, double imag) {
//        re = real;
//        im = imag;
//    }
//
//    // return a string representation of the invoking Complex object
//    public String toString() {
//        if (im == 0) return re + "";
//        if (re == 0) return im + "i";
//        if (im <  0) return re + " - " + (-im) + "i";
//        return re + " + " + im + "i";
//    }
//
//    // return abs/modulus/magnitude and angle/phase/argument
//    public double abs()   { return Math.hypot(re, im); }  // Math.sqrt(re*re + im*im)
//    public double phase() { return Math.atan2(im, re); }  // between -pi and pi
//
//    // return a new Complex object whose value is (this + b)
//    public MyComplex plus(MyComplex b) {
//        MyComplex a = this;             // invoking object
//        double real = a.re + b.re;
//        double imag = a.im + b.im;
//        return new MyComplex(real, imag);
//    }
//
//    // return a new Complex object whose value is (this - b)
//    public MyComplex minus(MyComplex b) {
//        MyComplex a = this;
//        double real = a.re - b.re;
//        double imag = a.im - b.im;
//        return new MyComplex(real, imag);
//    }
//    
//
//    
//
//    // return a new Complex object whose value is (this * b)
//    public MyComplex times(MyComplex b) {
//        MyComplex a = this;
//        double real = a.re * b.re - a.im * b.im;
//        double imag = a.re * b.im + a.im * b.re;
//        return new MyComplex(real, imag);
//    }
//
//    // scalar multiplication
//    // return a new object whose value is (this * alpha)
//    public MyComplex times(double alpha) {
//        return new MyComplex(alpha * re, alpha * im);
//    }
//
//    // return a new Complex object whose value is the conjugate of this
//    public MyComplex conjugate() {  return new MyComplex(re, -im); }
//    
//    // negation
//    public MyComplex negation() {  return new MyComplex(-re, -im); }
//
//    // return a new Complex object whose value is the reciprocal of this
//    public MyComplex reciprocal() {
//        double scale = re*re + im*im;
//        return new MyComplex(re / scale, -im / scale);
//    }
//
//    // return the real or imaginary part
//    public double re() { return re; }
//    public double im() { return im; }
//
//    // return a / b
//    public MyComplex divides(MyComplex b) {
//        MyComplex a = this;
//        return a.times(b.reciprocal());
//    }
//
//    // return a new Complex object whose value is the complex exponential of this
//    public MyComplex exp() {
//        return new MyComplex(Math.exp(re) * Math.cos(im), Math.exp(re) * Math.sin(im));
//    }
//
//    // return a new Complex object whose value is the complex sine of this
//    public MyComplex sin() {
//        return new MyComplex(Math.sin(re) * Math.cosh(im), Math.cos(re) * Math.sinh(im));
//    }
//
//    // return a new Complex object whose value is the complex cosine of this
//    public MyComplex cos() {
//        return new MyComplex(Math.cos(re) * Math.cosh(im), -Math.sin(re) * Math.sinh(im));
//    }
//
//    // return a new Complex object whose value is the complex tangent of this
//    public MyComplex tan() {
//        return sin().divides(cos());
//    }
//    
//    // return a new complex^degree
//    /*public MyComplex pow(MyComplex a, double degree) {
//		
//		// count root from abs a
//		double root_abs_a = Math.pow(a.abs(),1/degree);
//		
//		// hitung besar sudut teta
//		double teta = Math.atan2(a.im(), a.re());
//		
//		double real = root_abs_a*Math.cos(teta/degree);
//        double imag = root_abs_a*Math.sin(teta/degree);
//        return new MyComplex(real, imag);
//        
//    }*/
//    
// public MyComplex pow(double degree, int type) {
//		
//		// count root from abs
//		double root_abs = Math.pow(abs(),degree);
//		double teta = Math.atan2(im,re);
//		if(type==1){
//			// hitung besar sudut teta
//		    teta = Math.atan2(re,im);
//		}else{
//			teta = Math.atan2(im,re);
//		}
//		
//		double real = root_abs*Math.cos(teta/(1/degree));
//        double imag = root_abs*Math.sin(teta/(1/degree));
//        return new MyComplex(real, imag);
//        
//    }
//
//    // a static version of plus
//    public static MyComplex plus(MyComplex a, MyComplex b) {
//        double real = a.re + b.re;
//        double imag = a.im + b.im;
//        MyComplex sum = new MyComplex(real, imag);
//        return sum;
//    }
//
//
//
//    // sample client for testing
//    public static void main(String[] args) {
//        MyComplex a = new MyComplex(5.0, 6.0);
//        MyComplex b = new MyComplex(-3.0, 4.0);
//
//        System.out.println("a            = " + a);
//        System.out.println("b            = " + b);
//        System.out.println("Re(a)        = " + a.re());
//        System.out.println("Im(a)        = " + a.im());
//        System.out.println("b + a        = " + b.plus(a));
//        System.out.println("a - b        = " + a.minus(b));
//        System.out.println("a * b        = " + a.times(b));
//        System.out.println("b * a        = " + b.times(a));
//        System.out.println("a / b        = " + a.divides(b));
//        System.out.println("(a / b) * b  = " + a.divides(b).times(b));
//        System.out.println("conj(a)      = " + a.conjugate());
//        System.out.println("|a|          = " + a.abs());
//        System.out.println("tan(a)       = " + a.tan());
//    }
//
//}
//
//
