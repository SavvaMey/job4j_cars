<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<html lang="en">
<head>
    <!-- Required meta tags -->
    <meta charset="utf-8">
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
    <script src="https://code.jquery.com/jquery-3.4.1.min.js" ></script>
    <script>

        function validate() {
            let result = true;
            let description = $('#description').val();
            let mark = $('#mark').val();
            let model = $('#model').val();
            let body = $('#body').val();
            alert(body);
            console.log(mark);
            let price = $('#price').val();

            if ((name === '') || (mark === null) || (model === '')
                || (body === null) || (price === '')) {
                alert('all input tables required');
                result = false;
            }
            if (!isNaN(parseFloat(price)) && isFinite(price)) {
                result = true;
            } else {
                result = false;
                alert('price required a numeric type')
            }
            return result;
        }
    </script>
    <title>Новое объявление</title>
</head>
<body>
<div class="container">
    <div class="row">
        <ul class="nav">
            <li class="nav-item">
                <a class="nav-link" href="<%=request.getContextPath()%>/index">Главная</a>
            </li>
            <li class="nav-item">
                <c:if test="${user.name == null}">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp">Войти</a>
                </c:if>
                <c:if test="${user.name != null}">
                    <a class="nav-link" href="<%=request.getContextPath()%>/login.jsp"> <c:out value="${user.name}"/> | Выйти</a>
                </c:if>
            </li>
        </ul>
    </div>
</div>
<div class="container pt-3">
    <div class="row">
        <div class="card" style="width: 100%">
            <div class="card-header">
                Создать объявление
            </div>
            <div class="card-body">
                <form action="<%=request.getContextPath()%>/advert" method="post"
                      enctype="multipart/form-data">
                    <div class="form-group">
                        <label for="description">Описание</label>
                        <input type="text" class="form-control" name="description" id="description">
                    </div>
                    <div class="form-group">
                        <select  id="mark" name="mark">
                            <option disabled selected>Выбрать марку</option>
                            <c:forEach items="${marks}" var="mark">
                                <option value="<c:out value="${mark.name}"/>"><c:out value="${mark.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="model">Модель</label>
                        <input type="text" class="form-control" name="model" id="model">
                    </div>
                    <div class="form-group">
                        <select  id="body"  required name="body" >
                            <option disabled selected>Выбрать тип кузова</option>
                            <c:forEach items="${bodies}" var="body">
                                <option value="<c:out value="${body.name}"/>"><c:out value="${body.name}"/></option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label for="price">Цена</label>
                        <input  type="text"  required class="form-control" name="price" id="price">
                    </div>
                    <div class="form-group">
                        <label for="photo">Фото</label>
                        <input type="file" name="photo" id="photo">
                    </div>
                    <div class="form-group">
                    <button type="submit" class="btn btn-primary" onclick="return validate();">
                        Сохранить
                    </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
