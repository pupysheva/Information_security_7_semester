/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inf_secur_caesar_code;

import java.util.*;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author pupys
 */
public class CaesarsCode {

    
    static List<String> list_letters = new ArrayList<>(Arrays.asList("А","Б","В","Г","Д","Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ъ", "Ы", "Ь", "Э", "Ю", "Я" ));
    static List<Element> list_frequency1 = new ArrayList<>();
    static List<Element> list_frequency_glava1_decod = new ArrayList<>();
    
    static HashMap<String, ElemBigram> map_of_pairs_of_letters = new HashMap<>();
    static HashMap<String, ElemBigram> map_first_chap = new HashMap<>();
    
    public static void main(String[] args) {
        
        //Шифруем первую главу
        char[] first_chapter_encoded = shifr_Caesar();
        
        //Частотный анализ по всей книге букв и пар букв
        init();
        frequency_analysis();
        
        //Расшифровываем первую главу с помощью частотного анализа букв
        String b = new String(first_chapter_encoded);
        char[] rez = b.toCharArray();
        decoding(rez);
        
        
        b = new String(first_chapter_encoded);
        rez = b.toCharArray();
        //Расшифровываем первую главу с помощью частотного анализа пар букв
        decoding_pairs_of_letters(rez);
    }
    
    private static void init() {
        for(int i=0; i<list_letters.size();i++){
            list_frequency1.add(new Element(list_letters.get(i),0));
            list_frequency_glava1_decod.add(new Element(list_letters.get(i),0));
        }
        for(int i=0; i<list_letters.size();i++){
            for(int j=0; j<list_letters.size();j++){
                String name = list_letters.get(i)+list_letters.get(j);
                map_of_pairs_of_letters.put(name ,new ElemBigram(name ,0));
                map_first_chap.put(name ,new ElemBigram(name ,0));
            }
        }
    }

    
    
