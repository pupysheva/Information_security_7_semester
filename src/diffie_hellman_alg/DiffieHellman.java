/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package diffie_hellman_alg;
import static testing_bits_of_number.Testing_bits_of_number.generation_prime_num;//Имя пакет, класс, имя метода

/**
 *
 * @author pupys
 */
public class DiffieHellman {
    /*Протокол Ди́ффи — Хе́ллмана (англ. Diffie–Hellman, DH) — криптографический протокол, позволяющий двум и более сторонам получить общий секретный ключ, 
    используя незащищенный от прослушивания канал связи. Полученный ключ используется для шифрования дальнейшего обмена с помощью алгоритмов симметричного шифрования.*/
    
    /*Симметри́чные криптосисте́мы (также симметричное шифрование, симметричные шифры) (англ. symmetric-key algorithm) — способ шифрования, в котором для шифрования и расшифровывания применяется один и тот же криптографический ключ.*/
    public static long aAlice;
    public static long gAlice;
    public static long pAlice;
    public static long A_Alice;
    public static long kAlice;
    
    public static long bBob;
    public static long B_Bob;
    public static long kBob;
    public static void main(String[] args) {
        p(20);
    }
    private static long p(int bits){
        long p = generation_prime_num(bits);
        System.out.println(p);
        return p;
    }
}
