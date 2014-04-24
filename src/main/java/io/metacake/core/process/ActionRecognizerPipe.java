package io.metacake.core.process;

import java.util.Collection;

/**
 * This class provides access to the current set of active recognizers.
 */
public class ActionRecognizerPipe {
    /**
     * get the bucket with the given name, and empty it
     * @param n the name of the bucket
     * @param <T> the type of the recognizers to be returned
     * @return the recognizers emptied from the bucket
     */
    public <T extends ActionRecognizer> Collection<T> emptyBucket(RecognizerBucketName<T> n) {
       return n.get();
    }

    /**
     * register the recognizer bucket
     * @param bucketName the bucket
     * @param <T> the type of the recognizer
     */
    protected <T extends ActionRecognizer> void register(RecognizerBucketName<T> bucketName) {
        //TODO
    }
}
