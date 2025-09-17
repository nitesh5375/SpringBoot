package com.oracle.SpringJDBCdemo;

import com.oracle.SpringJDBCdemo.model.Alien;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class SpringJdbCdemoApplication {

	public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(SpringJdbCdemoApplication.class, args);
        Alien alien1 = context.getBean(Alien.class);
        alien1.setId(1111);
        alien1.setName("Nitesh");
        alien1.setEmail("niteshsingh5375@gamil.com");

        AlienRepo repo = context.getBean(AlienRepo.class);
        repo.save(alien1);

        System.out.println(repo.findAll());
	}

}
