package services;

import dataaccess.UserDB;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.User;

public class AccountService {

    public User login(String email, String password, String path) {
        UserDB userDB = new UserDB();

        try {
            User user = userDB.get(email);
            if (password.equals(user.getPassword())) {
                //Logger.getLogger(AccountService.class.getName()).log(Level.INFO, "Successful login by {0}", email);

                /*
                String to = user.getEmail();
                String subject = "Notes App Login";
                String template = path + "/emailtemplates/login.html";
                
                HashMap<String, String> tags = new HashMap<>();
                tags.put("firstname", user.getFirstName());
                tags.put("lastname", user.getLastName());
                tags.put("date", (new java.util.Date()).toString());
                
                GmailService.sendMail(to, subject, template, tags);
                 */
                
                return user;
            }
        } catch (Exception e) {
        }

        return null;
    }
    
    public void resetPassword(String email, String path, String url) {
        UserDB userDB = new UserDB();
        
        User user = userDB.get(email);
        
        if(user == null) {
            return;
        }
        
        //Reset uuid
        String uuid = UUID.randomUUID().toString();
        
        //Updating user uuid
        user.setResetPasswordUuid(uuid);
        userDB.updateUser(user);
        
        //Email
        HashMap<String, String> tags = new HashMap<>();
        tags.put("firstName", user.getFirstName());
        tags.put("lastName", user.getLastName());
        tags.put("resetLink", url + "?uuid=" + uuid);
        
        try {
            GmailService.sendMail(user.getEmail(), "My Notes Reset Password", path, tags);
        } catch (Exception e) {
        }
        
    }
    
    public User getUuidUser(String uuid) {
        UserDB userDB = new UserDB();
        
        User user = userDB.getUuidUser(uuid);
        
        return user;
    }
    
    public void updateUser(User user) {
        UserDB userDB = new UserDB();
        
        userDB.updateUser(user);
    }
    
}
