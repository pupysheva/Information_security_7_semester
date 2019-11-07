/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RSA;

import java.math.BigInteger;
import testing_bits_of_number.BigIntPrimeNumber;

/**
 *
 * @author pupys
 */
public class Person {
    private PublicKey pk;
    private SecretKey sk;
    private PublicKey otherPersonPublicKey;

    public Person() {
        BigInteger p, q, n, f, e, d;
        
        p = BigIntPrimeNumber.generationBigPrimeNum(30);
        q = BigIntPrimeNumber.generationBigPrimeNum(30);
        
        n = p.multiply(q);
        
        f = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        
        e = getE(f);
        System.out.println("(e,n)"+e+" "+n );
        
        d = e.modPow((BigInteger.valueOf(-1)), f);
        System.out.println("(d,n)"+d+" "+n);
        
        this.sk = new SecretKey(d,n);
        this.pk = new PublicKey(e,n);
    }

    public PublicKey getPk() {
        return pk;
    }

    public void setPk(PublicKey pk) {
        this.pk = pk;
    }

    public SecretKey getSk() {
        return sk;
    }

    public void setSk(SecretKey sk) {
        this.sk = sk;
    }

    public PublicKey getOtherPersonPublicKey() {
        return otherPersonPublicKey;
    }

    public void setOtherPersonPublicKey(PublicKey otherPersonPublicKey) {
        this.otherPersonPublicKey = otherPersonPublicKey;
    }
    
    private static BigInteger getE(BigInteger f){
        BigInteger e;
        for(e = BigInteger.valueOf(2); e.compareTo(f)==-1;e = e.add(BigInteger.ONE)){
            //System.out.println(e+"  "+e.gcd(f));
            if(e.gcd(f).compareTo(BigInteger.ONE) == 0)
               return e;
        }
        return BigInteger.valueOf(-1);
    }
}
