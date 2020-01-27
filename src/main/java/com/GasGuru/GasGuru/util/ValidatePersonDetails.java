package com.GasGuru.GasGuru.util;

import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.GasGuru.GasGuru.Exception.BadRequestException;

@Component
public class ValidatePersonDetails {

	public boolean isNicValid(String nic) {
		if (!(nic.trim().matches("/^[0-9]{9}[vVxX]$/"))) {
			throw new BadRequestException("enter valid nic number");

		}
		return true;
	}

	public boolean isValidPhoneNo(String phoneNo) {
		if (phoneNo.length() == 10) {
			try {
				Integer.parseInt(phoneNo);
				return true;
			} catch (Exception e) {

			}

		}
		throw new BadRequestException("enter valid phone no");
	}

	public boolean isValidEmail(String email) {
		String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
				+ "A-Z]{2,7}$";

		Pattern pat = Pattern.compile(emailRegex);
		if (pat.matcher(email).matches()) {
			return true;
		}
		throw new BadRequestException("enter valid email");

	}

}
