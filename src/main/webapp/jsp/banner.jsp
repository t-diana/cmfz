<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set value="${pageContext.request.contextPath}" var="app"/>
<style>
    #myH2{
        margin-top: -41px;
        margin-bottom: 10px;
    }
</style>
<script>
    $(function () {
        $("#bannerList").jqGrid({
            url: "${app}/Banner/showByPage",//获取数据的路径
            editurl: "${app}/Banner/bannerEdit",//修改数据的路径
            styleUI: "Bootstrap",
            datatype: "json",
            rownumbers: true,//是否显示行号。true：显示
            rownumWidth: "15px",// 行号所在列的宽度
            pager: "#tools",
            autowidth: true,//自适应父容器宽度
            viewrecords: true,
            multiselect: true,
            height:'300px',
            rowNum: 2,
            colNames: ["id", "title", "img", "create_date", "status"],
            colModel: [
                {name: 'id'},
                {name: 'title', editable: true},
                {
                    name: 'img', editable: true, edittype: 'file',
                    formatter: function (cellvalue, options, rowObject) {
                        return "<img style='width:100%;height:100px' src='${app}/img/" + cellvalue + "'>"
                    }
                },
                {name: 'create_date', editable: true, edittype: 'date'},
                {name: 'status', editable: true}
            ]
        }).jqGrid("navGrid", "#tools", {addtext: " 添加", edittext: " 修改", deltext: " 删除"},
            {
                /*修改相关*/
                closeAfterEdit: true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.id;
                    if (id == null) ;
                    else {
                        $.ajaxFileUpload({
                                url: "${app}/Banner/upload",
                                fileElementId: 'img',
                                data: {id: id},
                                type: "post",
                                success: function () {
                                    $("#bannerList").trigger("reloadGrid")
                                    $("#tip").show();
                                    setTimeout(function () {
                                        $("#tip").hide();
                                    }, 3000)
                                }
                            }
                        )
                    }
                    return response;
                }
            },
            {
                /*添加相关*/
                closeAfterAdd: true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.id;

                    $.ajaxFileUpload({
                            url: "${app}/Banner/upload",
                            fileElementId: 'img',
                            data: {id: id},
                            type: "post",
                            success: function () {
                                $("#bannerList").trigger("reloadGrid")
                                $("#tip").show();
                                setTimeout(function () {
                                    $("#tip").hide();
                                }, 3000)
                            }
                        }
                    )
                    return response;
                }
            },
            {
                /*删除相关*/
                closeAfterDel: true
            }
        )
    })
    function portBanner() {
        window.location.href="${app}/Banner/easyPoiPort?name=banner.xls";
    }
</script>
<div class="page-header">
    <h2 id="myH2">轮播图管理</h2>
</div>

<div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">轮播图信息</a></li>
        <li role="presentation" class="active"><button  onclick="portBanner();"class="btn bg-info" role="tab" data-toggle="tab">导出轮播图信息表格</button></li>
    </ul>
    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
            <table id="bannerList"></table>
            <div id="tools" style="height: 50px;"></div>
        </div>
    </div>
</div>
<div id="tip" style="display: none;">添加成功</div>