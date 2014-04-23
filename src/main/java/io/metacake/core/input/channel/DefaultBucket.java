package io.metacake.core.input.channel;

import io.metacake.core.process.ActionRecognizer;

import java.util.Collection;
import java.util.LinkedList;

/**
 * This is the default implementation for an action recognizer bucket. In reality, {@link io.metacake.core.input.channel.RecognizerBucketName}
 * are the actual buckets that store active recognizers. This class is meant to be used by input systems to implement these buckets. By default
 * recognizers are returned in chronological order, with the oldest first
 */
public class DefaultBucket<T extends ActionRecognizer> extends RecognizerBucketName<T> {
    protected Collection<T> buffer = new LinkedList<>();

    /**
     * Add to the bucket
     * @param e the recognizer to add
     */
    protected synchronized void add(T e) {
        buffer.add(e);
    }

    @Override
    protected synchronized Collection<T> empty() {
        Collection<T> ret = buffer;
        buffer = new LinkedList<>();
        return ret;
    }
}
