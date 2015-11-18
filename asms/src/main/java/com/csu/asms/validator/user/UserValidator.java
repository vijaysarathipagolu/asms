/**
 * 
 */
package com.csu.asms.validator.user;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import com.csu.asms.domain.user.User;

/**
 * @author vijay
 *
 */
@Component
public class UserValidator implements Validator {

	private static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

	@Override
	public boolean supports(@SuppressWarnings("rawtypes") Class clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		User user = (User) target;
		
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "csuid", "empty.csuid");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "firstName", "empty.firstName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "lastName", "empty.lastName");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "empty.email");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmemail", "empty.confirmEmail");

		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "city", "empty.city");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phoneNumber", "empty.phoneNumber");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "typeOfUser", "empty.typeOfUser");
		//ValidationUtils.rejectIfEmptyOrWhitespace(errors, "post", "empty.post");
		
		

		if (!user.isEdit()) {
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "empty.password");
			ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPwd", "empty.confirmpassword");
		}

		csuidValidation(user,errors);
		cityValidation(user, errors);
		firstNameValidation(user, errors);
		lastNameValidation(user, errors);
		if (!user.isEdit()) {
			passwordValidation(user, errors);
			passwordValidationNew(user, errors);
		}
		phoneNumberValidation(user, errors);
		// zipCodeValidation(user, errors);
		emailValidation(user, errors);
		//confirmEmailValidation(user, errors);
		typeOfUserValidation(user, errors);
	}

	private void csuidValidation(User user, Errors errors){
		
		Long csuid = user.getCsuid();
		
		if(csuid.toString().length()<7){
			errors.reject("csuid","invalid.csuid");
		}
	}
	private void typeOfUserValidation(User user, Errors errors) {

		
		String typeOfUser = user.getTypeOfUser();
		if ("select".equalsIgnoreCase(typeOfUser)) {
			errors.rejectValue("typeOfUser", "invalid.typeOfUser");
		}

	}

	private void cityValidation(User user, Errors errors) {

		String city = user.getCity();
		if (!"".equalsIgnoreCase(city)) {
			if (city.length() <= 50) {

				Pattern p = Pattern.compile("[a-zA-Z]*[ ]*[a-zA-Z]*[ ]*[a-zA-Z]*");

				Matcher m = p.matcher(city);
				boolean b = m.matches();
				if (!b)
					errors.rejectValue("city", "invalid.city");

			} else {
				errors.rejectValue("city", "invalidLength.city");
			}
		}
	}

	private void firstNameValidation(User user, Errors errors) {

		String firstName = user.getFirstName();
		if (firstName.length() < 50) {

			Pattern p = Pattern.compile("[a-zA-Z]*[ ]*[a-zA-Z]*");

			Matcher m = p.matcher(firstName);
			boolean b = m.matches();
			if (!b)
				errors.rejectValue("firstName", "invalid.firstName");

		} else if (firstName.length() > 50) {
			errors.rejectValue(" firstName", "required. firstName", "First Name Cannot Be More Than 50 Characters");
		}

	}

	private void phoneNumberValidation(User user, Errors errors) {

		String phoneNumber = user.getPhoneNumber();
		if (phoneNumber.length() != 0) {
			if (phnIfNonNumeric(phoneNumber, errors))
			
				errors.rejectValue("phoneNumber", "required.phoneNumber",
						"Phone number should only contain numeric characters");

			else if (phoneNumber.length() > 20)
				errors.rejectValue("phoneNumber", "required.phoneNumber",
						"Phone number should be less than 20 Characters");

		}
	}

	private boolean phnIfNonNumeric(String phoneNumber, Errors errors) {

		Pattern p = Pattern.compile("[0-9]*");

		Matcher m = p.matcher(phoneNumber);
		boolean b = m.matches();
		if (!b) {
			return true;
		}

		return false;
	}

	/**
	 * This method is used to validate last name of user
	 * 
	 * @param user
	 * @param errors
	 */
	private void lastNameValidation(User user, Errors errors) {

		String lastName = user.getLastName();
		if (lastName.length() < 50) {

			Pattern p = Pattern.compile("[a-zA-Z]*[ ]*[a-zA-Z]*");

			Matcher m = p.matcher(lastName);
			boolean b = m.matches();
			if (!b)
				errors.rejectValue("lastName", "invalid.lastName");

		} else if (lastName.length() > 50) {
			errors.rejectValue(" lastName", "invalid.lastName", "Last Name Cannot Be More Than 50 Characters");
		}

	}

	/**
	 * This method validates email and checks for correct pattern and max of 255
	 * characters
	 * 
	 * @param user
	 * @param errors
	 */
	public void emailValidation(User user, Errors errors) {

		String email = user.getEmail();

		if (email.length() != 0) {

			Pattern p = Pattern.compile(EMAIL_PATTERN);

			Matcher m = p.matcher(email);
			boolean b = m.matches();
			if (!b)
				errors.rejectValue("email", "invalid.email");

		}
		if (email.length() > 255) {
			errors.rejectValue("email", "required.email", "Email Cannot Be More Than 255 Characters");
		}
	}

	/**
	 * This method is used to validate and check password and confirm password
	 * 
	 * @param user
	 * @param errors
	 */
	private void confirmEmailValidation(User user, Errors errors) {

		String email = user.getConfirmemail();
		if (!"".equalsIgnoreCase(email)) {
			if (!user.getEmail().equals(email))
				errors.rejectValue("confirmemail", "email.different");

		}
	}

	/**
	 * This method validates confirm password and checks if password and confirm
	 * password matches
	 * 
	 * @param user
	 * @param errors
	 */
	public void passwordValidation(User user, Errors errors) {
		String pwdone = user.getPassword();
		String confirmpwd = user.getConfirmPwd();
		if (!"".equalsIgnoreCase(confirmpwd)) {
			if (!pwdone.equals(confirmpwd))

				errors.rejectValue("confirmPwd", "messagevalue");
		}

	}

	/**
	 * This method is used to validate user password
	 * 
	 * @param user
	 *            user details
	 * @param errors
	 *            object
	 */
	private void passwordValidationNew(User user, Errors errors) {

		String password = user.getPassword();
		if (!"".equalsIgnoreCase(password)) {

			int pwdLength = user.getPassword().length();
			if (pwdLength < 5 || pwdLength > 15) {
				errors.rejectValue("password", "invalid.passwordLength");
				//errors.rejectValue("password", "invalid.commonpassword");
			} else {
				int upperCaseCount = 0;
				int numericCount = 0;
				for (int i = 0; i < pwdLength; i++) {
					Character pwd = password.charAt(i);
					if (Character.isDigit(pwd))
						numericCount++;
					if (Character.isUpperCase(pwd))
						upperCaseCount++;
				}

				if (numericCount == 0 || upperCaseCount == 0) {
					errors.rejectValue("password", "invalid.passwordType");
					//errors.rejectValue("password", "invalid.commonpassword");
				}

			}
		}
	}

}
