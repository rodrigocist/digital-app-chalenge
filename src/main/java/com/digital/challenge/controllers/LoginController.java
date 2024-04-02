package com.digital.challenge.controllers;

import com.digital.challenge.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class LoginController {

    @Value("${security.api.user}")
    private String user;

    @Value("${security.api.password}")
    private String password;

    @PostMapping("login")
    public UserDto login(@RequestParam("user") String username, @RequestParam("password") String pwd) {

        UserDto userDto = new UserDto();
        if(user.equals(username) && password.equals(pwd)){
            String token = getJWTToken(username);
            userDto.setUser(username);
            userDto.setToken(token);
            return userDto;
        }
        userDto.setToken("Unauthorized");
        return userDto;
    }

    private String getJWTToken(String username) {
        String secretKey = "mySecretKey";
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        String token = Jwts
                .builder()
                .setId("softtekJWT")
                .setSubject(username)
                .claim("authorities",
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 600000))//expired every 10 minutes
                .signWith(SignatureAlgorithm.HS512,
                        secretKey.getBytes()).compact();
        return "Bearer " + token;
    }

}
