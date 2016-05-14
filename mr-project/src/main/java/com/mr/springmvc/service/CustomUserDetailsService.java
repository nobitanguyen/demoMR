package com.mr.springmvc.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mr.springmvc.model.User;
import com.mr.springmvc.repository.UserRepository;

@Service("customUserDetailsService")
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private UserRepository userService;
	
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String userName)
			throws UsernameNotFoundException {
		User user = userService.findByUserName(userName);
		System.out.println("User : "+user);
		if(user==null){
			System.out.println("User not found");
			throw new UsernameNotFoundException("Username not found");
		}
			return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), 
				 true, true, true, true, getGrantedAuthorities());
	}

	
	private List<GrantedAuthority> getGrantedAuthorities(){
		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		
		authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		System.out.print("authorities :"+authorities);
		return authorities;
	}
	
}