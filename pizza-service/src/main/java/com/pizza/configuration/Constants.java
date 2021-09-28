package com.pizza.configuration;

/**
 * Global values used in different part of the application
 */
public class Constants {

    // Global cache configuration
    public static final String CACHE_INSTANCE_NAME = "PizzaServiceCacheInstance";

    // Database schema on which the entities have been included
    public static final String DATABASE_SCHEMA = "eat";

    // Path of the folders in the application
    public static final class PATH {
        public static final String REPOSITORY = "com.pizza.repository";

        // External path
        public static final class EXTERNAL {
            public static final String COMMON = "com.common";
        }
    }

    // Mapping used to match the result of some custom queries
    public static final class SQL_RESULT_MAPPING {
        public static final String PIZZA_INGREDIENTS = "PizzaIngredientsMapping";
    }

    // Existing roles to manage the authorizations
    public static final String ROLE_ADMIN = "ADMIN";
    public static final String ROLE_USER = "USER";

    // Token configuration
    public static final String TOKEN_PREFIX = "Bearer ";

    // Default charset for plain text
    public static final String TEXT_PLAIN_UTF8_VALUE = "text/plain;charset=UTF-8";

}
