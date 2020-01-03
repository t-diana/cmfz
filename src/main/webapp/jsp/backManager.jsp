<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"/>
<html>
<head>
    <%--css--%>
    <%--bootstrap--%>
    <link rel="stylesheet" href="${app}/boot/css/bootstrap.css">
    <link rel="stylesheet" href="${app}/boot/css/bootstrap-theme.css">
    <%--jqgrid--%>
    <link rel="stylesheet" href="${app}/jqgrid/css/trirand/ui.jqgrid-bootstrap4.css">
    <%--kindeditor富文本编辑器--%>
    <link rel="stylesheet" href="${app}/kindeditor/themes/simple/simple.css">
    <%--js--%>
    <script src="${app}/js/jquery-3.3.1.js"></script>
    <%--bootstrap--%>
    <script src="${app}/boot/js/bootstrap.js"></script>
    <script src="${app}/boot/js/ajaxfileupload.js"></script>
    <script src="${app}/jqgrid/js/trirand/i18n/grid.locale-cn.js"></script>
    <script src="${app}/jqgrid/js/trirand/src/jquery.jqGrid.js"></script>
    <script src="${app}/kindeditor/lang/zh-CN.js"></script>
    <script src="${app}/kindeditor/kindeditor-all.js"></script>
    <script src="${app}/echarts/echarts.min.js"></script>
    <script src="${app}/echarts/china.js"></script>
    <script type="text/javascript" src="https://cdn.goeasy.io/goeasy-1.0.3.js"></script>
    <script>
        $(function () {
            // 基于准备好的dom，初始化echarts实例
            var myChart = echarts.init(document.getElementById('main'));
            // 指定图表的配置项和数据
            var option = {
                title: {
                    text: '七天注册人数'
                },
                tooltip: {},
                legend: {
                    data: ['注册人数']
                },
                xAxis: {
                    data: ["1", "2", "3", "4", "5", "6", "7"]
                },
                yAxis: {}
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);

            //12个月注册人数
            // 基于准备好的dom，初始化echarts实例
            var ss = echarts.init(document.getElementById('month'));
            // 指定图表的配置项和数据
            var emm = {
                title: {
                    text: '12个月注册人数'
                },
                tooltip: {},
                legend: {
                    data: ['注册人数']
                },
                xAxis: {
                    data: ["1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"]
                },
                yAxis: {}
            };
            // 使用刚指定的配置项和数据显示图表。
            ss.setOption(emm);
            $.ajax({
                url: "${app}/Banner/showActive",
                success: function (data) {
                    $.each(data, function (index, value) {
                        if (index == 0)
                            $("#banner").append($("<div class='item active'><img src='${app}/img/" + value.img + "'></div>"))
                        else
                            $("#banner").append($("<div class='item'><img src='${app}/img/" + value.img + "'></div>"))
                    })
                }
            })
            //七天注册人数初始数据
            $.ajax({
                url: "${app}/User/goeasy",
                datatype: "json",
                success: function (s) {
                    myChart.setOption({
                        series: [{
                            name: '注册人数',
                            type: 'bar',
                            data: s
                        }]
                    });
                }
            })
            //12个月初始数据
            $.ajax({
                url: "${app}/User/goeasyMonth",
                datatype: "json",
                success: function (s) {
                    ss.setOption({
                        series: [{
                            name: '注册人数',
                            type: 'line',
                            data: s
                        }]
                    });
                }
            })
            var goEasy = new GoEasy({
                host: 'hangzhou.goeasy.io', //应用所在的区域地址: 【hangzhou.goeasy.io |singapore.goeasy.io】
                appkey: "BS-172bf1a3efa1482d9364f61e2923bb62", //替换为您的应用appkey
            });
            goEasy.subscribe({
                channel: "180_Channel", //替换为您自己的channel
                onMessage: function (message) {
                    $.ajax({
                        url: "${app}/User/goeasy",
                        datatype: "json",
                        success: function (s) {
                            myChart.setOption({
                                series: [{
                                    name: '注册人数',
                                    type: 'bar',
                                    data: s
                                }]
                            });
                        }
                    })
                    $.ajax({
                        url: "${app}/User/goeasyMonth",
                        datatype: "json",
                        success: function (s) {
                            ss.setOption({
                                series: [{
                                    name: '注册人数',
                                    type: 'line',
                                    data: s
                                }]
                            });
                        }
                    })
                }
            });
        })

        function downExcel() {
            window.location.href = "${app}/Admin/poiExcel?name=adminExcel.xls";
        }

        function addUser() {
            var user = $("#adduser").serialize();
            console.log(user);
            $.ajax({
                    url: "${app}/User/register",
                    data: user,
                    datatype: "json"
                }
            )
            $("#adduser")[0].reset();
            $("#myModal").modal('hide');
        }
    </script>
