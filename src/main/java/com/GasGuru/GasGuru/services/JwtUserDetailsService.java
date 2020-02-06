package com.GasGuru.GasGuru.services;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.GasGuru.GasGuru.Exception.BadRequestException;
import com.GasGuru.GasGuru.entity.Person;
import com.GasGuru.GasGuru.jwtConfig.JwtTokenUtil;
import com.GasGuru.GasGuru.model.CommonResponse;
import com.GasGuru.GasGuru.model.JwtResponse;
import com.GasGuru.GasGuru.model.Login;
import com.GasGuru.GasGuru.repo.PersonRepo;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private PersonServices person;
	
	@Autowired
	private PersonRepo repo;

	@Autowired
	private AuthenticationManager authenticationManager;

	private UserDetails userDetails;
	private String token;
	private String userType;
	private static final Logger logger = LogManager.getLogger(JwtUserDetailsService.class);

	public ResponseEntity authenticate(Login login) {
		try {

			authenticate(login.getUsername(), login.getPassword());

			userDetails = loadUserByUsername(login.getUsername());
			
			userType =getUserType(login.getUsername());

			token = jwtTokenUtil.generateToken(userDetails, userType );

			return ResponseEntity.ok(new JwtResponse(token));

		} catch (DisabledException e) {
			return new ResponseEntity(new CommonResponse("user disabled"), HttpStatus.INTERNAL_SERVER_ERROR);
		} catch (BadCredentialsException e) {
			return new ResponseEntity(new CommonResponse("Invalid credentials"), HttpStatus.BAD_REQUEST);
		} catch (BadRequestException e) {
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			logger.error("error occured {}", e);
			return new ResponseEntity(new CommonResponse("error occured"), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		
		boolean present = repo.findById(username).isPresent();
		if (!present) {
			throw new UsernameNotFoundException("User not found with username: " + username);
	
		}
		Person user=repo.findById(username).get();
	
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());

	}
	public  String getUserType(String username) {
		Person user=repo.findById(username).get();
		
		return user.getUserType();
		
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			logger.error("User disabled  {}", e);
			throw new DisabledException("User disabled ");
		} catch (BadCredentialsException e) {
			logger.error("Invalid credentials {}", e);
			throw new BadCredentialsException("Invalid credentials");
		}
	}

}
