<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="<%=request.getContextPath() %>"/>
<c:if test="${path eq '/'}"><c:set var="path" value=""/></c:if>
<html>
<head>
</head>
<body>
<h1>angularJS DEMO</h1>
<ul>
    <li>
        <a href="statics/test/angularjs/helloworld.html">helloworld</a>
    </li>
    <li><a href="statics/test/angularjs/2.2simple_data_binding.html">2.2 数据绑定</a></li>
    <li><a href="statics/test/angularjs/2.3.html">2.3 最佳实践</a></li>
    <li><a href="statics/test/angularjs/3.html">3 模块</a></li>
    <li><a href="statics/test/angularjs/4.1.html">4.1 视图和$scope的世界</a></li>
    <li><a href="statics/test/angularjs/6.1.html">6.1解析AnjularJS表达式</a></li>
    <li><a href="statics/test/angularjs/6.2.html">6.2插值字符串</a></li>

</ul>
</body>
</html>
