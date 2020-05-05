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

    private static final String RESOURCE_SERVER_HOST = "http://200.107.154.160:8080/epay-rsrc-0.0.1-SNAPSHOT/api/ussd";

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return super.configure(builder);
    }

    public static void main(String[] args) {
        SpringApplication.run(UssdApplication.class, args);
    }


    @GetMapping(value = "/epayussd", produces = {MediaType.TEXT_HTML_VALUE})
    public String demo(@RequestParam(value = "ANI", required = false) String msisdn,
                       @RequestParam(value = "IMSI", required = false) String imsi,
                       @RequestParam(value = "DNIS", required = false) String dnis,
                       @RequestParam(value = "dialogID", required = false) String dialogID,
                       @RequestParam(value = "username", required = false, defaultValue = "") String username,
                       @RequestParam(value = "ussd", required = false, defaultValue = "") String ussd,
                       HttpServletRequest request) {
        System.out.println("***********  S  T  A  R  T  *****************************");
        Collections.list(request.getParameterNames()).forEach(param -> {
            System.out.println(">> " + param.toUpperCase() + ": " + request.getParameter(param));
        });
        if (ussd.isEmpty()) {

        }
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        MultiValueMap<String, String> mvm = new LinkedMultiValueMap<>();
        HttpEntity<MultiValueMap<String, String>> httpEntity =
                new HttpEntity<>(mvm, headers);
        String shortNumber = "*333%23";
        String finalParam = shortNumber + (ussd.isEmpty() ? username : ussd);
        UriComponents builder = UriComponentsBuilder.fromHttpUrl(RESOURCE_SERVER_HOST)
                .queryParam("ruta", finalParam).build();
        System.out.println(">> GET REST REQUEST: " + builder.toUriString());
        System.out.println(">> GET PARAM REQUEST: " + finalParam);
        ResponseEntity<String> responseEntity = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, httpEntity, String.class);
        return responseEntity.getBody();
//        return "<html>" +
//                " <body>" +
//                " Health Insurance<br/>" +
//                " Please enter your age<br/><br/>" +
//                "<a href=\"http://10.68.143.122:8080/epayussd?IMSI=1\" key=\"1\">Main\n" +
//                "menu</a><br/>" +
//                " <form action=\"http://10.68.143.122:8080/epayussd\"" +
//                "method=\"GET\">" +
//                " <input type=\"text\" name=\"ussdPath\">" +
//                " </form>" +
//                " </body>" +
//                "</html>";
        /*return "<html>" +
                " <body>" +
                " This is the main menu.<br/>" +
                " Please select:<br/>" +
                " <a href=\"http://10.68.143.122:8080/epayussd?DNIS=123\">Tariff options</a><br/>" +
                " <a href=\"http://10.68.143.122:8080/epayussd?DNIS=456\">Contract</a><br/>" +
                " <a href=\"http://10.68.143.122:8080/epayussd?DNIS=789\"" +
                " key=\"7\">Administration</a><br/>" +
                " <a href=\"error.html\" default=\"true\"></a><br/>" +
                " </body>" +
                "</html>";*/
    }


//    ANI=972521234567&vlr=123&hlr=123&DNIS=*123#&ph
//            ase=2&MPSESSIONID=21323&PP=1
//    Note that the MSISDN is mapped to the parameter ANI; the service code is

//    Note that the MSISDN is mapped to the parameter ANI; the service code is mapped to
//    the parameter DNIS; and the USSD session ID is mapped to MPSESSIONID

}
