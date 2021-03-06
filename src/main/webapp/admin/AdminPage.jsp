<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<link rel="stylesheet" href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
<link href="../assets/style.css" rel="stylesheet">
<script src="//cdn.bootcss.com/jquery/1.11.3/jquery.min.js"></script>
<script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>实验安排列表</title>
    <style type="text/css">
        table{
            width: 600px;

            text-align: center;
            margin:0 auto;
        }
        tr{
            height: 100px;

        }
        body{

            margin: 0;
            padding: 0;
            font-family: sans-serif;
            background-size: cover;
            -webkit-background-size: cover;
            -o-background-size: cover;
            -moz-background-size: cover;
            -ms-background-size: cover;

        }
        .guider{
            width: 1280px;
            height: 40px;
            margin: 0px auto 0;
            font-size: large;
            color: black;
            border: solid rgba(85, 170, 255, 0.6);
            border-radius: 10px 10px 10px 10px;
        }
        table {
            border: 1px solid pink;
            margin: 0 auto;}
        .guider li{
            float:left;
            list-style: none;
            padding-right: 2px;
        }
        .guider li a{
            text-decoration: none;
            color: black;
            padding: 0 80px;
            height: 37px;
            line-height: 37px;
            display: block;
        }
        .guider li a:hover{
            background: #333333;
        }
    </style>

</head>
<body>
<div>

    <br >
    <br >
    <h1 align="center" style="color: darkorange" >实验安排</h1>

    <br>

    <div class="form-group">
        <form action="${pageContext.request.contextPath}/AdminOperateArrange" method="post">
            <label  class="control-label col-md-3 col-md-offset-5"></label>
            <div class="col-md-2">
                <input type="text" name="arrange_id" class="email form-control"placeholder="请输入实验id">
            </div>
            <input type="submit" class="btn btn-default col-md-0" value="搜索" class="btn" name = 'submit'>
        </form>
        <br />
        <label  class="control-label col-md-2 col-md-offset-5"></label>

        <a  class="btn btn-default col-md-1 col-md-offset-3" href="teacher/experimentadding.jsp" >增加实验安排</a>

    </div>

    <br >
    <br >
    <br >
    <table class="table table-hover" >
        <tr>
            <td><h3>arrange_id</h3></td>
            <td><h3>start</h3></td>
            <td><h3>end</h3></td>
            <td><h3>name_exp</h3></td>
            <td><h3>address</h3></td>
            <td><h3>teacher_id</h3></td>
            <td><h3>number_use</h3></td>
            <td><h3>isShared</h3></td>
            <td><h3>type</h3></td>
            <td><h3>week</h3></td>
            <td><h3>day</h3></td>
            <td><h3>operation</h3></td>

        </tr>
        <c:forEach items="${list1}" var="list1">
            <tr>
                <td  width="9%"><h4>${list1.getArrange_id()}</h4></td>
                <td  width="9%"><h4>${list1.getStart()}</h4></td>
                <td  width="9%"><h4>${list1.getEnd()}</h4></td>
                <td  width="9%"><h4>${list1.getName_exp()}</h4></td>
                <td  width="9%"><h4>${list1.getAddress()}</h4></td>
                <td  width="9%"><h4>${list1.getTeacher_id()}</h4></td>
                <td  width="9%"><h4>${list1.getNumber_use()}</h4></td>
                <td  width="9%"><h4>${list1.isShared()}</h4></td>
                <td  width="9%"><h4>${list1.getType()}</h4></td>
                <td  width="9%"><h4>${list1.getWeek()}</h4></td>
                <td  width="9%"><h4>${list1.getDay()}</h4></td>
                <td  width="9%">
                    <a class="btn btn-default col-md-0"href="${pageContext.request.contextPath}/AdminOperateArrange?id=${list1.getArrange_id()}&operatecode2=1 ">删除</a>
                    <a class="btn btn-default col-md-0"href="admin/updateArrange.jsp?id=${list1.getArrange_id()}">修改</a>
                </td>

            </tr>
        </c:forEach>
    </table>
    <a class="btn btn-default col-md-2 col-md-offset-5"href="AdminOperateArrange">返回实验信息管理主界面</a>

</div>
</body>
</html>


