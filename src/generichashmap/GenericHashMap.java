/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generichashmap;

import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author admin
 */
public class GenericHashMap <T, V> {

   private class KeyValue <T, V> {
        private T key;
        private V val;
        public KeyValue <T, V> next = null;
        
        KeyValue(T k, V v, KeyValue<T,V> n) {
            key = k;
            val = v;
            next = n;
        }
        
        public T getKey() {
            return key;
        }
        
        public V getValue() {
            return val;
        }
    }
    
    private final List<KeyValue<T,V>> map = new ArrayList<>();
    private final int BUCKET_SIZE = 10;
    
    public void init() {
        for(int i = 0; i < BUCKET_SIZE; i++) {
            map.add(null);
        }
    }
    
    public GenericHashMap() {
        init();
    }
    
    private int getHash(T key) {
        int hash = key.hashCode();
        hash = abs(hash % BUCKET_SIZE);
        return hash;
    }
    
    public Boolean add(T key, V val) {
        int hash = getHash(key);
        
        try {
            if(map.get(hash) == null) {
                map.set(hash, new KeyValue(key, val, null));
            } else {
                KeyValue temp = new KeyValue(key, val, map.get(hash));
                map.set(hash, temp);
            }
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
    
    public V get(T key) throws Exception {
        int hash = getHash(key);
        
        if(map.get(hash) != null) {
            KeyValue temp = map.get(hash);
            while(temp != null) {
                if(temp.getKey().equals(key)) {
                    return (V) temp.getValue();
                }
                temp = temp.next;
            }
        }
        
        throw new Exception("Specified key doesn't exists"); 
    }

    public Boolean remove(T key) throws Exception {
        int hash = getHash(key);
        
        try {        
            if(map.get(hash) != null) {
                KeyValue temp = map.get(hash);
                KeyValue parent = null;

                while(temp != null) {
                    if(temp.getKey().equals(key)) {
                        if(parent == null) {
                            map.set(hash, temp.next);
                        } else {
                            parent.next = temp.next;
                            // delete temp
                            temp.next = null;
                        }
                        return true;
                    }
                    temp = temp.next;
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        return false;
    }

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        GenericHashMap<Integer, String> abc = new GenericHashMap<>();
        
        try {        
            abc.add(888, "def");
            abc.add(4553, "askjfhdjf");

            System.out.println(abc.get(888));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    
}
