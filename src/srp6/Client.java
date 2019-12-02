/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srp6;
import java.math.BigInteger;
import static srp6.Sha256.get_sha256;

import java.util.Random;
import testing_bits_of_number.BigIntPrimeNumber;


/**
 *
 * @author pupys
 */
public class Client {
    
    String I, p, salt;
    BigInteger x,v;
    BigInteger a;
    BigInteger A;

    BigInteger B;
    BigInteger u;
    
    BigInteger K;
    
    BigInteger S;
    BigInteger R;
    
    BigInteger M;
    
    public Client(String I, String p) {
        this.I = I;
        this.p = p;
        this.salt = getRandomWord(20,"abcdefgHIJKlmnopQrStUvwxYz!");
        this.x = get_sha256(new String[]{this.salt,this.p});
    }

    public void setV(BigInteger g,BigInteger N) {
        this.v = g.modPow(this.x, N);
    }
    
    public void setSecretAndPublicA(BigInteger g,BigInteger N) {
        this.a = BigIntPrimeNumber.generationBigPrimeNum(15);
        this.A = g.modPow(this.a, N);
    }
    
    public Boolean setPublicB(String salt, BigInteger B) {
        if(B.compareTo(BigInteger.ZERO)!=0){
            this.B = B;
            this.salt = salt;
            return true;
        }
        else return false;
    }
    
    public String getI() {
        return this.I;
    }

    public String getP() {
        return this.p;
    }

    public String getSalt() {
        return this.salt;
    }

    public BigInteger getX() {
        return x;
    }

    public BigInteger getV() {
        return v;
    }

    
    public static String getRandomWord(int length, String alphabet) {
        Random RND = new Random();
        StringBuilder sb = new StringBuilder(Math.max(length, 16));
        for (int i = 0; i < length; i++) {
            int len = alphabet.length();
            int random = RND.nextInt(len);
            char c = alphabet.charAt(random);
            sb.append(c);
        }
        return sb.toString();
    }

    public BigInteger setAndGetU() {
        this.u = get_sha256(new String[]{this.A.toString(),this.B.toString()});
        return this.u;
    }

    public BigInteger getA() {
        return this.A;
    }

    public void setKey(BigInteger k, BigInteger g, BigInteger N) {
        this.x = get_sha256(new String[]{this.salt,this.p});
        this.S = (this.B.subtract(k.multiply(g.modPow(x, N)))).modPow(this.a.add(this.u.multiply(this.x)), N);
        this.K = get_sha256(new String[]{this.S.toString()});
    }

    public void setM(BigInteger k, BigInteger g, BigInteger N) {
        BigInteger h_N = get_sha256(new String[]{N.toString()});
        BigInteger h_g = get_sha256(new String[]{g.toString()});
        BigInteger xorN_g = h_N.xor(h_g);
        BigInteger h_I= get_sha256(new String[]{this.I});
        this.M = get_sha256(new String[]{xorN_g.toString(),h_I.toString(),this.salt,this.A.toString(),this.B.toString(),this.K.toString()});
    }

    public BigInteger getM() {
        return M;
    }

    public String cheackR(BigInteger r) {
        this.R = get_sha256(new String[]{this.A.toString(),this.M.toString(),this.K.toString()});
        if(r.compareTo(this.R)==0){
            return "Соединение установлено";
        } else return  "Соединение НЕ установлено";
    }

    void setPublicB(String aaaaaabbbb) {
        this.p = aaaaaabbbb;
    }

}
