package com.GasGuru.GasGuru.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.GasGuru.GasGuru.entity.Feedback;
import com.GasGuru.GasGuru.entity.Person;
import com.GasGuru.GasGuru.model.CommonResponse;
import com.GasGuru.GasGuru.model.FeedbackModel;
import com.GasGuru.GasGuru.repo.FeedbackRepo;
import com.GasGuru.GasGuru.repo.PersonRepo;



@Service
public class FeedbackServices {
	
	@Autowired
	private FeedbackRepo repoFeedback;
	@Autowired
	private PersonRepo repoPerson;
	
	@Autowired
	private Feedback feedback;
	
	public ResponseEntity addFeedback(FeedbackModel feedback) {
		this.feedback.setFeedback(feedback.getFeedBack());
		this.feedback.setPerson(repoPerson.findById(feedback.getUsername()).get());
		repoFeedback.save(this.feedback);
		return new ResponseEntity(new CommonResponse("feedback is saved"), HttpStatus.OK);
	}
	
	public ResponseEntity getFeedback() {
		return new ResponseEntity(repoFeedback.findAll(), HttpStatus.OK);
	}

	public ResponseEntity deleteFeedback(int feedbackID){
		repoFeedback.deleteById(feedbackID);
		return new ResponseEntity(new CommonResponse("feedback is deleted"),HttpStatus.OK);
	}
}
