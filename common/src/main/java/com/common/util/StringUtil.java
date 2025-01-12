package com.common.util;

import lombok.experimental.UtilityClass;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static java.util.Optional.ofNullable;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;

@UtilityClass
public class StringUtil {

    private final String DEFAULT_STRING_SEPARATOR = ",";
    private final Function<String, String> DEFAULT_STRING_EXTRACTOR = String::trim;


    /**
     * Remove from the given {@code sourceString} all non-numeric characters.
     *
     * @param sourceString
     *    {@link String} to delete all non-numeric characters.
     *
     * @return {@code sourceString} without non-numeric characters.
     */
    public static Optional<String> keepOnlyDigits(final String sourceString) {
        return ofNullable(sourceString)
                .map(s -> s.replaceAll("[\\D]", ""));
    }


    /**
     *    Return a {@link List} splitting the given {@code source} in different parts, using {@code valueExtractor}
     * to know how to do it.
     *
     * @param source
     *    Source {@link String} with the values to extract
     * @param valueExtractor
     *    {@link Function} used to know how to split the provided {@link String}
     *
     * @return {@link List}
     */
    public static <T> List<T> splitFromString(final String source,
                                              final Function<String, T> valueExtractor) {
        return (List)splitFromString(source, valueExtractor, DEFAULT_STRING_SEPARATOR, ArrayList::new);
    }


    /**
     *    Return a {@link Collection} splitting the given {@code source} in different parts, using {@code separator}
     * to know how to split it.
     *
     * @param source
     *    Source {@link String} with the values to extract
     * @param separator
     *    {@link String} used to know how the values are splitted inside {@code source}
     * @param chunkLimit
     *    Maximum number of elements of which the given {@code source} will be splitted
     *
     * @return {@link List}
     */
    public static <T> List<T> splitFromString(final String source,
                                              final String separator,
                                              int chunkLimit) {
        return (List)splitFromString(source, separator, chunkLimit, DEFAULT_STRING_EXTRACTOR, ArrayList::new);
    }


    /**
     *    Return a {@link List} splitting the given {@code source} in different parts, using {@code valueExtractor}
     * to know how to do it.
     *
     * @param source
     *    Source {@link String} with the values to extract
     * @param valueExtractor
     *    {@link Function} used to know how to split the provided {@link String}
     * @param separator
     *    {@link String} used to know how the values are splitted inside {@code source}
     *
     * @return {@link List}
     */
    public static <T> List<T> splitFromString(final String source,
                                              final Function<String, T> valueExtractor,
                                              final String separator) {
        return (List)splitFromString(source, valueExtractor, separator, ArrayList::new);
    }


    /**
     *    Return a {@link Collection} splitting the given {@code source} in different parts, using {@code valueExtractor}
     * to know how to do it.
     *
     * @param source
     *    Source {@link String} with the values to extract
     * @param valueExtractor
     *    {@link Function} used to know how to split the provided {@link String}
     * @param separator
     *    {@link String} used to know how the values are splitted inside {@code source}
     * @param collectionFactory
     *    {@link Supplier} of the {@link Collection} used to store the returned elements.
     *
     * @return {@link Collection}
     */
    public static <T> Collection<T> splitFromString(final String source,
                                                    Function<String, T> valueExtractor,
                                                    final String separator,
                                                    Supplier<Collection<T>> collectionFactory) {
        return splitFromString(source, separator, -1, valueExtractor, collectionFactory);
    }


    /**
     *    Return a {@link Collection} splitting the given {@code source} in different parts, using {@code valueExtractor}
     * to know how to do it.
     *
     * @param source
     *    Source {@link String} with the values to extract
     * @param separator
     *    {@link String} used to know how the values are splitted inside {@code source}
     * @param chunkLimit
     *    Maximum number of elements of which the given {@code source} will be splitted
     * @param valueExtractor
     *    {@link Function} used to know how to split the provided {@link String}
     * @param collectionFactory
     *    {@link Supplier} of the {@link Collection} used to store the returned elements.
     *
     * @return {@link Collection}
     *
     * @throws Exception if there is an splitted part that cannot extracted using {@code valueExtractor}.
     *                   For example:
     *                        source = "1,2,3"
     *                        chunkLimit = 2
     *                        valueExtractor = Integer::parseInt


     *
     *                   Splitted parts will be:
     *                        ["1", "2,3"] => second one could not be converted to an {@link Integer}
     */
    public static <T> Collection<T> splitFromString(final String source,
                                                    final String separator,
                                                    int chunkLimit,
                                                    final Function<String, T> valueExtractor,
                                                    final Supplier<Collection<T>> collectionFactory) {
        return ofNullable(source)
                .map(s -> {
                    if (Objects.isNull(valueExtractor)) {
                        return null;
                    }
                    String[] splittedString =
                            0 >= chunkLimit
                                    ? s.split(null == separator ? DEFAULT_STRING_SEPARATOR : separator)
                                    : s.split(null == separator ? DEFAULT_STRING_SEPARATOR : separator, chunkLimit);

                    Stream<T> valueExtractedStream = Stream.of(splittedString)
                            .map(valueExtractor);

                    return Objects.isNull(collectionFactory)
                            ? valueExtractedStream.collect(toList())
                            : valueExtractedStream.collect(toCollection(collectionFactory));
                })
                .orElse(Objects.isNull(collectionFactory)
                        ? asList()
                        : collectionFactory.get());
    }

}
