/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RSA;
import java.math.BigInteger;
import testing_bits_of_number.BigIntPrimeNumber;//.generationBigPrimeNum;//Имя пакет, класс, имя метода

/**
 *
 * @author pupys
 */
public class Rsa {
    
    static BigInteger p;
    static BigInteger q;
    
    static void main(String[] args) {
        p = BigIntPrimeNumber.generationBigPrimeNum(30);
        q = BigIntPrimeNumber.generationBigPrimeNum(30);
        System.out.println(p+""+q);
    }
}
