package com.GasGuru.GasGuru.services;

import java.util.NoSuchElementException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.GasGuru.GasGuru.Exception.BadRequestException;
import com.GasGuru.GasGuru.contoller.GasGuruController;
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

	private static final Logger logger = LogManager.getLogger(FeedbackServices.class);

	public ResponseEntity addFeedback(FeedbackModel feedback) {
		try {
			Feedback feedbackObj = new Feedback();
			feedbackObj.setFeedback(feedback.getFeedBack());
			feedbackObj.setPerson(repoPerson.findById(feedback.getUsername()).get());
			repoFeedback.save(feedbackObj);
			return new ResponseEntity(new CommonResponse("feedback is saved"), HttpStatus.OK);
		} catch (BadRequestException e) {
			logger.error("error occured {} {} ", e.getMessage(), e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			logger.error("internal server error :: {}", e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity getFeedback() {
		try {
			return new ResponseEntity(repoFeedback.findAll(), HttpStatus.OK);

		} catch (BadRequestException e) {
			logger.error("error occured {} {} ", e.getMessage(), e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.BAD_REQUEST);
		}catch (Exception e) {
			logger.error("internal server error :: {}", e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	public ResponseEntity deleteFeedback(int feedbackID) {
		try {
			repoFeedback.deleteById(feedbackID);
			return new ResponseEntity(new CommonResponse("feedback is deleted"), HttpStatus.OK);

		} catch (BadRequestException e) {
			logger.error("error occured {} {} ", e.getMessage(), e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.BAD_REQUEST);

		} catch (Exception e) {
			logger.error("internal server error :: {}", e);
			return new ResponseEntity(new CommonResponse(e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
