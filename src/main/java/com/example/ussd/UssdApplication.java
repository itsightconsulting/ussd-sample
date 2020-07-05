package com.example.ussd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@SpringBootApplication
@RestController("")
public class UssdApplication extends SpringBootServletInitializer {

    public static final String ARTIFICAL_USSD_CODE = "*333";
    public static final String ESCAPE_HASHTAG_CHAR = "%23";

    private static final String RESOURCE_SERVER_HOST = "http://200.107.154.160:8080/epay-rsrc-0.0.1-SNAPSHOT/api/ussd";
//    private static final String RESOURCE_SERVER_HOST = "http://localhost:8090/api/ussd";

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    public static void main(String[] args) {
        SpringApplication.run(UssdApplication.class, args);
    }

    @GetMapping(value = "/epayussd", produces = {MediaType.TEXT_HTML_VALUE})
    public String call(@RequestParam(value = "ani", required = false) String ani,
                       @RequestParam(value = "imsi", required = false) String imsi,
                       @RequestParam(value = "dnis", required = false) String dnis,
                       @RequestParam(value = "msisdn", required = false) String msisdn,
                       @RequestParam(value = "dialogID", required = false) String dialogID,
                       @RequestParam(value = "username", required = false, defaultValue = "") String username,
                       @RequestParam(value = "sellerUsername", required = false, defaultValue = "") String sellerUsername,
                       @RequestParam(value = "ussd", required = false, defaultValue = "") String ussd,
                       @RequestParam(value = "rechargePhone", required = false, defaultValue = "") String rechargePhone,
                       @RequestParam(value = "rechargeAmount", required = false, defaultValue = "") String rechargeAmount,
                       @RequestParam(value = "userPin", required = false, defaultValue = "") String userPin,
                       HttpServletRequest request) {
        System.out.println("+|+|+|+|+|+ +|+|+|+|+|+ INVOKING METHOD CALL(...)");
        Collections.list(request.getParameterNames()).forEach(param ->
                System.out.println(">> " + param.toUpperCase() + ": " + request.getParameter(param))
        );

        System.out.println(">>> Prev to msisdn");
        System.out.println(">>> msisdn: " + msisdn);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> mvm = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> httpEntity =
                new HttpEntity<>(mvm, headers);
        String shortNumber = ARTIFICAL_USSD_CODE + ESCAPE_HASHTAG_CHAR;
        String finalParam = shortNumber + (ussd.isEmpty() ? username : ussd) + (rechargePhone.isEmpty() ? "" : ESCAPE_HASHTAG_CHAR + rechargePhone)
                + (rechargeAmount.isEmpty() ? "" : ESCAPE_HASHTAG_CHAR + rechargeAmount)
                + (userPin.isEmpty() ? "" : ESCAPE_HASHTAG_CHAR + userPin)
                + (sellerUsername.isEmpty() ? "" : ESCAPE_HASHTAG_CHAR + sellerUsername);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(RESOURCE_SERVER_HOST)
                .queryParam("ruta", finalParam)
                .queryParam("msisdn", msisdn.length() == 11 ? msisdn.substring(2) : msisdn).build();
        System.out.println(">> GET REST REQUEST: " + builder.toUriString());
        System.out.println(">> GET PARAM REQUEST: " + finalParam);
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, String.class);
        return responseEntity.getBody();
    }

}
