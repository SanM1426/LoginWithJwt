package com.example.demo.controller;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.UserData;
import com.example.demo.service.SignupService;
import com.example.demo.service.jwt.JwtService;
import com.example.demo.userdao.AuthRequest;

@RestController
@RequestMapping("/api")
public class Signup {
	
	@Autowired
	SignupService signupService;
	
	@Autowired
	JwtService jwtService;
	
	@Autowired
	AuthenticationManager authManager;
	
	
	@PostMapping("/signup")
	public ResponseEntity<String> getData(@RequestBody UserData signUp){
		try {
			if(signupService.emailCheck(signUp.getEmail())) {
				if(signupService.passwordCheck(signUp.getPass())) {
					signupService.postData(signUp);
					return new ResponseEntity<String>("Account has been created successfully ",HttpStatus.CREATED);
			 	}else {
					return new ResponseEntity<String>("Invalid Password \n Minimum eight characters, at least one letter and one number amd atleast one special characters",HttpStatus.BAD_REQUEST);
				}
			}else {
				return new ResponseEntity<String>("Invalid email address ",HttpStatus.BAD_REQUEST);
			}
		}
		catch(Exception e) {
			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	@GetMapping("/login")
	public String login(Principal principal) {
		String userName=principal.getName();
		return "Welcome " + signupService.getName(userName);
	}

	@PostMapping("/token")
	public ResponseEntity<String> generateToken(@RequestBody AuthRequest authRequest) {
		Authentication authentication=authManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUserName(),authRequest.getPassword()));
		
		if(authentication.isAuthenticated()) {
			
			return new ResponseEntity<String>(jwtService.generateToken(authRequest.getUserName()),HttpStatus.ACCEPTED);
		}
		else {
			return new ResponseEntity<String>("User is Invalid",HttpStatus.BAD_REQUEST);
		}
	}
	
	
	
	
	
	
	
	
	
	
	
//	@GetMapping("/get")
//	public Map<String,Object> getData(@RequestBody UserDAO userId){
//		Map<String,Object> map=new HashMap();
//		map.putAll(signupService.getData(userId));
//		return map;	
//	}
//	
//	@DeleteMapping("/delete")
//	public ResponseEntity<String> delAcc(@RequestBody UserDAO user){
//		try {
//			if(signupService.check(user.getId())) {
//			Integer id=user.getId();
//			signupService.delAccount(id);
//			return new ResponseEntity<String>("Your Account has been deleted succesfully",HttpStatus.ACCEPTED);
//			}
//			else {
//				return new ResponseEntity<String>("Account Not Exist ",HttpStatus.NOT_FOUND);
//			}
//		}
//		catch(Exception e) {
//			return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
//		}
//		
//	}
//	
//	@PutMapping("/update/{id}")
//	public ResponseEntity<String>putData(@RequestBody UpdateUser user,@PathVariable int id){
//		if(signupService.check(id)) {
//			signupService.updateData(user, id);
//			return new ResponseEntity<String>("Update Succesfully",HttpStatus.ACCEPTED);
//		}
//		else {
//			return new ResponseEntity<String>("User Not Exist",HttpStatus.BAD_REQUEST);
//		}
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	   
}


