/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inf_secur_caesar_code;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;



public class Temp_using_Map {
    //не получилось, потому что не смога отсортировать Map по ключу
    /*static HashMap<String, Integer> map = new HashMap<>();
    static List<String> list = new ArrayList<>(Arrays.asList("A","Б","В","Г","Д","Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ъ", "Ы", "Ь", "Э", "Ю", "Я" ));
        
    public static void cool_main(String[] args) {
        
        //shifr_Caesar();//////////////////////////////////////////////////////////////////////////////////////////////////
        
        for(String l: list){map.put(l, 0);}
        try(FileReader reader = new FileReader("Война и мир.txt"))
        {
            char[] buf = new char[256];
            int c;
            while((c = reader.read(buf))>0){
                if(c < 256){
                    buf = Arrays.copyOf(buf, c);
                }
                for(int i=0;i<buf.length;i++) {
                    String strTemp = String.valueOf(buf[i]);
                    for(String l : list){
                        if(strTemp.equalsIgnoreCase(l)){
                            Integer tempInt = map.get(l)+1;
                            map.put(l, tempInt); 
                            break;
                        }
                    }
               }
            } 
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }    
        System.out.println(map);
        
        
        System.out.println(map);
    }

    private static void sorting_map() {
        
    }*/
    
    
    
    static List<String> list_letters = new ArrayList<>(Arrays.asList("А","Б","В","Г","Д","Е", "Ё", "Ж", "З", "И", "Й", "К", "Л", "М", "Н", "О", "П", "Р", "С", "Т", "У", "Ф", "Х", "Ц", "Ч", "Ш", "Щ", "Ъ", "Ы", "Ь", "Э", "Ю", "Я" ));
    static List<Element> list_frequency1 = new ArrayList<>();
    static List<Element> list_frequency_glava1_decod = new ArrayList<>();
    
    static HashMap<String, Elem_bigram> map_of_pairs_of_letters = new LinkedHashMap<>();
    static HashMap<String, Elem_bigram> map_first_chap = new LinkedHashMap<>();
    
    public static void maining(String[] args) {
        
        //Шифруем первую главу
        char[] first_chapter_encoded = shifr_Caesar();
        
        //Частотный анализ по всей книге букв и пар букв
        init();
        frequency_analysis();
        
        //Расшифровываем первую главу с помощью частотного анализа букв
        decoding(first_chapter_encoded);
        
        
        //Расшифровываем первую главу с помощью частотного анализа пар букв
        decoding_pairs_of_letters(first_chapter_encoded);
    }
    
    private static void init() {
        for(int i=0; i<list_letters.size();i++){
            list_frequency1.add(new Element(list_letters.get(i),0));
            list_frequency_glava1_decod.add(new Element(list_letters.get(i),0));
        }
        for(int i=0; i<list_letters.size();i++){
            for(int j=0; j<list_letters.size();j++){
                String name = list_letters.get(i)+list_letters.get(j);
                map_of_pairs_of_letters.put(name ,new Elem_bigram(name ,0));
                map_first_chap.put(name ,new Elem_bigram(name ,0));
            }
        }
    }

    private static String reading_file(String file_name) {
        String string_first_chapter = "";
        try(FileReader reader = new FileReader(file_name)){
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
        return string_first_chapter;
    }
    private static void write_in_file(String file_name,String str) {
        try(FileWriter writer = new FileWriter("Зашифрованная первая глава.txt", false)){//False означает стирать содержимое, если там что-то есть
            writer.write(str);
            writer.flush();
        }
        catch(IOException ex){
             
            System.out.println(ex.getMessage());
        } 
    }
    
    
    
    //Зашифровываем 1 главу
    private static char[] shifr_Caesar() {
        //Читаем первую главу в строку
        int k = 17;
        String string_first_chapter = reading_file("voyna-i-mir-tom-1.txt");
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
        String b = new String(result);
        write_in_file("Зашифрованная первая глава.txt",b);
        return result;
    }
    //Чтение из файла всей книги Война и мир и частотный анализ букв и пар букв
    private static void frequency_analysis() {
        String str = reading_file("Война и мир.txt");
        char[] mass_str = str.toCharArray();
        search_count_letter(mass_str,list_frequency1);
        func(mass_str,map_of_pairs_of_letters);
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
            String strTemp = String.valueOf(first_chapter_encoded[i]);
            /*
            String strTemp;
            if(Character.isLowerCase(first_chapter_encoded[i])) strTemp = String.valueOf(first_chapter_encoded[i]).toUpperCase();
            else strTemp = String.valueOf(first_chapter_encoded[i]);
            if(list_letters.contains(strTemp)){//Если эта буква есть в списке
                if(Character.isLowerCase(first_chapter_encoded[i])) 
                   first_chapter_encoded[i] = search_element_in_list(strTemp).toLowerCase().charAt(0);
                else  first_chapter_encoded[i] = search_element_in_list(strTemp).charAt(0);
            }*/
            for(String l : list_letters){
                if(strTemp.equalsIgnoreCase(l)){//Если эта буква есть в списке
                    //меняю эту букву на букву в поле relation
                    if(Character.isLowerCase(first_chapter_encoded[i])) 
                        first_chapter_encoded[i] = search_element_in_list(l).toLowerCase().charAt(0);
                    else  first_chapter_encoded[i] = search_element_in_list(l).charAt(0);
                    break;
                }
            }
        }
        //сделать из массива char строку
        String b = new String(first_chapter_encoded);
        //записать в файл "расшифрованная первая глава"
        write_in_file("Расшифрованная первая глава.txt",b);
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
    /*private static void printer_map(HashMap<String, Elem_bigram> map){
        for (Map.Entry<String, Elem_bigram> entry : map.entrySet()) {
            System.out.println(entry.getKey()+" : "+entry.getValue().getCount()+ "  "+entry.getValue().getRelation());
        }
    }*/
    
    
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
    private static void func(char[] mass,HashMap<String, Elem_bigram> map) {
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
        List<Elem_bigram> elems_toSort_map1 = new ArrayList<Elem_bigram>(map_of_pairs_of_letters.values());
        Collections.sort(elems_toSort_map1, new Comparator<Elem_bigram>() {
            public int compare(Elem_bigram o1, Elem_bigram o2) {
                return o1.getCount() - o2.getCount();
            }
        });
        List<Elem_bigram> elems_toSort_map2 = new ArrayList<Elem_bigram>(map_first_chap.values());
        Collections.sort(elems_toSort_map2, new Comparator<Elem_bigram>() {
            public int compare(Elem_bigram o1, Elem_bigram o2) {
                return o1.getCount() - o2.getCount();
            }
        });
        //сопоставить 2 map
        for(int o = 0; o<elems_toSort_map1.size();o++){
            //elems_toSort_map1.get(o).setRelation(elems_toSort_map2.get(o).getName());
        }
        for(Elem_bigram el :elems_toSort_map1){
            //map_of_pairs_of_letters.get(el.getName()).setRelation(el.getRelation());
        }
        //printer_map(map_of_pairs_of_letters);
    }

    
}