</head>
<body>

<div class="modal fade" id="userModal" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">add user</h4>
            </div>
            <form id="adduser">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="nsame">name</label>
                        <input type="text" name="name" class="form-control" id="nsame"
                               placeholder="username">
                    </div>
                    <div class="form-group">
                        <label for="exampleInputCreateDate1">create_date</label><br/>
                        <input type="date" name="createDate" class="form-control" id="exampleInputCreateDate1"
                               placeholder="CreateDate">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="addUser();">添加</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

<nav class="navbar navbar-default">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
                    data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">持明法州管理系统</a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav navbar-right">
                <li><a>欢迎</a></li>
                <li><a onclick="downExcel()">导出Admin信息到表格 <span class="glyphicon glyphicon-cloud-download"></span></a>
                </li>
                <li class="nav navbar-nav navbar-right">
                    <a href="#">退出登录 <span class="glyphicon glyphicon-log-out"></span></a>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>
<%--手风琴--%>
<div class="col-sm-2">
    <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingOne">
                <h4 class="panel-title">
                    <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne"
                       aria-expanded="true" aria-controls="collapseOne">
                        用户管理
                    </a>
                </h4>
            </div>
            <div id="collapseOne" class="panel-collapse collapse " role="tabpanel" aria-labelledby="headingOne">
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><a>用户展示</a></li>
                        <li class="list-group-item"><a href="#profile" aria-controls="profile" role="tab"
                                                       data-toggle="modal"
                                                       data-target="#userModal">注册用户</a></li>
                    </ul>
                </div>
            </div>
        </div>

        <%--单独一个手风琴--%>
        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingTwo">
                <h4 class="panel-title">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseTwo">
                        上师管理
                    </a>
                </h4>
            </div>
            <div id="collapseTwo" class="panel-collapse collapse">
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><a>用户展示</a></li>
                    </ul>
                </div>
            </div>
        </div>


        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingThree">
                <h4 class="panel-title">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseThree">
                        文章管理
                    </a>
                </h4>
            </div>
            <div id="collapseThree" class="panel-collapse collapse">
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><a
                                href="javascript:$('#bannerTable').load('${app}/jsp/article.jsp')">文章管理</a></li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingFour">
                <h4 class="panel-title">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseFour">
                        专辑管理
                    </a>
                </h4>
            </div>
            <div id="collapseFour" class="panel-collapse collapse">
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><a href="javascript:$('#bannerTable').load('${app}/jsp/album.jsp')">专辑管理</a>
                        </li>
                    </ul>
                </div>
            </div>
        </div>

        <div class="panel panel-default">
            <div class="panel-heading" role="tab" id="headingFive">
                <h4 class="panel-title">
                    <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion"
                       href="#collapseFive">
                        轮播图管理
                    </a>
                </h4>
            </div>
            <div id="collapseFive" class="panel-collapse collapse">
                <div class="panel-body">
                    <ul class="list-group">
                        <li class="list-group-item"><a
                                href="javascript:$('#bannerTable').load('${app}/jsp/banner.jsp')">轮播图管理</a></li>
                    </ul>
                </div>
            </div>
        </div>

    </div>
</div>

<div class="col-sm-10" id="bannerTable">
    <div class="jumbotron">
        <div class="col-sm-offset-1">

            <h3 style="color: gray">欢迎来到持明法州后台管理系统</h3>
        </div>
    </div>

    <%--轮播图--%>
    <div id="carousel-example-generic" class="carousel slide text-center" style="width: 1280px;height: 720px"
         data-ride="carousel">

        <!-- Wrapper for slides -->
        <div class="carousel-inner" id="banner" role="listbox">
        </div>

        <!-- Controls -->
        <a class="left carousel-control" href="#carousel-example-generic" data-slide="prev">
            <span class="glyphicon glyphicon-chevron-left"></span>
            <span class="sr-only">Previous</span>
        </a>
        <a class="right carousel-control" href="#carousel-example-generic" data-slide="next">
            <span class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
            <span class="sr-only">Next</span>
        </a>
    </div>
    <%--统计图数据--%>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="main" style="width: 600px;height:400px;"></div>
    <%--统计图数据--%>
    <!-- 为ECharts准备一个具备大小（宽高）的Dom -->
    <div id="month" style="width: 600px;height:400px;"></div>
</div>
<%--<nav class="navbar navbar-default navbar-fixed-bottom">--%>
<%--    <div class="container text-center" style="padding-top: 15px">--%>
<%--        <h4>@Hades_Diana</h4>--%>
<%--    </div>--%>
<%--</nav>--%>
</body>
</html>