package io.metacake.core.process;

import io.metacake.core.common.CustomizableMap;

import java.util.Collection;
import java.util.HashSet;

/**
 * This class provides access to the current set of {@link io.metacake.core.process.ActionRecognizer}s.
 *
 * @author florence
 * @author rpless
 */
public class ActionRecognizerPipe {
    private Collection<RecognizerBucketName> registry = new HashSet<>();
    /**
     * get the bucket with the given name, and empty it
     * @param n the name of the bucket
     * @param <T> the type of the recognizers to be returned
     * @return the recognizers emptied from the bucket
     */
    public <T extends ActionRecognizer> CustomizableMap<ActionRecognizerName, T> emptyBucket(RecognizerBucketName<T> n) {
       return n.get();
    }

    /**
     * register the recognizer bucket
     * @param bucketName the bucket
     * @param <T> the type of the recognizer
     */
    protected <T extends ActionRecognizer> void register(RecognizerBucketName<T> bucketName) {
        registry.add(bucketName);
    }

    /**
     * Clear all of the {@link io.metacake.core.process.RecognizerBucketName}s registered in this pipe.
     */
    protected void clear() {
        registry.forEach(RecognizerBucketName::clear);
    }
}
