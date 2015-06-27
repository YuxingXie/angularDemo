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
    <li><a href="statics/test/angularjs/helloworld.html">helloworld</a></li>
    <li><a href="statics/test/angularjs/2.2simple_data_binding.html">2.2 数据绑定</a></li>
    <li><a href="statics/test/angularjs/2.3.html">2.3 最佳实践</a></li>
    <li><a href="statics/test/angularjs/3.html">3 模块</a></li>
    <li><a href="statics/test/angularjs/4.1.html">4.1 视图和$scope的世界</a></li>
    <li><a href="statics/test/angularjs/6.1.html">6.1解析AnjularJS表达式</a></li>
    <li><a href="statics/test/angularjs/6.2.html">6.2插值字符串</a></li>
    <li><a href="statics/test/angularjs/8.1.html">8.1指令：自定义HTML元素和属性</a></li>
    <li><a href="statics/test/angularjs/8.2.html">8.1指令：自定义HTML元素和属性</a></li>
    <li><a href="statics/test/angularjs/8.2.1.html">8.2.1 向指令中传递数据</a></li>
    <li><a href="statics/test/angularjs/8.2.2.html">8.2.2 来理解一下双向数据绑定</a></li>
    <li><a href="statics/test/angularjs/9.1.html">9.1 基础ng属性指令</a></li>
    <li><a href="statics/test/angularjs/9.2.html">9.2 在指令中使用子作用域</a></li>
    <li><a href="statics/test/angularjs/10.2.html">10.2 指令作用域</a></li>
    <li><a href="statics/test/angularjs/10.2.2.html">10.2.2 隔离作用域</a></li>
    <li><a href="statics/test/angularjs/10.3.html">10.3 绑定策略</a></li>
    <li><a href="statics/test/angularjs/10.4.html">10.4 AngularJS的生命周期</a></li>
    <li><a href="statics/test/angularjs/12.2test.html">12.2 布局模板</a></li>
    <li><a href="statics/test/angularjs/13.html">13 依赖注入</a></li>
    <li><a href="statics/test/angularjs/14.html">14 使用服务</a></li>
    <li><a href="statics/test/angularjs/15test.html">15 同外界通信：XHR和服务器通信</a></li>
    <li><a href="statics/test/angularjs/15test$resource.html">15 $resource</a></li>
    <li><a href="statics/test/angularjs/15test.Restangular.html">15 Restangular</a></li>
    <li><a href="statics/test/angularjs/16Test.html">16 XHR实践</a></li>
    <li><a href="statics/test/angularjs/17promise.html">17 promise</a></li>
    <li><a href="statics/test/angularjs/17promise.html">18 otherss</a></li>


</ul>
</body>
</html>
