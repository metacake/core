package io.metacake.core.process;

import io.metacake.core.common.Symbol;

import java.util.Collection;
import java.util.LinkedList;

/**
 * Name for Buckets, used to reference them via the {@link RecognizerBucketName}
 */
public final class RecognizerBucketName<T extends ActionRecognizer> extends Symbol {
    private Collection<T> bucket = new LinkedList<>();

    public RecognizerBucketName() {}

    public RecognizerBucketName(String name) {
        super(name);
    }

    /**
     * @return the contents of the bucket this references
     */
    protected Collection<T> get() {
        return bucket;
    }

    /**
     * SYSTEM CALL ONLY
     * add the recognizer to this bucket
     * @param e the recognizer
     */
    public void register(T e) {
        bucket.add(e);
    }
}
