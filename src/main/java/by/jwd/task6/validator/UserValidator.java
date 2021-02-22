package by.jwd.task6.validator;

import java.nio.CharBuffer;
import java.util.regex.Pattern;

public class UserValidator {

    private static final Pattern namePattern = Pattern.compile("([а-яёА-ЯЁ]|[a-zA-Z]){2,15}");
    private static final Pattern surnamePattern = Pattern.compile("([а-яёА-ЯЁ]|[a-zA-Z]){2,15}");
    private static final Pattern emailPattern = Pattern.compile("\\w+@[\\w]+[\\.][a-zA-Z]+$");
    private static final Pattern passwordPattern = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*_=+-]).{6,12}$");
    
    public static boolean isValidForSignUp(String name, String surname, String email, char[] password) {
        boolean isValid = (namePattern.matcher(name).matches() 
                            && surnamePattern.matcher(surname).matches()
                            && emailPattern.matcher(email).matches()
                            && passwordPattern.matcher(CharBuffer.wrap(password)).matches());
        return isValid;
    }
    
    public static boolean isValidForSignIn(String email, char[] password)  {
        boolean isValid = (emailPattern.matcher(email).matches() && passwordPattern.matcher(CharBuffer.wrap(password)).matches());
        return isValid;
    }

}
