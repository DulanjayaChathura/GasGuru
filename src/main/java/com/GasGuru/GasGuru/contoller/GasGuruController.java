package com.GasGuru.GasGuru.contoller;

import java.util.UUID;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.GasGuru.GasGuru.entity.Person;
import com.GasGuru.GasGuru.jwtConfig.JwtTokenUtil;
import com.GasGuru.GasGuru.model.EditDetails;
import com.GasGuru.GasGuru.model.FeedbackModel;
import com.GasGuru.GasGuru.model.FualStationDetailsModel;
import com.GasGuru.GasGuru.model.Login;
import com.GasGuru.GasGuru.model.RegisterDetails;
import com.GasGuru.GasGuru.services.PersonServices;
import com.GasGuru.GasGuru.services.FeedbackServices;
import com.GasGuru.GasGuru.services.FualStationServices;
import com.GasGuru.GasGuru.services.JwtUserDetailsService;
import com.GasGuru.GasGuru.util.JacksonConfig;



@RestController
@RequestMapping(path = "/gasGuru")
@CrossOrigin(origins = "http://localhost:4200" )
public class GasGuruController {
	
	@Autowired
	private PersonServices personServices;
	@Autowired
	private FualStationServices fualStationServices;
	@Autowired
	private FeedbackServices feedbackServices;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private JwtUserDetailsService userDetailsService;
	
	
	@Autowired
	private JacksonConfig jacksonConfig;
	
	private static final Logger logger = LogManager.getLogger(GasGuruController.class);
	private static final String API_NAME = "apiName"; 
	
	@GetMapping("/serviceCheck")
	public ResponseEntity<String> serviceCheck() {
		return ResponseEntity.ok(" Service is working ...");
	}
	
	@PostMapping("/register")
	public ResponseEntity register(@RequestBody RegisterDetails model){
		
		ThreadContext.put("id", UUID.randomUUID().toString());
		ThreadContext.put(API_NAME, "/gasGuru/register");
		logger.info("register {}" , jacksonConfig.convertToJson(model));
		return personServices.register(model);
		
	}
	@PostMapping("/login")
	public ResponseEntity login(@RequestBody Login model){
		
		ThreadContext.put("id", UUID.randomUUID().toString());
		ThreadContext.put(API_NAME, "/gasGuru/login");
		logger.info("login {}" , jacksonConfig.convertToJson(model));
		return userDetailsService.authenticate(model);
		
	}
	@PostMapping("/addFeedback")
	public ResponseEntity addFeedback(@RequestBody FeedbackModel model){
		
		ThreadContext.put("id", UUID.randomUUID().toString());
		ThreadContext.put(API_NAME, "/gasGuru/addFeedback");
		logger.info("addFeedback {}" , jacksonConfig.convertToJson(model));
		return feedbackServices.addFeedback(model);
		
	}
	
	@GetMapping("/getFeedback")
	public ResponseEntity getFeedback(){
		
		ThreadContext.put("id", UUID.randomUUID().toString());
		ThreadContext.put(API_NAME, "/gasGuru/getFeedback");
		logger.info("getFeedback " );
		return feedbackServices.getFeedback();
	}
	
	@GetMapping("/deleteFeedback/{feedbackID}")
	public ResponseEntity deleteFeedback(@PathVariable(name ="feedbackID", required = true)  int feedbackID){
		
		ThreadContext.put("id", UUID.randomUUID().toString());
		ThreadContext.put(API_NAME, "/gasGuru/deleteFeedback");
		logger.info("deleteFeedback :: id :: {}",feedbackID );
		return feedbackServices.deleteFeedback(feedbackID);
	}
	
	@PutMapping("/editDetails/{username}")
	public ResponseEntity editDetails(@RequestBody EditDetails model,
			@PathVariable(name ="username", required = true)  String username) {
		ThreadContext.put("id", UUID.randomUUID().toString());
		ThreadContext.put(API_NAME, "/gasGuru/editDetails");
		logger.info("editDetails {} {}", jacksonConfig.convertToJson(model), username);
		return personServices.editDetails(username, model);
		
	}
	@PostMapping("/getDetailsByUsername")
	public ResponseEntity getDetails(@RequestParam String username) {
		ThreadContext.put("id", UUID.randomUUID().toString());
		ThreadContext.put(API_NAME, "/getDetailsByUsername");
		logger.info("getDetails {}",  username);
		return personServices.getDetails(username);
		
	}
	@GetMapping("/getUsername")
	public ResponseEntity getAllUserDetails() {
		ThreadContext.put("id", UUID.randomUUID().toString());
		ThreadContext.put(API_NAME, "/getUsername");
		logger.info("getUsername");
		return personServices.getUsername();
		
	}
	@PostMapping("/addFualStation")
	public ResponseEntity addFualStation(@RequestBody FualStationDetailsModel model) {
		ThreadContext.put("id", UUID.randomUUID().toString());
		ThreadContext.put(API_NAME, "/gasGuru/addFualStation");
		logger.info("addFualStation {}", jacksonConfig.convertToJson(model));
		return fualStationServices.addFualStation(model);
	}
	@PutMapping("/editFualStation")
	public ResponseEntity editFualStation(@RequestBody FualStationDetailsModel model) {
		ThreadContext.put("id", UUID.randomUUID().toString());
		ThreadContext.put(API_NAME, "/gasGuru/editFualStation");
		logger.info("editFualStation {} {}", jacksonConfig.convertToJson(model));
		return fualStationServices.editFualStation( model);
		
	}
	@GetMapping("/searchFualStation/{stationId}")
	public ResponseEntity serachFualStation(@PathVariable(name ="stationId", required = true)  int  stationId) {
		ThreadContext.put("id", UUID.randomUUID().toString());
		ThreadContext.put(API_NAME, "/gasGuru/searchFualStation");
		logger.info("searchFualStation {}", stationId);
		return fualStationServices.searchFualStation(stationId);
	}
	@GetMapping("/allFualStation")
	public ResponseEntity getAllFualStation() {
		ThreadContext.put("id", UUID.randomUUID().toString());
		ThreadContext.put(API_NAME, "/gasGuru/allFualStation");
		logger.info("/gasGuru/allFualStation {}");
		return fualStationServices.getAllFualStation();
	}




	

}
