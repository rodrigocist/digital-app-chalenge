package com.digital.challenge.client;

import com.digital.challenge.exception.NewsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
public class Consume {
    public String consume(String uri) throws NewsException {
        try {
            log.debug("[Consume] consumeApi api {} ", uri);
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject(uri, String.class);
        }catch(NewsException e){
            throw e;
        }
    }

}
