package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
		Scanner sc=new Scanner(System.in);
		String movie=sc.nextLine();
		if movie.matches("https://www.omdbapi.com"){
			System.out.print("Movie Matched");
		}	
		else{
			System.out.print("Movie not matched");
		}
		}
		

}
