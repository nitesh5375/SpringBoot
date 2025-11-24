package com.self.SpringJDBCdemo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


//When Spring Boot starts, it://
// ✔ scans your project
//✔ finds all classes annotated with @Configuration
//✔ prepares them for bean creation
//Spring says: “This class contains bean definitions. I must run them and save the results into my container."
@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    //Spring normally injects beans by TYPE(return type). in this case ModelMapper
    // if there are multiple bean with same return type.
    // at the time of autowiring we need to define the name also of the bean like this
    //@Autowired
    //@Qualifier("mapper2")
    //ModelMapper modelMapper;
    @Bean
    @Primary        // or we can make a bean primary
    public ModelMapper mapper2() {
        ModelMapper m = new ModelMapper();
        return m;
    }
}
