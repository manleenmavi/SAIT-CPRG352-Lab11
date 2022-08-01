package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import models.User;
import services.AccountService;

public class ResetPasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("uuid");
        
        if(uuid == null) {
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);            
            return;
        }
        
        AccountService as = new AccountService();
        User uuidUser = as.getUuidUser(uuid);
        
        if(uuidUser == null) {
            request.setAttribute("resetMessage", "Invalid Reset Link. Regenerate new link if you forgot your password.");
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
        } else {
            request.setAttribute("curr_reset_uuid", uuid);            
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
        }
        
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String uuid = request.getParameter("reset_uuid");
        String newPassword = request.getParameter("reset_npassword");
        String resetEmail = request.getParameter("reset_email");
        
        AccountService as = new AccountService();
        
        if(resetEmail != null) {
            request.setAttribute("receivedResetRequest", true);
            as.resetPassword(resetEmail, getServletContext().getRealPath("/WEB-INF/emailtemplates/resetpassword.html"), request.getRequestURL().toString());
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            return;
        } else if (uuid == null || newPassword == null) {
            response.sendRedirect("./reset");
            return;
        }
        
        if(newPassword.trim().isEmpty()) {
            request.setAttribute("resetMessage", "Password can't be empty. Please re-enter.");
            
            request.setAttribute("curr_reset_uuid", uuid);
            
            
            getServletContext().getRequestDispatcher("/WEB-INF/resetNewPassword.jsp").forward(request, response);
            return;
        }
        
        //Updating the password
        User user = as.getUuidUser(uuid);
        
        if(user == null) {
            request.setAttribute("resetMessage", "Invalid password reset link.");
            getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
            return;
        }
        
        user.setPassword(newPassword);
        user.setResetPasswordUuid(null);
        as.updateUser(user);
        
        request.setAttribute("resetMessage", "Password reset successfully. Please login");
        getServletContext().getRequestDispatcher("/WEB-INF/reset.jsp").forward(request, response);
        

        
    }
}
