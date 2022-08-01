<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Forgot Password - Reset</title>
        <style>
            body > div {
                margin-bottom: 20px;
            }
        </style>
    </head>
    <body>
        <div>
            <h1>Reset Password</h1>
        </div>
        
        <div>
            Please enter your email address to reset your password.
        </div>
        
        <div>
            <form method="post" action="reset">
                <div>
                    <label>Email Address:</label>
                    <input type="text" name="reset_email">
                </div>
                
                <div>
                    <input type="submit" value="Submit">
                </div>
            </form>
        </div>
        
        <c:if test="${receivedResetRequest}">
            <div>
                Reset request received. If the email address is valid, you will receive password reset link shortly.
            </div>
        </c:if>
        
        <c:if test="${resetMessage != null}">
            <div>${resetMessage}</div>
        </c:if>
        
        <div>
            <a href="./login">Login</a>
        </div>
    </body>
</html>
