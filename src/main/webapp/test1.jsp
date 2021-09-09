<%--
  Created by IntelliJ IDEA.
  User: Huang Jun
  Date: 2021/8/17
  Time: 14:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>

<html>
<script type="text/javascript">

    $.ajax({
        url : "",
        data : {

        },
        type : "",
        dataType : "json",
        success : function (data){

        }
    })

    String createTime = DateTimeUtil.getSysTime();
    String createBy = ((User)request.getSession().getAttribute("user")).getName();

    $(".time").datetimepicker({
        minView: "month",
        language:  'zh-CN',
        format: 'yyyy-mm-dd',
        autoclose: true,
        todayBtn: true,
        pickerPosition: "bottom-left"
    });

</script>


<head>
    <base href="<%=basePath%>">
    <title>Title</title>
</head>
<body>

</body>
</html>
