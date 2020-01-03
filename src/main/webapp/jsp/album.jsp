<%@page isELIgnored="false" pageEncoding="UTF-8" contentType="text/html; UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="app" value="${pageContext.request.contextPath}"/>
<style>
    #myH2{
        margin-top: -41px;
        margin-bottom: 10px;
    }
</style>
<script>
    $(function () {
        $("#albumList").jqGrid({
            url: "${app}/Album/AlbumShowByPage",
            editurl: "",
            rownumbers: true,//是否显示行号。true：显示
            rownumWidth: "15px",// 行号所在列的宽度
            datatype: "json",
            pager: "#pager",
            styleUI: "Bootstrap",
            height: "400px",
            caption: "专辑",
            autowidth: true,
            rowNum: 5,
            viewrecords: true,
            colNames: ["id", "title", "img", "score", "author", "broadcaster", "count", "brief", "createDate", "status"],
            colModel: [
                {name: "id"},
                {name: "title"},
                {
                    name: "img", formatter: function (cellvalue, options, rowObject) {
                        return "<img style='width:100%;height:100px' src='${app}/img/" + cellvalue + "'>"
                    }
                },
                {name: "score"},
                {name: "author"},
                {name: "broadcaster"},
                {name: "count"},
                {name: "brief"},
                {name: "createDate"},
                {name: "status"}
            ],
            subGrid: true,//开启子表格
            subGridRowExpanded: function (subGridId, subGridRowId) {
            //subGrid是子表格的id也是父表格的行id      subGridRowId 是子表格的行id
                addSubGrid(subGridId, subGridRowId);
            }
        }).jqGrid("navGrid", "#pager", {addtext: " 添加", edittext: " 修改", deltext: " 删除"})
    })

    //添加子表格
    function addSubGrid(subGridId, subGridRowId) {
        var albumId = {"albumId": subGridRowId};
        var subGridTabledId = subGridId + "table";//动态添加 table的id
        var subGridDivId = subGridId + "div";//动态添加div的表格
        //动态添加子表格
        $("#" + subGridId).html(
            "<table id='" + subGridTabledId + "'></table>" +
            "<div id='" + subGridDivId + "' style='height: 50px'></div>"
        )
        $("#" + subGridTabledId).jqGrid({
            url: "${app}/Chapter/showChapterAll?albumId=" + subGridRowId,
            editurl: "${app}/Chapter/chapterEdit?albumId=" + subGridRowId,
            styleUI: "Bootstrap",
            datatype: "json",
            /* data:{"albumId":subGridRowId},*/
            records: true,
            autowidth: true,
            rownumbers: true,//是否显示行号。true：显示
            rownumWidth: "15px",// 行号所在列的宽度
            multiselect:true,
            rowNum: 2,
            caption: "章节",
            toolbar: [true, "top"],
            pager: "#" + subGridDivId,
            colNames: ["id", "title", "albumId", "size", "duration", "src", "status"],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {name: "albumId"},
                {name: "size"},
                {name: "duration"},
                {
                    name: "src", editable: true, edittype: 'file'
                },
                {name: "status", editable: true}
            ],
        }).jqGrid("navGrid", "#" + subGridDivId, {addtext: " 添加", edittext: " 修改", deltext: " 删除"},
            {
                //修改相关
                closeAfterEdit:true,
                afterSubmit: function (response) {
                    var id = response.responseJSON.chapterId;
                    if (id != null) {
                        $.ajaxFileUpload({
                                url: "${app}/Chapter/chapterUpload",
                                fileElementId: 'src',
                                data: {id: id},
                                type: "post",
                                success: function () {
                                    $("#" + subGridTabledId).trigger("reloadGrid")
                                    alert("修改成功")
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
                    var id = response.responseJSON.chapterId;
                    if (id != null) {
                        $.ajaxFileUpload({
                                url: "${app}/Chapter/chapterUpload",
                                fileElementId: 'src',
                                data: {id: id},
                                type: "post",
                                success: function () {
                                    $("#" + subGridTabledId).trigger("reloadGrid")
                                    alert("添加成功")
                                }
                            }
                        )
                    }
                    return response;
                }
            },
            {}
        )
        $("#t_" + subGridTabledId).html(
            "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "<button class='btn bg-info' onclick=\"playMusic('" + subGridTabledId + "')\">Player <span class='glyphicon glyphicon-play-circle'></spanc></button>" + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" +
            "<button class='btn bg-warning' data-toggle=\"modal\" data-target=\"#myModal\" onclick=\"downFile('" + subGridTabledId + "')\">DownLoader <span class='glyphicon glyphicon-download-alt'></span></button>"
        )
    }

    //播放
    function playMusic(subGridTableId) {
        var gr = $("#" + subGridTableId).jqGrid("getGridParam", "selrow");
        var data = $("#" + subGridTableId).jqGrid("getRowData", gr);
        if (gr == null) {
            alert("请选择要播放的章节")
        } else {
            //2.jqgrid 提供的方法 根据id拿到对应的值
            $("#fuckModal").modal('show')
            var music = document.getElementById("fuckAudio");
            music.setAttribute("src", "${app}/mp3/" + data.src)
            $("#audioBtn").click(function () {
                if (music.paused) {
                    music.play();
                    $("#audioBtn").removeClass("pause").addClass("play");
                } else {
                    music.pause();
                    $("#audioBtn").removeClass("play").addClass("pause");
                }
            });
        }
    }

    //文件下载
    function downFile(subGridTableId) {
        var gr = $("#" + subGridTableId).jqGrid("getGridParam", "selrow");
        var data = $("#" + subGridTableId).jqGrid("getRowData", gr);
        if (gr == null) {
            alert("请选择要下载的章节")
        } else {
            location.href="${app}/Chapter/download?src="+data.src;
        }

    }
</script>

<div class="modal fade" id="fuckModal" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">Modal title</h4>
            </div>
            <div class="modal-body">
                <audio controls src="" id="fuckAudio"></audio>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<div class="page-header">
    <h2 id="myH2">轮播图管理</h2>
</div>
<div>

    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">专辑与章节信息</a></li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
        <div role="tabpanel" class="tab-pane active" id="home">
            <table id="albumList"></table>
            <div id="pager" style="height: 50px"></div>
        </div>
    </div>
</div>
