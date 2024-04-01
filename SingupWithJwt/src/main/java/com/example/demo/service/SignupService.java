package com.example.demo.service;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.entity.UserData;
import com.example.demo.repository.UserRepository;
import com.example.demo.userdao.UpdateUser;
import com.example.demo.userdao.UserDAO;

import jakarta.transaction.Transactional;

@Service
public class SignupService {
	
	@Autowired
	UserRepository userRepo;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public void postData(UserData userData) {
		userData.setPass(passwordEncoder.encode(userData.getPass()));
		userRepo.save(userData);
	}
	
	
	public boolean check(int id) {
		List<UserData> list=userRepo.findAll();
		Iterator<UserData> itr=list.iterator();
		while(itr.hasNext()) {
			UserData user=itr.next();
			if(user.getUserId()==id) {
				return true;
			}
		}
		return false;
	}
	
	public boolean emailCheck(String email) {
		Pattern p=Pattern.compile("^(.+)@(.+)$");
		Matcher m=p.matcher(email);
		if(m.matches()) 
			return true;
		else 
			return false;
	}
	
	public boolean passwordCheck(String pass) {
		Pattern p=Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@#$%^&+=!])(?!.*\\s).{8,}$");
		Matcher m=p.matcher(pass);
		if(m.matches())
			return true;
		else
			return false;
		
	} 
	
	public String getName(String userName) {
		Optional<UserData> user=userRepo.findByEmail(userName);
		return user.get().getName();
	}
	
	
	
	
//	
//	public Map<String,Object> getData(UserDAO userDAO){
//		Map<String,Object> map=new HashMap();
//		List<UserData> list=userRepo.findAll();
//		Iterator<UserData> itr=list.iterator();
//		while(itr.hasNext()) {
//			UserData user=itr.next();
//			if(user.getUserId()==userDAO.getId()) {
//				map.put("User Name ", user.getName());
//				map.put("User Email ",user.getEmail());
//				map.put("User Pass ", user.getPass());
//			}
//		}
//		return map;
//		}
//	
//	@Transactional
//	public void delAccount(Integer id) {
//		userRepo.deleteById(id);
//	}
	
//	public void updateData(UpdateUser user,int id) {
//		userRepo.findById(id).map(userExist->{
//		userExist.setEmail(user.getEmail());
//		userExist.setPass(user.getPass());
//		return userRepo.save(userExist);
//		});
//	
//	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * Password : Minimum eight characters, at least one letter and one number amd atleast one special characters
	 * Email : @ Symbol between any character.
	 */
}
