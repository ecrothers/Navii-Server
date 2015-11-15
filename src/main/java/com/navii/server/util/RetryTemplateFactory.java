package com.navii.server.util;

import org.springframework.retry.RetryPolicy;
import org.springframework.retry.backoff.ExponentialBackOffPolicy;
import org.springframework.retry.policy.ExceptionClassifierRetryPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.client.HttpServerErrorException;

import com.google.common.collect.ImmutableMap;

/**
 * Created by JMtorii on 15-10-16.
 */
public class RetryTemplateFactory {

    /**
     * Creates and configures a stateful {@link RetryTemplate} that can be used to automatically retry operations with a configurable back-off policy.
     * This pattern is particularly useful for retrying operations that might not succeed on the first try, like HTTP requests to external services.
     *
     * <p>Here's an example of how to use this object:</p>
     * <pre>
     * <code>
     * RetryTemplate retryTemplate = RetryTemplateFactory.createRetryTemplate(retryMaxAttempts, retryInitialInterval, retryMultiplier);
     * String someResponseObject = null;
     *
     * try {
     *  // RetryCallback is a generic of the same type as someResponseObject. This is also the type that doWithRetry is expected to return.
     *  someResponseObject = retryTemplate.execute(new RetryCallback&lt;String&gt;() {
     *      // every time we try to do the operation, doWithRetry will be called
     *      &#64;Override
     *      public String doWithRetry(RetryContext context) {
     *          // this is where you put the code that might fail. if it throws an exception, doWithRetry will be called
     *          // again according to the retryTemplate's configuration. if we run out of retries, the exception will bubble
     *          // up and must be caught externally.
     *          return doSomethingThatMightNotWork(someParameters);
     *      }
     *  });
     * } catch (Exception e) {
     *  // this exception will only be thrown if doWithRetry is called more than retryMaxAttempts times
     *  throw new InvalidDataException("An exception occurred while trying to doSomethingThatMightNotWork. See inner exception for details.", e);
     * }
     *
     * // if the operation succeeds, we can return the object that we got back from doSomethingThatMightNotWork
     * return someResponseObject;
     * </code>
     * </pre>
     *
     * @param maxAttempts the maximum number of times to retry the operation.
     * @param initialInterval the amount of time in ms to wait before retrying the operation after the first failure.
     * @param multiplier a multiplier that will will be applied to the initialInterval after every subsequent retry operation.
     * @return a configured {@link RetryTemplate} object
     */
    public static RetryTemplate createRetryTemplate(int maxAttempts, int initialInterval, int multiplier) {
        RetryTemplate retryTemplate = new RetryTemplate();
        SimpleRetryPolicy retryPolicy = new SimpleRetryPolicy();
        retryPolicy.setMaxAttempts(maxAttempts);
        ExceptionClassifierRetryPolicy exceptionRetryPolicy = new ExceptionClassifierRetryPolicy();
        exceptionRetryPolicy.setPolicyMap(ImmutableMap.<Class<? extends Throwable>, RetryPolicy>builder().put(HttpServerErrorException.class, retryPolicy).build());
        retryTemplate.setRetryPolicy(retryPolicy);
        ExponentialBackOffPolicy backOffPolicy = new ExponentialBackOffPolicy();
        backOffPolicy.setInitialInterval(initialInterval);
        backOffPolicy.setMultiplier(multiplier);
        retryTemplate.setBackOffPolicy(backOffPolicy);
        return retryTemplate;
    }
}
