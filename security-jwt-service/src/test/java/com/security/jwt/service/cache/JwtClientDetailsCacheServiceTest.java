package com.security.jwt.service.cache;

import com.security.jwt.configuration.cache.CacheConfiguration;
import com.security.jwt.model.JwtClientDetails;
import com.common.service.CacheService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.stream.Stream;

import static com.security.jwt.TestDataFactory.buildDefaultJwtClientDetails;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = JwtClientDetailsCacheService.class)
public class JwtClientDetailsCacheServiceTest {

    @MockBean
    private CacheConfiguration mockCacheConfiguration;

    @MockBean
    private CacheService mockCacheService;

    @Autowired
    private JwtClientDetailsCacheService jwtClientDetailsCacheService;

    @BeforeEach
    public void init() {
        when(mockCacheConfiguration.getJwtConfigurationCacheName()).thenReturn("TestCache");
    }


    static Stream<Arguments> clearTestCases() {
        return Stream.of(
                //@formatter:off
                //            cacheServiceResult,   expectedResult
                Arguments.of( false,                false ),
                Arguments.of( true,                 true )
        ); //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("clearTestCases")
    @DisplayName("clear: test cases")
    public void clear_testCases(boolean cacheServiceResult, boolean expectedResult) {
        // When
        when(mockCacheService.clear(anyString())).thenReturn(cacheServiceResult);
        boolean operationResult = jwtClientDetailsCacheService.clear();

        // Then
        assertEquals(expectedResult, operationResult);
    }


    static Stream<Arguments> containsTestCases() {
        String clientId = "clientId";
        return Stream.of(
                //@formatter:off
                //            clientId,   cacheServiceResult,   expectedResult
                Arguments.of( null,       false,                false ),
                Arguments.of( clientId,   false,                false ),
                Arguments.of( clientId,   true,                 true )
        ); //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("containsTestCases")
    @DisplayName("contains: test cases")
    public void contains_testCases(String clientId, boolean cacheServiceResult, boolean expectedResult) {
        // When
        when(mockCacheService.contains(anyString(), eq(clientId))).thenReturn(cacheServiceResult);
        boolean operationResult = jwtClientDetailsCacheService.contains(clientId);

        // Then
        assertEquals(expectedResult, operationResult);
    }


    static Stream<Arguments> getTestCases() {
        String clientId = "clientId";
        Optional<JwtClientDetails> cacheServiceResult = of(buildDefaultJwtClientDetails(clientId));
        return Stream.of(
                //@formatter:off
                //            clientId,   cacheServiceResult,   expectedResult
                Arguments.of( null,       empty(),              empty() ),
                Arguments.of( clientId,   empty(),              empty() ),
                Arguments.of( clientId,   cacheServiceResult,   cacheServiceResult )
        ); //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("getTestCases")
    @DisplayName("get: test cases")
    public void get_testCases(String clientId, Optional<JwtClientDetails> cacheServiceResult, Optional<JwtClientDetails> expectedResult) {
        // When
        when(mockCacheService.get(anyString(), eq(clientId))).thenReturn((Optional)cacheServiceResult);
        Optional<JwtClientDetails> operationResult = jwtClientDetailsCacheService.get(clientId);

        // Then
        assertEquals(expectedResult, operationResult);
    }


    static Stream<Arguments> putTestCases() {
        String clientId = "clientId";
        JwtClientDetails jwtClientDetails = buildDefaultJwtClientDetails(clientId);
        return Stream.of(
                //@formatter:off
                //            clientId,   jwtClientDetails,   cacheServiceResult,   expectedResult
                Arguments.of( null,       null,               false,                false ),
                Arguments.of( clientId,   null,               false,                false ),
                Arguments.of( clientId,   null,               true,                 true ),
                Arguments.of( clientId,   jwtClientDetails,   true,                 true )
        ); //@formatter:on
    }

    @ParameterizedTest
    @MethodSource("putTestCases")
    @DisplayName("put: test cases")
    public void put_testCases(String clientId, JwtClientDetails jwtClientDetails, boolean cacheServiceResult, boolean expectedResult) {
        // When
        when(mockCacheService.put(anyString(), eq(clientId), eq(jwtClientDetails))).thenReturn(cacheServiceResult);
        boolean operationResult = jwtClientDetailsCacheService.put(clientId, jwtClientDetails);

        // Then
        assertEquals(expectedResult, operationResult);
    }

}
