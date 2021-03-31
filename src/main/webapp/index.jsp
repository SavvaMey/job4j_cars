<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>
<%@ page import="model.Ad" %>
<%@ page import="model.User" %>
<%@ page import="java.text.DateFormat" %>
<!doctype html>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <%@page contentType="text/html; charset=UTF-8" %>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css"
          integrity="sha384-Vkoo8x4CGsO3+Hhxv8T/Q5PaXtkKtu6ug5TOeNV6gBiFeWPGFN9MuhOf23Q9Ifjh" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js"
            integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js"
            integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js"
            integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <title>Объявления на продажу</title>
</head>

<body>
<div class="container">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
    <c:if test="${user.name == null}">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login">Войти</a>
            </li>
    </c:if>
    <c:if test="${user.name != null}">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/login"> <c:out value="${user.name}"/> | Выйти</a>
            </li>
        <li class="nav-item">
            <a class="nav-link" href="<%=request.getContextPath()%>/advert">Добавить объявление</a>
        </li>
    </c:if>
        </ul>
    </div>
</div>
<div class="container pt-3">
    <div class="row">
        <form action="<%=request.getContextPath()%>/index" method="get">
            <div class="form-group">
                <button type="submit" class="btn btn-primary" name="chooseList" value="AllCars">Все машины</button>
            </div>
        </form>
        <form action="<%=request.getContextPath()%>/index" method="get">
            <div class="form-group">
                <button type="submit" class="btn btn-primary" name="chooseList" value="CarsToday">Объявления сегодня</button>
            </div>
        </form>
        <form action="<%=request.getContextPath()%>/index" method="get">
            <div class="form-group" >
                <button type="submit" class="btn btn-primary"  name="chooseList" value="CarsPhoto">
                    Объявление с фото</button>
            </div>
        </form>
            <form action="<%=request.getContextPath()%>/index" method="get">
                <div class="form-group" >
                    <select  id="marksValue" name="marksValue" onchange="this.form.submit()">
                        <option disabled selected>Выбор по марке</option>
                        <c:forEach items="${marks}" var="mark">
                        <option value="<c:out value="${mark.name}"/>"><c:out value="${mark.name}"/></option>
                        </c:forEach>
                    </select>
                </div>
            </form>
        <form action="<%=request.getContextPath()%>/index" method="get">
            <div class="form-group" >
                <select  id="bodiesValue" name="bodiesValue" onchange="this.form.submit()">
                    <option disabled selected>Выбор по кузову</option>
                    <c:forEach items="${bodies}" var="body">
                        <option value="<c:out value="${body.name}"/>"><c:out value="${body.name}"/></option>
                    </c:forEach>
                </select>
            </div>
        </form>
    </div>
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Объявления
            </div>
            <div class="card-body">
                <table class="table">
                    <thead>
                    <tr>
                        <th scope="col">Описание</th>
                        <th scope="col">Фото</th>
                        <th scope="col">Статус</th>
                        <th scope="col">Пользователь</th>
                        <th scope="col">Дата</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${ads}" var="ad">
                        <tr>
                            <td>
                                <ul>
                                    <li> <c:out value="${ad.description}"/></li>
                                    <li> <c:out value="${ad.car.mark.name}"/></li>
                                    <li> <c:out value="${ad.car.model}"/></li>
                                    <li> <c:out value="${ad.car.body.name}"/></li>
                                    <li> <c:out value="${ad.car.price}"/></li>
                                </ul>
                            </td>
                            <td>
                                <img src="<c:url value='/download?name=${ad.photo.path}'/>" width="200px"
                                     height="200px"/>
                            </td>
                            <td>
                                <c:if test="${ad.sold == true}">
                                  <p>  Продано  </p>
                                </c:if>
                                <c:if test="${ad.sold == false}">
                                <p> Активно </p>
                                    <c:if test="${ad.user.email == user.email}">
                                <form action='<%=request.getContextPath()%>/index?id=<c:out value="${ad.id}"/>' method="post">
                                    <button type="submit" class="btn btn-primary">Изменить на статус: Продано</button>
                                </form>
                                    </c:if>
                                </c:if>
                            </td>
                            <td>
                              <p><c:out value="${ad.user.name}"/></p>
                            </td>
                            <td>
                                <p> <fmt:formatDate value="${ad.created}" type="both" dateStyle="long" /><p>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</div>
</body>
</html>