<jsp:include page="./correct.jsp" />
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Correct address</title>
    <link rel="stylesheet" type="text/css" href="https://stackpath.bootstrapcdn.com/bootswatch/4.3.1/cosmo/bootstrap.min.css"/>
</head>
<body>
<h1>Correct address</h1>
<form action="correctAddress"method="post">
    <table>
        <tr>
            <td>Country:</td>
            <td><input type="text" name="country" /></td>
        </tr>
        <tr>
            <td>County:</td>
            <td><input type="text" name="county" /></td>
        </tr>
        <tr>
            <td>City:</td>
            <td><input type="text" name="city" /></td>
        </tr>
        <tr>
            <td>Postal code:</td>
            <td><input type="number" name="postalCode" /></td>
        </tr>
        <tr>
            <td>Street address</td>
            <td><input type="text" name="streetAddress" /></td>
        </tr>
     </table>
    <input type="submit" value="Correct" /></form>
</body>
</html>
