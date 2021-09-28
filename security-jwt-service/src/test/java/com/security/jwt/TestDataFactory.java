package com.security.jwt;

import com.security.jwt.configuration.Constants;
import com.security.jwt.dto.AuthenticationRequestDto;
import com.security.jwt.dto.RawAuthenticationInformationDto;
import com.security.jwt.application.spring5microservices.enums.RoleEnum;
import com.security.jwt.enums.SignatureAlgorithmEnum;
import com.security.jwt.model.JwtClientDetails;
import com.security.jwt.application.spring5microservices.model.Role;
import com.security.jwt.application.spring5microservices.model.User;
import com.common.dto.AuthenticationInformationDto;
import com.common.dto.UsernameAuthoritiesDto;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.asList;
import static com.security.jwt.enums.TokenKeyEnum.AUTHORITIES;
import static com.security.jwt.enums.TokenKeyEnum.NAME;
import static com.security.jwt.enums.TokenKeyEnum.USERNAME;

@UtilityClass
public class TestDataFactory {

    public static AuthenticationInformationDto buildDefaultAuthenticationInformation() {
        return AuthenticationInformationDto.builder()
                .accessToken("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InVzZXJuYW1lIHZhbHVlIiwicm9sZXMiOlsiYWRtaW4iLCJ1c2VyIl0s"
                           + "Im5hbWUiOiJuYW1lIHZhbHVlIiwianRpIjoidW5pcXVlIGlkZW50aWZpZXIiLCJhZ2UiOjIzLCJpYXQiOjUwMDAwMDAwM"
                           + "DAsImV4cCI6NTAwMDAwMDAwMH0.pKoZm2qHaA1zOw9MPCT_1ho2WtDmcYcn2eNr73r_CWg")
                .tokenType("Bearer")
                .refreshToken("eyJhbGciOiJIUzI1NiJ9.eyJ1c2VybmFtZSI6InVzZXJuYW1lIHZhbHVlIiwicm9sZXMiOlsiYWRtaW4iLCJ1c2VyIl0sIm5hbWU"
                            + "iOiJuYW1lIHZhbHVlIiwianRpIjoidW5pcXVlIGlkZW50aWZpZXIiLCJhdGkiOiJyZWZyZXNoIHRva2VuIGlkZW50aWZpZXIiLCJ"
                            + "hZ2UiOjIzLCJpYXQiOjUwMDAwMDAwMDAsImV4cCI6NTAwMDAwMDAwMH0.vwelPSmcasRZItPfBZ_wwqazR_74U0kHF-d_b7DKO3g")
                .expiresIn(250)
                .jwtId("unique identifier")
                .scope(null)
                .additionalInfo(new HashMap<String, Object>() {{
                    put(NAME.getKey(), "name value");
                }})
                .build();
    }


    public static AuthenticationRequestDto buildAuthenticationRequest(String username, String password) {
        return new AuthenticationRequestDto(username, password);
    }


    public static JwtClientDetails buildDefaultJwtClientDetails(String clientId) {
        return JwtClientDetails.builder()
                .clientId(clientId)
                .signatureAlgorithm(SignatureAlgorithmEnum.HS256)
                .signatureSecret(Constants.CIPHER_SECRET_PREFIX + "secretKey_ForTestingPurpose@12345#")
                .accessTokenValidity(250)
                .refreshTokenValidity(500)
                .tokenType("Bearer")
                .build();
    }


    public static RawAuthenticationInformationDto buildDefaultRawAuthenticationInformation() {
        return RawAuthenticationInformationDto.builder()
                .accessTokenInformation(new HashMap<String, Object>() {{
                    put(USERNAME.getKey(), "username value");
                    put(AUTHORITIES.getKey(), asList("admin", "user"));
                }})
                .refreshTokenInformation(new HashMap<String, Object>() {{
                    put(USERNAME.getKey(), "username value");
                }})
                .additionalTokenInformation(new HashMap<String, Object>() {{
                    put(NAME.getKey(), "name value");
                }})
                .build();
    }


    public static User buildDefaultUser() {
        return User.builder()
                .id(1L)
                .name("name value")
                .username("username value")
                .password("password value")
                .active(true)
                .roles(new HashSet<>(asList(
                        Role.builder().id(1).name(RoleEnum.ADMIN).build(),
                        Role.builder().id(2).name(RoleEnum.USER).build())))
                .build();
    }


    public static UsernameAuthoritiesDto buildUsernameAuthorities(String username, Set<String> authorities, Map<String, Object> additionalInfo) {
        return UsernameAuthoritiesDto.builder()
                .username(username)
                .authorities(authorities)
                .additionalInfo(additionalInfo)
                .build();
    }

}
