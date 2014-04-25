package io.metacake.core.process;

import io.metacake.core.common.CustomizableMap;
import io.metacake.core.common.Symbol;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * Name for Buckets, used to reference them via the {@link RecognizerBucketName}.
 */
public final class RecognizerBucketName<T extends ActionRecognizer> extends Symbol {
    private CustomizableMap<ActionRecognizerName,T> bucket = new CustomizableMap<>(new HashMap<>());

    public RecognizerBucketName() {}

    public RecognizerBucketName(String name) {
        super(name);
    }

    /**
     * @return the contents of the bucket this references
     */
    protected CustomizableMap<ActionRecognizerName,T> get() {
        return bucket;
    }

    /**
     * SYSTEM CALL ONLY
     * add the recognizer to this bucket
     * @param e the recognizer
     */
    public void register(T e) {
        bucket.put(e.getName(), e);
    }

    /**
     * SYSTEM CALL ONLY
     * empty the bucket completely (deregister)
     */
    public void clear() {
       bucket = new CustomizableMap<>(new HashMap<>());
    }
}
