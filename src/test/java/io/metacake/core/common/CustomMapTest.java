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

    @Before
    public void setup(){
        this.map = new CustomizableMap<>(new HashMap<Integer, String>());
    }

    @Test
    public void testGivingOptionalDefaultReturnReturns(){
        Callable<String> res = new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "irrelevant";
            }
        };
        assertEquals(map.get(0,res), "irrelevant");

        map.setOnMissing(res);
        assertEquals(map.get(0), "irrelevant");
    }

    @Test
    public void testDefaultMissingThrowsCorrectError(){
        try {
            map.get(0);
            fail("map.get did not throw exception");
        } catch (RuntimeException e){
            assertTrue("Wrong exception thrown",
                    e.getCause() instanceof NoSuchElementException);
        }
    }

    @Test
    public void canGetExistingKey(){
        map.put(0,"data");
        assertEquals("data",map.get(0));
    }

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
