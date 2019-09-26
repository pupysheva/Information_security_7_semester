/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generation_and_search_prime_numbers;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author pupys
 */
public class Generation_and_search_prime_numbers {
    //Число разрядов числа
    static int n = 50; n++;
    static int N = 2000;
    static BigInteger p = BigInteger.ZERO;
    static List<BigInteger> list_prime_num = new ArrayList<>();;
    public static void main(String[] args) {
        do{
            generation_n_bit_random_number();
            System.out.println("\n"+p);
            sieve_of_eratosthenes();
        }while(isDivided_into_simple());
        
        int b  = 0;
        for(int i=0;i<5;i++){
            b = robin_miller_test();
            System.out.println(b);
        }
        
    }
    static public void generation_n_bit_random_number(){
        String s = "1";
        for(int i=1;i<n-1;i++){
            int temp_didit = (int)(Math.random()*10);
            s=s+temp_didit;
        }
        s+=1;
        p = new BigInteger(s);
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
                //System.out.println(i);
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
           if(p.mod(j).compareTo(BigInteger.ZERO) == 0) {
               System.out.println(p+"  "+j+" "+flag_return);
               return true;
           }
        }
        //Если число не делится на простые то false
        return flag_return;
    }

    private static int robin_miller_test() {
        
        //остановилася тута
        //сколько раз число делится на 2
        int k = 0;
        BigInteger TWO = BigInteger.valueOf(2);
        BigInteger temp_num = p.subtract(BigInteger.ONE);
        while(temp_num.compareTo(BigInteger.ZERO) == 1) { //если temp_num>0
            if(temp_num.mod(TWO).compareTo(BigInteger.ZERO)==0){
                k++;
                temp_num = temp_num.divide(TWO);
            }
            else
            {
                return k;
            }
        }
        return k;
        
        //Вычислить m
        
        //Выбрать случайное а
        
        //Установить j=0 и
        
        
    }
}
