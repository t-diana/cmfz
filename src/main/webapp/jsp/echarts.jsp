<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<C:set var="app" value="${pageContext.request.contextPath}"/>
<html lang="en">
<head>
    <script src="${app}/js/jquery-3.3.1.js"></script>
    <script src="${app}/echarts/echarts.min.js"></script>
    <script src="${app}/echarts/china.js"></script>
</head>
<body>
<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
<div id="main" style="width: 600px;height:400px;"></div>
<script type="text/javascript">
    $(function () {
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '用户地区分布图'
            },
            tooltip: {},
            series: [{
                type: 'map',
                map: 'china'}
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        $.ajax({
            url:"${app}/User/Location",
            type:"get",
            datatype:"json", //dataType 会进入ajax错误函数回调
            success:function (s) {
                myChart.setOption({
                    series: [{
                        type: 'map',
                        map: 'china',
                        data:s
                    }
                    ]
                });
            }
        })

    })
</script>
</body>
</html>