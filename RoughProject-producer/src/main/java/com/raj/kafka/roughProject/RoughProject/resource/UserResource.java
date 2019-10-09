package com.raj.kafka.roughProject.RoughProject.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.raj.kafka.roughProject.RoughProject.Model.User;

@RestController
@RequestMapping("/kafka")
public class UserResource {
	
	//KafkaTemplate<String, String> kafkaTemplate;
	@Autowired
	KafkaTemplate<String, User> kafkaTemplate;
	
	private static final String TOPIC = "Kafka_Example_json3";
	
	@GetMapping("/publis/{name}")
	public String post(@PathVariable("name") final String name) {
		kafkaTemplate.send(TOPIC,new User(name,"Tech",12000L));
		return "Published Successfully!";
	}
	
	@PostMapping("/user")
	public String userDetail(@RequestBody User user) {
		//kafkaTemplate.send("Kafka_Example_json3",new User(user.getName(),user.getDept(),user.getSalary()));
		
		Message<User> message = MessageBuilder
	            .withPayload(user)
	            .setHeader(KafkaHeaders.TOPIC, TOPIC)
	            .build();
	    this.kafkaTemplate.send(message);
		
		return "Published Successfully!";
	}
	
}
