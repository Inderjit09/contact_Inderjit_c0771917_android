package macbook.example.contact_inderjitsingh_c0771917_android;

import android.widget.EditText;

public class Validations {

    public static boolean isUsernameValid(EditText editText){
        String input = editText.getText().toString().trim();
        if(input.equals("")){
            editText.setError("Username Cannot be Empty");
            return false;
        } else if(input.contains(" ")){
            editText.setError("Username Cannot Contain Space");
            return false;
        } else if(input.length() <4 ){
            editText.setError("Username must be between 4 and 12");
            return false;
        }
        editText.setError(null);
        return true;
    }

    public static boolean isGenericallyValid(EditText editText){
        String input = editText.getText().toString().trim();
        if(input.equals("")){
            editText.setError("Field Cannot be Empty");
            return false;
        }
        return true;
    }

    /* Explanations:

            (?=.*[0-9]) a digit must occur at least once
            (?=.*[a-z]) a lower case letter must occur at least once
            (?=.*[A-Z]) an upper case letter must occur at least once
            (?=.*[@#$%^&+=]) a special character must occur at least once
            (?=\\S+$) no whitespace allowed in the entire string
            .{6,} at least 6 characters */

    public static boolean isPasswordValid(EditText editText){
        String input = editText.getText().toString().trim();

        String regexPass = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{6,}";

        if(input.equals("")){
            editText.setError("Password Cannot be Empty");
            return false;
        } else if(input.contains(" ")){
            editText.setError("Password Cannot Contain Space");
            return false;
        } else if(!input.matches(regexPass)) {
            editText.setError("Must use 1 A-Z,a-z,0-9,@$%^&*()");
            return false;
        }
        return true;

    }


}


