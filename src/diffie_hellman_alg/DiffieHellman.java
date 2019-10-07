/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diffie_hellman_alg;
import java.math.BigInteger;
import java.util.*;
import testing_bits_of_number.BigIntPrimeNumber;//.generationBigPrimeNum;//Имя пакет, класс, имя метода

/**
 *
 * @author pupys
 */
public class DiffieHellman {
    /*Протокол Ди́ффи — Хе́ллмана (англ. Diffie–Hellman, DH) — криптографический протокол, позволяющий двум и более сторонам получить общий секретный ключ, 
    используя незащищенный от прослушивания канал связи. Полученный ключ используется для шифрования дальнейшего обмена с помощью алгоритмов симметричного шифрования.*/
    
    /*Симметри́чные криптосисте́мы (также симметричное шифрование, симметричные шифры) (англ. symmetric-key algorithm) — способ шифрования, в котором для шифрования и расшифровывания применяется один и тот же криптографический ключ.*/
    public static BigInteger aAlice;
    public static BigInteger gAlice;
    public static BigInteger pAlice;
    public static BigInteger A_Alice;
    public static BigInteger kAlice;
    
    public static BigInteger bBob;
    public static BigInteger B_Bob;
    public static BigInteger kBob;
    
    static List<BigInteger> list_prime_num = new ArrayList<>();
    
    
    public static void main(String[] args) {
        //Известно всем
        sieveOfEratosthenes();
        pAlice = BigIntPrimeNumber.generationBigPrimeNum(15);
        antiderivativeRootModuloP();//gAlice
        
        
        //Закрытые ключи
        aAlice = BigIntPrimeNumber.nextRandomBigInteger(pAlice);
        bBob = BigIntPrimeNumber.nextRandomBigInteger(pAlice);
        System.out.println("aAlice "+aAlice+",  bBob "+bBob);
        //Открытые личные ключи
        A_Alice = gAlice.modPow(aAlice, pAlice);
        B_Bob = gAlice.modPow(bBob, pAlice);
        System.out.println("A "+A_Alice+",  B "+B_Bob);
        
        //Закрытый единый ключь для шифрования
        kAlice = B_Bob.modPow(aAlice, pAlice);
        kBob = A_Alice.modPow(bBob, pAlice);
        System.out.println("K (Alice) "+kAlice+",  K (Bob) "+kBob);
        
    }
    
    //первообразный корнем по модулю p
    private static void antiderivativeRootModuloP(){
        boolean flag = true;
        HashSet<BigInteger> rems = new HashSet<BigInteger>();
        for(int i=0;i<list_prime_num.size();i++){
            System.out.println("-------");
            flag = true;
            rems.clear();
            gAlice = list_prime_num.get(i);
            for(BigInteger j =BigInteger.ONE ;j.compareTo(pAlice) == -1;j = j.add(BigInteger.ONE)){//i от 1; i<pAlice: i++
                BigInteger r = gAlice.modPow(j, pAlice);
                System.out.println("r "+r);
                if(!rems.contains(r)) rems.add(r);
                else {
                    flag = false;
                    break;
                }
            }
            if(flag)
                for(BigInteger j =BigInteger.ONE ;j.compareTo(pAlice) == -1;j = j.add(BigInteger.ONE))
                    if(!rems.contains(j)){flag = false; break;}
            
            if(flag) {
                System.out.println("gAlice "+gAlice);
                break;
            }
        }
        
        if(flag == false){
            gAlice = BigInteger.ZERO;
            System.out.println("flag "+flag+"  "+"gAlice "+gAlice);
        }
    }
    
    static public void sieveOfEratosthenes(){
        int N = 100;
        boolean[] isPrime  = new boolean[N];
        Arrays.fill(isPrime,true);
        isPrime[1] = false;
        for (int i=2; i*i < N; i++)
           if (isPrime[i])
              for (int j=i*i; j < N; j+=i)
                 isPrime[j] = false;
        for(int i=1;i<N;i++){
            if(isPrime[i]){
                list_prime_num.add( BigInteger.valueOf(i));
            }
        }
        for(BigInteger j : list_prime_num){
            //System.out.println(j);
        }
    }
}
