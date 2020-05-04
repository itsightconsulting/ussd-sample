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
                       HttpServletRequest request) {
        System.out.println(">> MSISDN: " + msisdn);
        System.out.println(">> IMSI: " + imsi);
        System.out.println(">> IMSI: " + dialogID);
        System.out.println(">> USSD: " + ussd);
        System.out.println("****************************************");
        Collections.list(request.getParameterNames()).forEach(param->{
            System.out.println(">> " + param.toUpperCase() +": " +request.getParameter(param));
        });//http://200.121.226.12:8080/epayussd?ANI=987
        return "<html>" +
                " <body>" +
                " This is the main menu.<br/>" +
                " Please select:<br/>" +
                " <a href=\"menu1.html\">Tariff options</a><br/>" +
                " <a href=\"http://200.121.226.12:8080/epayussd?ANI=987\">Contract</a><br/>" +
                " <a href=“menu2.html?option=2\"" +
                " key=\"9\">Administration</a><br/>" +
                " <a href=\"error.html\" default=\"true\"></a><br/>" +
                " </body>" +
                "</html>";
    }


//    ANI=972521234567&vlr=123&hlr=123&DNIS=*123#&ph
//            ase=2&MPSESSIONID=21323&PP=1
//    Note that the MSISDN is mapped to the parameter ANI; the service code is

//    Note that the MSISDN is mapped to the parameter ANI; the service code is mapped to
//    the parameter DNIS; and the USSD session ID is mapped to MPSESSIONID

}
