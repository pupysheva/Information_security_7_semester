/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package srp6;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author pupys
 */
public class Sha256 {
    //Этот класс использую Сервер и Клиент
    public static byte[] getSHA(String input) throws NoSuchAlgorithmException{ 
        //Статический метод getInstance вызывается с хэшированием SHA
        MessageDigest md = MessageDigest.getInstance("SHA-256");  
        // метод digest () вызван
        // рассчитать дайджест сообщения ввода
        // и возвращаем массив байтов
        return md.digest(input.getBytes(StandardCharsets.UTF_8));  
    } 
    
    public static String toHexString(byte[] hash){ 
        // Преобразование байтового массива в представление знака
        BigInteger number = new BigInteger(1, hash);  
  
        // Преобразуем дайджест сообщения в шестнадцатеричное значение 
        StringBuilder hexString = new StringBuilder(number.toString(16));  
  
        // Pad with leading zeros 
        while (hexString.length() < 32){  
            hexString.insert(0, '0');  
        }  
        return hexString.toString();  
    } 
  
    public static BigInteger get_sha256(String[] args){ 
        try{ 
            String joined = String.join("", args);
            return new BigInteger(toHexString(getSHA(joined)),16);  
        } 
        // Для указания неправильных алгоритмов дайджеста сообщений
        catch (NoSuchAlgorithmException e) {  
            System.out.println("Exception thrown for incorrect algorithm: " + e);  
        }
        return BigInteger.ZERO;
    } 
}
