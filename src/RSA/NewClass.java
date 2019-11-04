/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RSA;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author pupys
 */
public class NewClass {
    static ArrayList<Character> characters = new ArrayList<Character>( Arrays.asList( 'А', 'Б', 'В', 'Г', 'Д', 'Е', 'Ё', 'Ж', 'З', 'И',
                                                'Й', 'К', 'Л', 'М', 'Н', 'О', 'П', 'Р', 'С', 
                                                'Т', 'У', 'Ф', 'Х', 'Ц', 'Ч', 'Ш', 'Щ', 'Ь', 'Ы', 'Ъ',
                                                'Э', 'Ю', 'Я', ' ', '1', '2', '3', '4', '5', '6', '7',
                                                '8', '9', '0',
                                                'A', 'B', 'C', 'D', 'I', 'F', 'G', 'H', 'I', 'J', 'K',
                                                'L', 'M', 'N') );
    public static void main1(String[] args){
        for(Character c:characters){
            
            byte b_arr[] = (c.toString()).getBytes(Charset.forName("UTF-8"));
            String str = new String(b_arr,Charset.forName("UTF-8") );
            
            System.out.print("for : "+str);
            for(int i=0; i<b_arr.length;i++){
                System.out.print(" byte "+i+" :"+b_arr[i]+"    ");
                System.out.print(String.format("%8s", Integer.toBinaryString(b_arr[i] & 0xFF)).replace(' ', '0'));
            }
            
            
            
            BigInteger bytesTointeger = new BigInteger(b_arr);
            if (bytesTointeger.compareTo(BigInteger.ZERO) < 0)
                bytesTointeger = bytesTointeger.add(BigInteger.ONE.shiftLeft(b_arr.length*8));
           
            System.out.println("bytesTointeger "+ bytesTointeger);
           
        }
        
        
        byte[] b_test = "АБ".getBytes(Charset.forName("UTF-8"));
        for(int i=0; i<b_test.length;i++){
             System.out.print(" byte \"AB\" "+b_test[i]+"    ");
        }
        
//        BigInteger bInt = new BigInteger("5339253393");
//        byte[] array  = bInt.toByteArray();
//        for(int i=0; i<array.length;i++){
//             System.out.print(" byte "+array[i]+"    ");
//        }
//        System.out.println( new String(bInt.toByteArray(), Charset.forName("UTF-8")));
    }
}
 
           //ByteBuffer wrapped = ByteBuffer.wrap(b_arr); // big-endian by default
           //System.out.print(" ByteBuffer wrapped  "+ wrapped );
           //short num = wrapped.getShort();

           //System.out.println(ByteBuffer.wrap(b_arr).getShort());
