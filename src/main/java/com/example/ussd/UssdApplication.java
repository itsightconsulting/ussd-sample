package com.example.ussd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;

@SpringBootApplication
@RestController("")
public class UssdApplication extends SpringBootServletInitializer {

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
                       @RequestParam(value = "DNIS", required = false) String ussd,
                       @RequestParam(value = "dialogID", required = false) String dialogID,
                       @RequestParam(value = "ussdPath", required = false, defaultValue = "") String ussdPath,
                       HttpServletRequest request) {
        System.out.println("***********  S  T  A  R  T  *****************************");
        Collections.list(request.getParameterNames()).forEach(param -> {
            System.out.println(">> " + param.toUpperCase() + ": " + request.getParameter(param));
        });//http://200.121.226.12:8080/epayussd?ANI=987
        if (ussdPath.isEmpty()) {

        }
        return "<html>" +
                " <body>" +
                " Health Insurance<br/>" +
                " Please enter your age<br/><br/>" +
                " <form action=\"http://10.68.143.122:8080/epayussd\"" +
                "method=\"GET\">" +
                " <input type=\"text\" name=\"ussdPath\">" +
                " </form>" +
                " </body>" +
                "</html>";
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
