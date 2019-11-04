/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RSA;

import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import testing_bits_of_number.BigIntPrimeNumber;

/**
 *
 * @author pupys
 */
public class MainClass {
    
    public static void main(String[] args){
        Person alice = createKeys();
        Person bob = createKeys();
        
        alice.setOtherPersonPublicKey(bob.getPk());
        bob.setOtherPersonPublicKey(alice.getPk());
        
        
        ArrayList<BigInteger> encodedMEssage = readFileAndEncodMEssage(bob.getOtherPersonPublicKey());
        System.out.print("Encoding message:");
        for(int i = 0;i<encodedMEssage.size();i++)
            System.out.print(encodedMEssage.get(i)+"  ");
        
        String strDecoded = dencodMEssage(encodedMEssage,alice.getSk());
    }
    
    public static Person createKeys() {
        BigInteger p, q, n, f, e, d;
        
        p = BigIntPrimeNumber.generationBigPrimeNum(30);
        q = BigIntPrimeNumber.generationBigPrimeNum(30);
        
        n = p.multiply(q);
        
        f = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        
        e = getE(f);
        System.out.println("(e,n)"+e+" "+n );
        
        d = e.modPow((BigInteger.valueOf(-1)), f);
        System.out.println("(d,n)"+d+" "+n);
        
        return new Person(new PublicKey(e,n),new SecretKey(d,n));
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
    
    //считать текст из файла
    private static  ArrayList<BigInteger> readFileAndEncodMEssage(PublicKey keyForCipher) {
        //Читаем первую главу в строку
        int k = 17;
        String str ="";
        try(FileReader reader = new FileReader("fortests\\for_tests_rsa.txt")){
            char[] buf = new char[256];
            int c;
            while((c = reader.read(buf))>0){//В данном случае считываем последовательно символы из файла в массив из 256 символов, пока не дойдем до конца файла в этом случае метод read возвратит число -1.
                if(c < 256){
                    buf = Arrays.copyOf(buf, c);
                }
                str += new String(buf);
            } 
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        ArrayList<BigInteger> message = new ArrayList<>();
        for(int i =0;i<str.length();i++){
            byte[] bytes = ((str.charAt(i))+"").getBytes(Charset.forName("UTF-8"));
            BigInteger bytesTointeger = new BigInteger(bytes);
            if (bytesTointeger.compareTo(BigInteger.ZERO) < 0)
                bytesTointeger = bytesTointeger.add(BigInteger.ONE.shiftLeft(bytes.length*8));
            message.add(bytesTointeger);
            System.out.print(bytesTointeger + "  ");
        }
        System.out.println();
        for(int ind=0;ind<message.size();ind++){
            BigInteger temp = message.get(ind);
            message.set(ind, temp.modPow(keyForCipher.getE(), keyForCipher.getN()));
        }
        
        return message;
    }
    
    //считать текст из файла
    private static String dencodMEssage(ArrayList<BigInteger> encodMEssage,SecretKey sk) {
        for(int i=0;i<encodMEssage.size();i++){
            BigInteger m = encodMEssage.get(i).modPow(sk.getD(), sk.getN());
            
            byte[] bytes = m.toByteArray();
            String str = new String( bytes,Charset.forName("UTF-8") );
            System.out.println("Decoded: "+str);
        }
        return "";
    }
}
