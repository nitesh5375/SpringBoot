package com.self.SpringJDBCdemo;

import com.self.SpringJDBCdemo.model.Alien;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Scanner;

//Annotations in Java:
//Do NOT guarantee order
//Are NOT evaluated top-to-bottom
//Are NOT executed in the order they appear in your code

@SpringBootApplication
public class SpringJdbcDemoApplication {

	public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(SpringJdbcDemoApplication.class, args);
//        Alien alien1 = context.getBean(Alien.class);
//        alien1.setId(1111);
//        alien1.setName("Nitesh");
//        alien1.setEmail("niteshsingh5375@gamil.com");
//
//        AlienRepo repo = context.getBean(AlienRepo.class);
//        repo.save(alien1);
//
//        System.out.println(repo.findAll());
//
//        Scanner sc = new Scanner(System.in);
//        System.out.print("Enter an alien's ID: ");
//        int alienID = Integer.parseInt(sc.nextLine());
//        System.out.print("Enter an alien's name: ");
//        String alienName =  sc.nextLine();
//        System.out.print("Enter an alien's email: ");
//        String alienEmail =  sc.nextLine();
//        alien1.setId(alienID);
//        alien1.setName(alienName);
//        alien1.setEmail(alienEmail);
//        repo.save(alien1);
//        System.out.println(repo.findAll());

	}

}
