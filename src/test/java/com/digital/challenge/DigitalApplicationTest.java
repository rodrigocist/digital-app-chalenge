package com.digital.challenge;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = DigitalApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
@TestPropertySource("/application-test.properties")
public class DigitalApplicationTest {


    @LocalServerPort
    int randomServerPort;

    @Value("${security.app.user}")
    private String user;

    @Value("${security.app.password}")
    private String password;


    @Test
    public void givenAnEmptyUserAndPassowrd_whenPostSecureRequest_thenUnauthorized() throws Exception {
        Map response = executePostRequest("", "");
        assertEquals("Unauthorized", response.get("token"));
    }

    @Test
    public void givenAWrongUserAndPassowrd_whenPostSecureRequest_thenUnauthorized() throws Exception {
        Map response = executePostRequest("user", "pass");
        assertEquals("Unauthorized", response.get("token"));
    }

    @Test
    public void givenACorrectUserAndPassowrd_whenPostSecureRequest_thenReturnToken() throws Exception {
        Map response = executePostRequest(user, password);
        assertNotNull(response);
        assertFalse(response.get("token").toString().isEmpty());
    }

    @Test
    public void givenAEmptyToken_whenGetNewsRequest_thenForbiden() throws Exception {
        ResponseEntity<String> response = executeGetRequest("");
        assertNotNull(response);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void givenAWrongToken_whenGetNewsRequest_thenForbiden() throws Exception {
        ResponseEntity<String> response = executeGetRequest("wrong-token");
        assertNotNull(response);
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void givenACorrectToken_whenGetNewsRequest_thenOk() throws Exception {
        Map responseToken = executePostRequest(user, password);
        String token = responseToken.get("token").toString();
        ResponseEntity<String> response = executeGetRequest(token);
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }


    private ResponseEntity<String> executeGetRequest(String token) throws Exception {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        String api = "/v1/api/news";
        String baseUrl = "http://localhost:" + randomServerPort + api;
        URI uri = new URI(baseUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);
        return testRestTemplate.exchange(RequestEntity.get(uri).headers(headers).build(), String.class);
    }



    public Map executePostRequest(String user, String password) throws URISyntaxException {
        TestRestTemplate testRestTemplate = new TestRestTemplate();
        MultiValueMap<String, String> request = new LinkedMultiValueMap<>();
        request.set("user", user);
        request.set("password", password);
        String api = "/login";
        String baseUrl = "http://localhost:" + randomServerPort + api;
        URI uri = new URI(baseUrl);
        return testRestTemplate.postForObject(uri, request, Map.class);
    }




}
