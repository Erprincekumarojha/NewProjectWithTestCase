package com.accolite.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.accolite.entity.User;

import com.accolite.repository.UserRepository;

@RestController
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public ResponseEntity<Object> getUser(@RequestParam Long userId) {
		Optional<User> user = userRepository.findByUserId(userId);
		if (user.isPresent())
			return new ResponseEntity<Object>(user.get(), HttpStatus.FOUND);
		else
			return new ResponseEntity<Object>("User not found", HttpStatus.NOT_FOUND);
	}

	@RequestMapping(value = "/addUser", method = RequestMethod.POST)
	public ResponseEntity<Object> addUser(@RequestBody User userDetails) {
		ResponseEntity<Object> response = null;
		if (userDetails.getUserName().length() < 3 || userDetails.getUserEmail().length() < 11) {
			response = new ResponseEntity<Object>("User not created", HttpStatus.NOT_ACCEPTABLE);
			return response;
		}
		User user = new User();
		user.setUserEmail(userDetails.getUserEmail());
		user.setUserName(userDetails.getUserName());
		User userSaved = userRepository.save(user);
		if (null != userSaved) {
			response = new ResponseEntity<Object>(userSaved.getUserId() + " created", HttpStatus.CREATED);
		}
		return response;
	}

	@RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
	public ResponseEntity<Object> update(@RequestBody User updateUser) {
		ResponseEntity<Object> response = null;
		Long userId = updateUser.getUserId();
		if (updateUser.getUserName().length() < 3 || updateUser.getUserEmail().length() < 11) {
			response = new ResponseEntity<Object>("User not created", HttpStatus.NOT_ACCEPTABLE);
			return response;
		}
		Optional<User> user = userRepository.findByUserId(userId);
		if (user.isPresent()) {
			User currentUser = user.get();
			currentUser.setUserEmail(updateUser.getUserEmail());
			currentUser.setUserName(updateUser.getUserName());
			User userUpdated = userRepository.save(currentUser);
			if (null != userUpdated) {
				response = new ResponseEntity<Object>(userUpdated.getUserId() + " updated", HttpStatus.OK);
			}
		} else {
			User newUser = new User();
			newUser.setUserId(updateUser.getUserId());
			newUser.setUserEmail(updateUser.getUserEmail());
			newUser.setUserName(updateUser.getUserName());
			User userSaved = userRepository.save(newUser);
			if (null != userSaved) {
				response = new ResponseEntity<Object>(userSaved.getUserId() + " created", HttpStatus.CREATED);
			}

		}
		return response;
	}

	@RequestMapping(value = "/deleteUser", method = RequestMethod.DELETE)
	public ResponseEntity<Object> deleteUser(@RequestParam Long userId) {
		try {
			userRepository.deleteById(userId);
			return new ResponseEntity<Object>(userId + " deleted", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<Object>(userId + "not found", HttpStatus.NOT_FOUND);
		}
	}

}
