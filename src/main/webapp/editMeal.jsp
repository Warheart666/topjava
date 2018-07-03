<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://sargue.net/jsptags/time" prefix="javatime" %>
<%@ page contentType="text/html;charset=UTF-8"%>
<%--
  Created by IntelliJ IDEA.
  User: WarhearT
  Date: 02.07.2018
  Time: 18:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8"%>
<html>
<head>
    <title>Редактирование блюда</title>
</head>
<body>

<c:set var="meal" value="${meal}"/>
<form method="POST">
    id : <label type="text" name="id" text="<c:out value="${meal.id}"/>"/>
    <br/>
    Описание : <input type="text" name="description" value="<c:out value="${meal.description}"/>"/>
    <br/>
    Дата/время : <input type="text" name="dateTime"
                        value="<javatime:format value="${meal.dateTime}" style="MS"/>"/>
    <br/>
    Калории : <input type="text" name="calories" value="<c:out value="${meal.calories}"/>"/> <br/>

    <input type="submit" value="Submit"/>
</form>
</body>
</html>
