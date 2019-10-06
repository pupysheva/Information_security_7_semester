/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generation_and_search_prime_numbers;

import static testing_bits_of_number.Testing_bits_of_number.generation_prime_num;//Имя пакет, класс, имя метода
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 *
 * @author pupys
 */
public class PrimeNumbers {
    //Число разрядов числа
    static BigInteger num_p = BigInteger.ZERO;
    static int n = 100;
    static int N = 2000;
    static List<BigInteger> list_prime_num = new ArrayList<>();
    static int K = 5;
    
    /*static // Статический конструктор.
    {   
        sieve_of_eratosthenes();
    }*/
    
    public static void main(String[] args) {
        /*long a = generation_prime_num(63);
        System.out.println(a);*/
        generation_Big_prime_num();
        
    }
    public static void generation_Big_prime_num() {
        boolean c = false;
        sieve_of_eratosthenes();
        while(!c){
            do{
                generation_n_bit_random_number();
            }while(isDivided_into_simple());

            //тест Робина-Миллера
            c = rabin_miller_test();
        }
        System.out.println(num_p);
    }
    static public void generation_n_bit_random_number(){
        /*String s = "1";
        for(int i=1;i<n-1;i++){
            int temp_didit = (int)(Math.random()*10);
            s=s+temp_didit;
        }
        s+=1;
        num_p = new BigInteger(s);
        System.out.println("p = "+ num_p);*/
        num_p = BigInteger.ONE;
        for(int j = 0; j < n - 1; j++)
            num_p = (num_p.multiply(BigInteger.valueOf(2))).add(BigInteger.valueOf((int) (Math.random()*2)));
        num_p = num_p.or(BigInteger.valueOf(1));
        System.out.println("---------------------------------------------------");
        System.out.println(num_p);
        //num_p |=(1L<<(n-1)) +1L;
        //System.out.println(num_p);
       //System.out.println(Long.toBinaryString(num_p));
        //System.out.println(Math.log(num_p)/Math.log(2));
        //System.out.println("---------------------------------------------------");
       
    }
    
    static public void sieve_of_eratosthenes(){
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

    private static boolean isDivided_into_simple() {
        boolean flag_return = false;
        for(BigInteger j : list_prime_num){
            //если p делится на число (если остаток ==0)
           if(num_p.mod(j).compareTo(BigInteger.ZERO) == 0) {
               System.out.println(num_p+"  "+j+" "+flag_return);
               return true;
           }
        }
        //Если число не делится на простые то false
        return flag_return;
    }

    private static boolean rabin_miller_test() {
        //сколько раз число делится на 2
        BigInteger b = BigInteger.ZERO;
        BigInteger BIGINTEGER_TWO = BigInteger.valueOf(2);
        BigInteger temp_num = num_p.subtract(BigInteger.ONE);
        while(temp_num.compareTo(BigInteger.ZERO) == 1) { //если temp_num>0
            if(temp_num.mod(BIGINTEGER_TWO).compareTo(BigInteger.ZERO)==0){
                b = b.add(BigInteger.ONE);
                temp_num = temp_num.divide(BIGINTEGER_TWO);
            }
            else{
                break;
            }
        }
        System.out.println("b = "+b);
        
        //Вычислить m = (p-1)/(2^b) (разделится без остатка)
        BigInteger m = (num_p.subtract(BigInteger.ONE)).divide(BIGINTEGER_TWO.pow(b.intValue()));
        System.out.println("m = "+m);
        
        //Тест Рабина-Миллера повторяется 5 раз
        for(int i = 0;i<K;i++){
            //Выбрать случайное а<num_p. а должно быть: 1 < a < n
            BigInteger a = nextRandomBigInteger();
            System.out.println("a = "+a);
            
            //Установить j=0
            int j =0;
            BigInteger z = a.modPow(m, num_p);
            System.out.println("z = a ** m % num_p = " + a +" ^ " + m + " mod " + num_p + " = " + z);
            
            if (z.compareTo(BigInteger.ONE) == 0 || z.compareTo(num_p.subtract(BigInteger.ONE) ) == 0){continue;}//то мб и простым
            
            boolean flag_while = true;
            while(flag_while){
                flag_while = false;
                if(j>0 && z.compareTo(BigInteger.ONE) == 0) {return false;}// num_p  не простое, точка выхода
                j+=1;
                if(BigInteger.valueOf(j).compareTo(b) ==-1 && z.compareTo(num_p.subtract(BigInteger.ONE)) == -1){
                    z = z.modPow(BIGINTEGER_TWO, num_p);
                    flag_while = true;
                    continue;
                }
                if(z.compareTo(num_p.subtract(BigInteger.ONE) ) == 0){flag_while = false; break;}//то мб и простым
                if(b.compareTo(BigInteger.valueOf(j))==0 && z.compareTo(num_p.subtract(BigInteger.ONE)) !=0) return false;// num_p  не простое, точка выхода
            }
        }
        return true;
        
        
    }
    public static BigInteger nextRandomBigInteger() {
        /*Следующий метод использует конструктор BigInteger(int numBits, Random rnd) и отклоняет результат, если он больше указанного n.*/
        BigInteger temp_n = new BigInteger(num_p+"");
        Random rand = new Random();
        BigInteger result = new BigInteger(temp_n.bitLength(), rand);
        while( result.compareTo(temp_n) >= 0 && !(result.compareTo(BigInteger.ONE)==1)) {//пока result>n
            //System.out.println("result = "+result);
            result = new BigInteger(temp_n.bitLength(), rand);
        }
        //System.out.println("result = "+result);
        return result;
    }
}
