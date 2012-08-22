package kz.bee.nedb.passport.classes;

import java.util.HashMap;

public class HashTable<K1, K2, V> {
    private HashMap<K1, HashMap<K2, V>> hashMap = new HashMap<K1, HashMap<K2, V>>();
    
    public void put(K1 key1, K2 key2, V value) {
        HashMap<K2, V> innerHashMap = hashMap.get(key1);
        
        if (innerHashMap == null) {
            innerHashMap = new HashMap<K2, V>();
            hashMap.put(key1, innerHashMap);
        }
        
        innerHashMap.put(key2, value);
    }
    
    public V get(K1 key1, K2 key2) {
        HashMap<K2, V> innerHashMap = hashMap.get(key1);
        if (innerHashMap == null) {
            return null;
        } else {
            return innerHashMap.get(key2);
        }
    }
}