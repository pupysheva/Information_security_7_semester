/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package RSA;

/**
 *
 * @author pupys
 */
public class Person {
    private PublicKey pk;
    private SecretKey sk;
    private PublicKey otherPersonPublicKey;

    public Person(PublicKey pk, SecretKey sk) {
        this.pk = pk;
        this.sk = sk;
    }

    public PublicKey getPk() {
        return pk;
    }

    public void setPk(PublicKey pk) {
        this.pk = pk;
    }

    public SecretKey getSk() {
        return sk;
    }

    public void setSk(SecretKey sk) {
        this.sk = sk;
    }

    public PublicKey getOtherPersonPublicKey() {
        return otherPersonPublicKey;
    }

    public void setOtherPersonPublicKey(PublicKey otherPersonPublicKey) {
        this.otherPersonPublicKey = otherPersonPublicKey;
    }
}
