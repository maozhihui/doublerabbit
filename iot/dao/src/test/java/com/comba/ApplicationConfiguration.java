package com.comba;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.comba.server.dao"})
public class ApplicationConfiguration {

}
