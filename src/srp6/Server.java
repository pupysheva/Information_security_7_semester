/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srp6;

import java.math.BigInteger;
import static srp6.Sha256.get_sha256;
import testing_bits_of_number.BigIntPrimeNumber;

/**
 *
 * @author pupys
 */
public class Server {
   String I;
   String salt;
   BigInteger v;
   
   BigInteger A;
   BigInteger b;
   BigInteger B;
   BigInteger u;
   
   BigInteger K;
   
   BigInteger S;
   BigInteger R;
   
   BigInteger M;

    public void setI_s_v(String I,String s,BigInteger v) {
        this.I = I;
        this.salt = s;
        this.v = v;
    }

    public Boolean setA(BigInteger A) {
        if(A.compareTo(BigInteger.ZERO)!=0){ //Если А != 0
            this.A = A;
            return true;
        }
        else return false;
    }

    public void setSercretAndPublicB(BigInteger k,BigInteger g, BigInteger N) {
        this.b = BigIntPrimeNumber.generationBigPrimeNum(15);
        this.B = (k.multiply(v) .add(g.modPow(this.b, N)) ).mod(N);
    }

    public String getSalt() {
        return salt;
    }

    public BigInteger getB() {
        return B;
    }

    public BigInteger setAndGetU() {
        this.u = get_sha256(new String[]{this.A.toString(),this.B.toString()});
        return this.u;
    }
    public void setKey(BigInteger k, BigInteger g, BigInteger N) {
        this.S = (this.A.multiply(this.v.modPow(this.u, N))).modPow(this.b, N);
        this.K = get_sha256(new String[]{this.S.toString()});
    }

    public Boolean setAndGetM(BigInteger k, BigInteger g, BigInteger N, BigInteger clientM) {
        BigInteger h_N = get_sha256(new String[]{N.toString()});
        BigInteger h_g = get_sha256(new String[]{g.toString()});
        BigInteger xorN_g = h_N.xor(h_g);
        BigInteger h_I= get_sha256(new String[]{this.I});
        this.M = get_sha256(new String[]{xorN_g.toString(),h_I.toString(),this.salt,this.A.toString(),this.B.toString(),this.K.toString()});
        
        if(clientM.compareTo(this.M)==0){
            return true;
        }else{
            return false;
        }
    }

    public void setR() {
        this.R = get_sha256(new String[]{this.A.toString(),this.M.toString(),this.K.toString()});
    }

    public BigInteger getR() {
        return R;
    }
    
}
