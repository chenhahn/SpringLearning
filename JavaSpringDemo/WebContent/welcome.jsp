<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome!</title>
</head>
<body>
	<%
		String messages[] = (String[]) request.getAttribute("myWelcomeMessage");
		String welcomeMessage = "";
		for(String msg : messages){
			welcomeMessage += msg + " ";
		}
		out.print(welcomeMessage);
	%>

</body>
</html>