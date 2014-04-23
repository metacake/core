package io.metacake.core.input.channel;

import io.metacake.core.common.Symbol;
import io.metacake.core.process.ActionRecognizer;

import java.util.Collection;

/**
 * Name for Buckets, used to reference them via the {@link io.metacake.core.input.channel.RecognizerBucketName}
 */
public abstract class RecognizerBucketName<T extends ActionRecognizer> extends Symbol {
    /**
     * Only called by {@link io.metacake.core.input.channel.ActionRecognizerPipe}
     * @return the contents of the bucket this references
     */
    protected abstract Collection<T> empty();
}
