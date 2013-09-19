package io.metacake.core.common;

import java.util.*;
import java.util.concurrent.Callable;

/**
 * This class acts as a decorator over a Map. It allows for custom error callbacks
 * for when a key it not found on get, and for iterating over the entry set.
 * The default callback throws a {@link NoSuchElementException}
 * @author florence
 * @author rpless
 */
public class CustomizableMap<K,V> implements Map<K,V>, Iterable<Map.Entry<K,V>> {
    private Map<K,V> theMap;
    private Callable<V> onMissing = () -> { throw new NoSuchElementException(); };

    public CustomizableMap(Map<K, V> theMap) {
        this.theMap = theMap;
    }

    /**
     * Change the default on missing callable
     * @param newMissing The {@link Callable} that should be called when a key does not map to a value.
     */
    public void setOnMissing(Callable<V> newMissing){
        this.onMissing = newMissing;
    }

    // updates methods

    @Override
    /**
     * Like the other get, but uses the default callback
     */
    public V get(Object key) {
       return this.get(key,onMissing);
    }

    /**
     * Get the values attached to that key
     * @param key the Key (Really wish its type was K not Object)
     * @param missing Return value from this callback if key not found
     * @return the value, or the result of {@code missing.call()}.
     * This method will throw a {@link RuntimeException} if an error occurs while calling the missing callback. The
     * {@link RuntimeException} will wrap the exception thrown by the callable.
     */
    public V get(Object key, Callable<V> missing) {
        V res = theMap.get(key);
        if(res != null) {
            return res;
        } else {
            try {
                return missing.call();
            } catch (Exception e) {
                // Wrap in runtime exception so that the original get method's signature is not violated
                throw new RuntimeException(e);
            }
        }
    }

    // delegated methods

    @Override
    public int hashCode() {
        return theMap.hashCode();
    }

    @Override
    public int size() {
        return theMap.size();
    }

    @Override
    public boolean isEmpty() {
        return theMap.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return theMap.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return theMap.containsValue(value);
    }

    public V put(K key, V value) {
        return theMap.put(key, value);
    }

    @Override
    public V remove(Object key) {
        return theMap.remove(key);
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        theMap.putAll(m);
    }

    @Override
    public void clear() {
        theMap.clear();
    }

    @Override
    public Set<K> keySet() {
        return theMap.keySet();
    }

    @Override
    public Collection<V> values() {
        return theMap.values();
    }

    @Override
    public Set<Entry<K,V>> entrySet() {
        return theMap.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        return theMap.equals(o);
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return theMap.entrySet().iterator();
    }
}
