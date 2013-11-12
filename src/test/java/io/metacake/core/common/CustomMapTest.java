package io.metacake.core.common;

import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.concurrent.Callable;

import static junit.framework.Assert.*;
import static org.junit.Assert.fail;

/**
 * @author florence
 * @author rpless
 */
public class CustomMapTest {
    CustomizableMap<Integer,String> map;

    @Test
    public void mapIteratesOverEntrySet(){
        List<Integer> elements = new LinkedList<>();

        Random r = new Random();
        for(int i = 0; i < 10; i++){
            Integer next = r.nextInt();
            elements.add(next);
            map.put(next,next.toString());
        }

        for(Map.Entry<Integer,String> e : map){
            assertNotNull(elements.remove(e.getKey()));
            assertEquals(e.getKey().toString(),e.getValue());
        }

        assertTrue(elements.isEmpty());
    }
}
