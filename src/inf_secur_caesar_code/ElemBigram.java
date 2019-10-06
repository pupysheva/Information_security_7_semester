/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package inf_secur_caesar_code;

/**
 *
 * @author pupys
 */
public class ElemBigram implements Comparable<ElemBigram> {
    String name;
    Integer count;

    public ElemBigram(String name, Integer count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

   

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public int compareTo(ElemBigram el) {
        return (int)(this.count - el.getCount());
    }
}
