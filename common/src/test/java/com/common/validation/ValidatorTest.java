package com.common.validation;

import com.common.PizzaDto;
import com.common.util.ValidationUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidatorTest {

    static Stream<Arguments> validateWithCombineTestCases() {
        PizzaDto validPizza = new PizzaDto("Carbonara", 15d);
        PizzaDto invalidPizzaName = new PizzaDto("12#2", 11d);
        PizzaDto invalidPizzaCost = new PizzaDto("Margherita", -5d);
        PizzaDto invalidPizza = new PizzaDto("564", -2d);

        Validation<PizzaDto> validValidation = Validation.valid(validPizza);
        Validation<PizzaDto> invalidPizzaNameValidation = Validation.invalid(asList("Name contains invalid characters: '12#2'"));
        Validation<PizzaDto> invalidPizzaCostValidation = Validation.invalid(asList("Cost must be at least 0"));
        Validation<PizzaDto> invalidPizzaValidation = Validation.invalid(asList("Name contains invalid characters: '564'", "Cost must be at least 0"));
        return Stream.of(
                //@formatter:off
                //            objectToVerifyInstance,   expectedResult
                Arguments.of( validPizza,               validValidation ),
                Arguments.of( invalidPizzaName,         invalidPizzaNameValidation ),
                Arguments.of( invalidPizzaCost,         invalidPizzaCostValidation ),
                Arguments.of( invalidPizza,             invalidPizzaValidation )
        ); //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("validateWithCombineTestCases")
    @DisplayName("validate: with combine test cases")
    public <T> void validateWithCombine_testCases(PizzaDto objectToVerifyInstance,
                                                  Validation<T> expectedResult) {
        PizzaDtoValidatorCombine validator = new PizzaDtoValidatorCombine();
        assertEquals(expectedResult, validator.validate(objectToVerifyInstance));
    }


    static Stream<Arguments> validateWithGetFirstInvalidTestCases() {
        PizzaDto validPizza = new PizzaDto("Carbonara", 15d);
        PizzaDto invalidPizzaName = new PizzaDto("12#2", 11d);
        PizzaDto invalidPizzaCost = new PizzaDto("Margherita", -5d);
        PizzaDto invalidPizza = new PizzaDto("564", -2d);

        Validation<PizzaDto> validValidation = Validation.valid(validPizza);
        Validation<PizzaDto> invalidPizzaNameValidation = Validation.invalid(asList("Name contains invalid characters: '12#2'"));
        Validation<PizzaDto> invalidPizzaCostValidation = Validation.invalid(asList("Cost must be at least 0"));
        Validation<PizzaDto> invalidPizzaValidation = Validation.invalid(asList("Name contains invalid characters: '564'"));
        return Stream.of(
                //@formatter:off
                //            objectToVerifyInstance,   expectedResult
                Arguments.of( validPizza,               validValidation ),
                Arguments.of( invalidPizzaName,         invalidPizzaNameValidation ),
                Arguments.of( invalidPizzaCost,         invalidPizzaCostValidation ),
                Arguments.of( invalidPizza,             invalidPizzaValidation )
        ); //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("validateWithGetFirstInvalidTestCases")
    @DisplayName("validate: with getFirstInvalid test cases")
    public <T> void validateWithGetFirstInvalid_testCases(PizzaDto objectToVerifyInstance,
                                                          Validation<T> expectedResult) {
        PizzaDtoValidatorGetFirstInvalid validator = new PizzaDtoValidatorGetFirstInvalid();
        assertEquals(expectedResult, validator.validate(objectToVerifyInstance));
    }

}


class PizzaDtoValidatorCombine implements Validator<PizzaDto> {

    private static final String VALID_NAME_CHARS = "[a-zA-Z ]";
    private static final int MIN_COST = 0;

    @Override
    public Validation<PizzaDto> validate(PizzaDto p) {
        return ValidationUtil.combine(validateName(p), validateAge(p));
    }

    private Validation<PizzaDto> validateName(PizzaDto p) {
        final String onlyValidCharacters = p.getName().replaceAll(VALID_NAME_CHARS, "");
        return onlyValidCharacters.isEmpty()
                ? Validation.valid(p)
                : Validation.invalid(asList("Name contains invalid characters: '" + p.getName() + "'"));
    }

    private Validation<PizzaDto> validateAge(PizzaDto p) {
        return p.getCost() >= MIN_COST
                ? Validation.valid(p)
                : Validation.invalid(asList("Cost must be at least " + MIN_COST));
    }

}


class PizzaDtoValidatorGetFirstInvalid implements Validator<PizzaDto> {

    private static final String VALID_NAME_CHARS = "[a-zA-Z ]";
    private static final int MIN_COST = 0;

    @Override
    public Validation<PizzaDto> validate(PizzaDto p) {
        return ValidationUtil.getFirstInvalid(() -> validateName(p), () -> validateCost(p));
    }

    private Validation<PizzaDto> validateName(PizzaDto p) {
        final String onlyValidCharacters = p.getName().replaceAll(VALID_NAME_CHARS, "");
        return onlyValidCharacters.isEmpty()
                ? Validation.valid(p)
                : Validation.invalid(asList("Name contains invalid characters: '" + p.getName() + "'"));
    }

    private Validation<PizzaDto> validateCost(PizzaDto p) {
        return p.getCost() >= MIN_COST
                ? Validation.valid(p)
                : Validation.invalid(asList("Cost must be at least " + MIN_COST));
    }

}
