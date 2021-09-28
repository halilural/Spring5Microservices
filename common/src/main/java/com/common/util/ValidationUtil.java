package com.common.util;

import com.common.validation.Invalid;
import com.common.validation.Valid;
import com.common.validation.Validation;
import lombok.experimental.UtilityClass;
import org.springframework.util.ObjectUtils;

import java.util.function.Supplier;

@UtilityClass
public class ValidationUtil {

    /**
     * Merges the given {@link Validation} in a result one that will be:
     *
     *   1. {@link Valid} instance with all given {@code validations} are {@link Valid} ones or such parameters is {@code null} or empty.
     *   2. {@link Invalid} instance if there is at least one {@link Invalid} in the given {@code validations}. In this case, errors of
     *      all provided {@link Invalid}s will be included in the result.
     *
     * @param validations
     *    {@link Validation} instances to combine
     *
     * @return {@link Validation}
     */
    public static <T> Validation<T> combine(Validation<? super T>... validations) {
        Validation<T> result = Valid.empty();
        if (!ObjectUtils.isEmpty(validations)) {
            for (int i = 0; i < validations.length; i++) {
                result = (Validation<T>) result.ap(validations[i]);
            }
        }
        return result;
    }


    /**
     *    Checks the given {@link Supplier} of {@link Validation}, returning a {@link Valid} instance if no {@link Invalid}
     * {@link Supplier} was given or the first {@link Invalid} one.
     *
     * @param suppliers
     *    {@link Supplier} of {@link Validation} instances to verify
     *
     * @return {@link Validation}
     */
    public static <T> Validation<T> getFirstInvalid(Supplier<Validation<? super T>>... suppliers) {
        Validation<T> result = Valid.empty();
        if (!ObjectUtils.isEmpty(suppliers)) {
            for (int i = 0; i < suppliers.length; i++) {
                result = (Validation<T>) result.ap(suppliers[i].get());
                if (!result.isValid()) {
                    return result;
                }
            }
        }
        return result;
    }

}
