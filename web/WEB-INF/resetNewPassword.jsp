<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Reset Password - Enter new Password</title>
    </head>
    <body>
        <h1>Enter a new password</h1>

        <form method="post" action="reset">
            <div>
                <input type="password" name="reset_npassword">
            </div>

            <div>
                <input type="hidden" name="reset_uuid" value="${curr_reset_uuid}">
                <input type="submit" value="Submit">
            </div>
        </form>
                
        <c:if test="${resetMessage}">
            <div>${resetMessage}</div>
        </c:if>
    
        <div>
            <a href="./login">Login</a>
        </div>
    </body>
</html>
