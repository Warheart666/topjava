<%--
  Created by IntelliJ IDEA.
  User: WarhearT
  Date: 02.07.2018
  Time: 12:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<html>
<head>
    <title>Список еды</title>
</head>
<body>

<p><a href="mealList?action=insert">Добавить блюдо</a></p>
<table>
    <tr>
        <th>Дата</th>
        <th>Описание</th>
        <th>Калорийность</th>
        <th></th>
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
            <td>
                <a href="mealList?action=edit&mealId=<c:out value="${mealWithExceed.id}"/>">Редактировать</a>
            </td>
            <td>
                <a href="mealList?action=delete&mealId=<c:out value="${mealWithExceed.id}"/>">Удалить</a>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