    //Зашифровываем 1 главу
    private static char[] shifr_Caesar() {
        //Читаем первую главу в строку
        int k = 17;
       String string_first_chapter ="";
        try(FileReader reader = new FileReader("fortests\\1 глава.txt")){
            char[] buf = new char[256];
            int c;
            while((c = reader.read(buf))>0){//В данном случае считываем последовательно символы из файла в массив из 256 символов, пока не дойдем до конца файла в этом случае метод read возвратит число -1.
                if(c < 256){
                    buf = Arrays.copyOf(buf, c);
                }
                string_first_chapter += new String(buf);
            } 
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        //шифруем первую  главу
        char[] result = string_first_chapter.toCharArray();
        for (int j=0;j<result.length;j++) {
            String strTemp = String.valueOf(result[j]).toUpperCase();
            
            if(list_letters.contains(strTemp)){
                int ind_list = list_letters.indexOf(strTemp);
                String newStr;
                    if((ind_list+k)<list_letters.size())
                        newStr = list_letters.get(ind_list+k);
                    else newStr = list_letters.get((ind_list+k) - list_letters.size() );
                    if(Character.isLowerCase(result[j])) newStr = newStr.toLowerCase();
                    result[j] = newStr.charAt(0);
            }
        }
        //записываем зашифрованную главу в другой файл
        try(FileWriter writer = new FileWriter("fortests\\Зашифрованная первая глава.txt", false)){
            String b = new String(result);
            writer.write(result);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 
        return result;
    }
    
    
    
    
    //Чтение из файла всей книги Война и мир и частотный анализ букв и пар букв
    private static void frequency_analysis() {
        try(FileReader reader = new FileReader("fortests\\Война и мир.txt")){
            char[] buf = new char[256];
            int c;//колличество прочитанных символов
            while((c = reader.read(buf))>0){//В данном случае считываем последовательно символы из файла в массив из 256 символов, пока не дойдем до конца файла в этом случае метод read возвратит число -1.
                if(c < 256){
                    buf = Arrays.copyOf(buf, c);
                }
                search_count_letter(buf,list_frequency1);
                func(buf,map_of_pairs_of_letters);
            } 
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
        sorting_list(list_frequency1);
    }
    
    //находим колличество встречаний каждой буквы
    private static void search_count_letter(char[] buf,List<Element> list) {
        for(int i=0;i<buf.length;i++) {
            String strTemp;
            if(Character.isLowerCase(buf[i])) strTemp = String.valueOf(buf[i]).toUpperCase();
            else strTemp = String.valueOf(buf[i]);
            
            if(list_letters.contains(strTemp)){
                int ind = list_letters.indexOf(strTemp);
                int tempValue = list.get(ind).getValue()+1;
                list.get(ind).setValue(tempValue);
            }
        }
    }
   
    
    
    
    //Находим какие буквы чаще встречаются в зашифрованной первой главе
    //Расшифровываем первую главу
    private static void decoding(char[] first_chapter_encoded) {
        search_count_letter(first_chapter_encoded,list_frequency_glava1_decod);
        sorting_list(list_frequency_glava1_decod);
        make_relation();
        printer_list(list_frequency1,true);
        //Расшифровываем первую главу
        for(int i=0;i<first_chapter_encoded.length;i++) {
            String strTemp;
            if(Character.isLowerCase(first_chapter_encoded[i])) strTemp = String.valueOf(first_chapter_encoded[i]).toUpperCase();
            else strTemp = String.valueOf(first_chapter_encoded[i]);
            if(list_letters.contains(strTemp)){//Если эта буква есть в списке
                if(Character.isLowerCase(first_chapter_encoded[i])) 
                   first_chapter_encoded[i] = search_element_in_list(strTemp).toLowerCase().charAt(0);
                else  first_chapter_encoded[i] = search_element_in_list(strTemp).charAt(0);
            }
        }
        //сделать из массива char строку
        String b = new String(first_chapter_encoded);
        //записать в файл "расшифрованная первая глава"
        try(FileWriter writer = new FileWriter("fortests\\Расшифрованная первая глава.txt", false)){//False означает стирать содержимое, если там что-то есть
            writer.write(b);
            writer.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 
        
    }

    private static void sorting_list(List<Element> list) {
        list.sort(new Comparator<Element>() {
        @Override
            public int compare(Element o1, Element o2) {
                if (o1.getValue() == o2.getValue()) return 0;
                else if (o1.getValue()< o2.getValue()) return 1;
                else return -1;
            }
        });
    }
    private static void printer_list(List<Element> list,boolean t) {
        if(t) {
            System.out.println(list.size());
            for(Element el:list){
                System.out.println(el.getName()+"  "+el.getValue()+ "  "+el.getRelation());
            }
        }
        else
            for(Element el:list){
                System.out.println(el.getName()+"  "+el.getValue());
            }
    }
    //Делает связь между буквами и зашифрованными буквами
    private static void make_relation() {
        for(int i=0;i<list_frequency1.size();i++){
            list_frequency1.get(i).setRelation(list_frequency_glava1_decod.get(i).getName());
        }
    }
    private static String search_element_in_list(String l) {
        String ind ="";
        for(Element el:list_frequency1){
            if(el.getRelation().equals(l)) {
                ind = el.getName();
                break;
            }
        }
        return ind;
    }
   
    
    
    
    
    private static void func(char[] mass,HashMap<String, ElemBigram> map) {
        Integer tempInt=0;
        for(int i =0; i<mass.length-1;i++){
            String s = ""+mass[i]+mass[i+1];
            s = s.toUpperCase();
            if(map.keySet().contains(s)){//проверить если строка в map
                //если есть по ключу в мар прибалвяем значение
                tempInt = map.get(s).getCount()+1;
                map.get(s).setCount(tempInt);
            }
        }
    }
    private static void decoding_pairs_of_letters(char[] first_chapter_encoded) {
        //смотрим пары букв в зашифрованной 1 главе
        func(first_chapter_encoded,map_first_chap);
        //отсортировать 2 map
        List<ElemBigram> elems_toSort_map1 = new ArrayList<ElemBigram>(map_of_pairs_of_letters.values());
        Collections.sort(elems_toSort_map1, new Comparator<ElemBigram>() {
            public int compare(ElemBigram o1, ElemBigram o2) {
                if (o1.getCount() == o2.getCount()) return 0;
                else if (o1.getCount()< o2.getCount()) return 1;
                else return -1;
            }
        });
        List<ElemBigram> elems_toSort_map2 = new ArrayList<ElemBigram>(map_first_chap.values());
        Collections.sort(elems_toSort_map2, new Comparator<ElemBigram>() {
            public int compare(ElemBigram o1, ElemBigram o2) {
                if (o1.getCount() == o2.getCount()) return 0;
                else if (o1.getCount()< o2.getCount()) return 1;
                else return -1;
            }
        });
        
        /*for(int i=0;i<elems_toSort_map1.size();i++){
            System.out.println(elems_toSort_map1.get(i).getName()+"  "+elems_toSort_map1.get(i).getCount()+"  "+elems_toSort_map2.get(i).getName()+"  "+elems_toSort_map2.get(i).getCount());
        }*/
        
        //сопоставить 2 map
        
        HashMap<String, String> new_map = new HashMap<>();
        for(int i=0; i<elems_toSort_map1.size();i++){
            new_map.put(elems_toSort_map2.get(i).getName(), elems_toSort_map1.get(i).getName());
        }
        for (Map.Entry<String, String> entry : new_map.entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue());
        }
       
        //Расшифровываем первую главу
        String str_result ="";
        String t;
        char[] replacement;
        char[] res_char_mass = onlyRussian(first_chapter_encoded);
        //System.out.println(res_char_mass);
        String str =  new String(res_char_mass);
        String[] string_mass = str.split("-");
        /*for(String s:string_mass){
            System.out.println(s);
        }*/
        for(String st:string_mass ){
            if(!st.isEmpty()){
                char[] ch = st.toCharArray();
                for(int i=0; i<ch.length;i+=2){
                    if(i!= ch.length -1){
                        t = (""+ch[i]+ch[i+1]).toUpperCase();
                        if (new_map.containsKey(t)){
                            replacement = new_map.get(t).toCharArray();
                            if(Character.isLowerCase(ch[i])) replacement[0] = (replacement[0]+"").toLowerCase().charAt(0);
                            if(Character.isLowerCase(ch[i+1])) replacement[1] = (replacement[1]+"").toLowerCase().charAt(0);
                            ch[i] = replacement[0]; 
                            ch[i+1] = replacement[1];
                        }
                    }
                    else if((i== ch.length -1 && ch.length % 2!=0)|| (ch.length == 1)){
                        t = (""+ch[i]).toUpperCase();
                        String temp = search_element_in_list(t);
                        if(!temp.equals("")){
                            if(Character.isLowerCase(ch[i])) ch[i] = temp.toLowerCase().charAt(0);
                            else ch[i] = temp.charAt(0);
                        }
                    }
                }
                str_result += new String(ch);
                str_result += " ";
            }
        }
        
        //Записываем в файл "Расшифоровка биграммами.txt"
        try(FileWriter writer1 = new FileWriter("fortests\\Расшифоровка биграммами.txt", false)){//False означает стирать содержимое, если там что-то есть
            writer1.write(str_result);
            writer1.flush();
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        } 
    
    }
   
    public static char[] onlyRussian(char[] input)
    {
        StringBuilder sb = new StringBuilder();
        for(char a:input){
            if('а' <= a && a <= 'я'
                    || 'А' <= a && a <= 'Я'
                    || 'Ё' == a || 'ё' == a 
                    || 'A' <= a && a <= 'Z'
                     || 'a' <= a && a <= 'z' )
                sb.append(a);
            if(a == ' ') {
                sb.append('-');
            }
        }
        return sb.toString().toCharArray();
    }
}