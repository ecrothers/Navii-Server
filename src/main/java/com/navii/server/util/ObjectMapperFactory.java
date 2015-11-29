package com.navii.server.util;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;

/**
 * Static factory of Jackson {@link ObjectMapper} so that it can be used in either Spring DI or in non-DI classes (e.g. unit tests).
 *
 * Created by JMtorii on 15-10-16.
 */
public class ObjectMapperFactory {

    /**
     * Creates the {@link ObjectMapper} instance for the entire project.
     * Register all modules and set all configuration here.
     * @return a configured {@link ObjectMapper}
     */
    public static ObjectMapper createMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new AfterburnerModule());
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return mapper;
    }
}
