package edu.kpinotepad;

import edu.kpinotepad.services.PasswordControl;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class KpinotepadApplication {

    @PostConstruct
    public void init() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }

    public static void main(String[] args) {

        PasswordControl pc = new PasswordControl();
        System.out.println(pc.hash("DemN_9Bsj40"));

        SpringApplication.run(KpinotepadApplication.class, args);
    }

}
