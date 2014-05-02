package io.metacake.core.process;

import io.metacake.core.common.CustomizableMap;
import io.metacake.core.common.Symbol;

import java.util.HashMap;

/**
 * Name for Buckets, used to reference them via the {@link RecognizerBucketName}.
 *
 * @author florence
 * @author rpless
 */
public final class RecognizerBucketName<T extends ActionRecognizer> extends Symbol {
    private CustomizableMap<ActionRecognizerName, T> bucket = new CustomizableMap<>(new HashMap<>());

    public RecognizerBucketName() {}

    public RecognizerBucketName(String name) {
        super(name);
    }

    /**
     * @return the contents of the bucket this references
     */
    protected CustomizableMap<ActionRecognizerName, T> get() {
        return bucket;
    }

    /**
     * This is an internal library method. Do not use it.
     * add the recognizer to this bucket
     * @param e the recognizer
     */
    public void register(T e) {
        bucket.put(e.getName(), e);
    }

    /**
     * This is an internal library method. Do not use it.
     * empty the bucket completely (deregister)
     */
    public void clear() {
       bucket = new CustomizableMap<>(new HashMap<>());
    }
}
