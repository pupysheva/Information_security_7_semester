/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srp6;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

/**
 *
 * @author pupys
 */
public class MainClass {
    public static BigInteger k = BigInteger.valueOf(3);
    public static BigInteger N = new BigInteger("2903");
    public static BigInteger g = antiderivativeRootModuloP();
    public static void main(String[] args){
        
        //Регистрация
            //Клиент генерирует
            //s - соль
            //x = H(s,p)
            //v = g**x  % N
            //Клиент отправляет на сервер I,s,v
        
        Client client = new Client("username","1234user");
        client.setV(g,N);
        
        Server server = new Server();
        server.setI_s_v(client.getI(),client.getSalt(),client.getV());
        
        
        
        
        //Аутентификация
        
        //Клиент генерирует рандомное число а 
        //отправляет серверу I и A = g^a  % N
        //client.setPublicB("aaaaaabbbb");
        client.setSecretAndPublicA(g,N);
        Boolean flagConnectionA = server.setA(client.getA());
        if(flagConnectionA==true){
            server.setSercretAndPublicB(k,g,N);
            Boolean flagConnectionB = client.setPublicB(server.getSalt(),server.getB());
            if(flagConnectionB==true){
                BigInteger uClient = client.setAndGetU();
                BigInteger uServer = server.setAndGetU();
                if(uClient.compareTo(BigInteger.ZERO)!=0  &&  uServer.compareTo(BigInteger.ZERO)!=0 ){
                    
                    //Вычисление ключей сессии
                    client.setKey(k,g,N);
                    server.setKey(k,g,N);
                    
                    //Свериться, что ключи key совпадают
                    //Клиент генерирует подтверждение
                    client.setM(k,g,N);
                    Boolean flagConnectionM = server.setAndGetM(k,g,N,client.getM());
                    System.out.println(client.K);
                    System.out.println(server.K);
                    if(flagConnectionM){
                        server.setR();
                        String result = client.cheackR(server.getR());
                        System.out.println(result);
                    }else{
                        System.out.println("Соединение прервано!");
                    }
                }else{
                    System.out.println("Соединение прервано!");
                }
                
            }else{
                System.out.println("Соединение прервано!");
            }
        }else{
            System.out.println("Соединение прервано!");
        }

    }
    //первообразный корень по модулю p
    private static BigInteger antiderivativeRootModuloP(){
        List<BigInteger> list_prime_num = new ArrayList<>();
        sieveOfEratosthenes(list_prime_num);
        boolean flag = true;
        HashSet<BigInteger> rems = new HashSet<BigInteger>();
        for(int i=0;i<list_prime_num.size();i++){
            flag = true;
            rems.clear();
            g = list_prime_num.get(i);
            for(BigInteger j =BigInteger.ONE ;j.compareTo(N) == -1;j = j.add(BigInteger.ONE)){//i от 1; i<pAlice: i++
                BigInteger r = g.modPow(j, N);
                if(!rems.contains(r)) rems.add(r);
                else {
                    flag = false;
                    break;
                }
            }
            if(flag)
                for(BigInteger j =BigInteger.ONE ;j.compareTo(N) == -1;j = j.add(BigInteger.ONE))
                    if(!rems.contains(j)){flag = false;}
            
            if(flag) {
                return g;
            }
        }
        
        return BigInteger.ZERO;
    }
    
    //Решето Эрастофена
    static public void sieveOfEratosthenes(List<BigInteger> list_prime_num){
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
    }

    
}
