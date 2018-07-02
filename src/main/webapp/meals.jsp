<%--
  Created by IntelliJ IDEA.
  User: WarhearT
  Date: 02.07.2018
  Time: 12:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Список еды</title>
</head>
<body>


<table>
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калорийность</th>
    </tr>
    <c:forEach var="mealWithExceed" items="${mealListWithExceed}">
        <tr style="background: ${mealWithExceed.exceed ? 'red' : 'green'}">
            <td>
                <javatime:format value="${mealWithExceed.dateTime}" style="MS"/>
            </td>

            <td>
                <c:out value="${mealWithExceed.description}"/>
            </td>
            <td>
                <c:out value="${mealWithExceed.calories}"/>
            </td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
