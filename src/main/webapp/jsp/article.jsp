<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html; UTF-8" pageEncoding="UTF-8" isELIgnored="false" %>
<c:set value="${pageContext.request.contextPath}" var="app"/>
<script>
    $(function () {
        var editor = KindEditor.create("textarea[name=content]", {
            minWidth: "500px",
            themeType: 'simple',
            resizeType: 0,
            langType: 'zh-CN',
            afterBlur: function () {
                this.sync();
            },

            allowFileManager: true,
            uploadJson: '${app}/Article/kindeditor',//文件（图片）上传的位置
            fileManagerJson: '${app}/Article/getAllImages',//图片空间
            filePostName: "img",//文件上传所在的form表单名称 也是图片的名称
        });
        $("#articleList").jqGrid({
            url: "${app}/Article/showArticleAll",
            editurl: "${app}/Article/edit",
            styleUI: "Bootstrap",
            autowidth: true,
            datatype: "json",
            rownumbers: true,//是否显示行号。true：显示
            rownumWidth: "15px",// 行号所在列的宽度
            pager: "#articlePager",
            multiselect: true,
            height: "400px",
            caption: "文章",
            colNames: ["id", "title", "content", "guruId", "createDate", "status", "operation"],
            colModel: [
                {name: "id"},
                {name: "title", editable: true},
                {name: "content", editable: true},
                {name: "guruId"},
                {name: "createDate", editable: true},
                {name: "status", editable: true},
                {
                    name: "operation",
                    formatter: function () {
                        return "<a onclick='showDate()'><span class='glyphicon glyphicon-th-list'></span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<a " +
                            " onclick=\"showUpdateRowDate()\"><span class='\n" +
                            "glyphicon glyphicon-pencil'></span><a>"
                    }
                }
            ]
        }).jqGrid("navGrid", "#articlePager", {
            addtext: " 添加",
            edittext: " 修改",
            deltext: " 删除",
            edit: false,
            add: false,
            del: true,
            search: true
        })

    })


    //进行表格的数据添加
    function addArticle() {
        var article = $("#articleAdd").serialize();
        $.ajax({
                url: "${app}/Article/addArticle",
                datatype: "json",
                data: article,
            }
        )
        $("#articleList").trigger("reloadGrid");//刷新表格
        $("#myModal").modal('hide')
    }

    //回显数据
    function showDate() {
        var id = $('#articleList').jqGrid('getGridParam', 'selrow');
        if (id == null) {
            alert("请选择一条数据")
        } else {
            $("#update_modal_footer").prop("style", "display: none;")
            $("#UpdateArticle")[0].reset();//form表单
            var rowData = $("#articleList").jqGrid('getRowData', id);
            $("#updateId").val(rowData.id);
            $("#UpdateTitle").val(rowData.title);
            $("#UpdateCreateDate").val(rowData.createDate);
            console.log(rowData);
            //清空之前富文本编辑器中保存内容
            KindEditor.html("#update_id", null);//文本域
            KindEditor.appendHtml("#update_id", rowData.content);
            $("#UpdateModel").modal('show')
        }
    }

    //展示修改文章信息
    function showUpdateRowDate() {
        showDate();
        $("#update_modal_footer").prop("style", null)
    }

    //修改文章
    function updateRowDate() {
        var article = $("#UpdateArticle").serialize();

        $.post(
            "${app}/Article/updateArticle",
            article,
            "json"
        )
        $("#UpdateModel").modal('hide');
        $("#articleList").trigger("reloadGrid");//刷新表格
    }
    //展示模态框
    function open(){
        $("#myModal").modal('show')
    }
</script>

<div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">添加文章sss</h4>
            </div>
            <form id="articleAdd">
                <div class="modal-body">
                    <div class="form-group">
                        <label for="exampleInputTitle1">title</label>
                        <input type="text" name="title" class="form-control" id="exampleInputTitle1"
                               placeholder="Title">
                    </div>
                    <div class="form-group">
                        <label for="editor_id">content</label>
                        <textarea id="editor_id" name="content" style="width:575px;height:352px;">
                        </textarea>
                    </div>
                    <div class="form-group">
                        <label for="exampleInputCreateDate1">createDate</label><br/>
                        <input type="date" name="createDate" class="form-control" id="exampleInputCreateDate1"
                               placeholder="CreateDate">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="addArticle();">添加</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>
<!-- /.modal -->

<%--修改文章数据--%>
<div class="modal fade" id="UpdateModel" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">修改文章</h4>
            </div>
            <form id="UpdateArticle">
                <input type="hidden" name="id" id="updateId"/>
                <div class="modal-body">
                    <div class="form-group">
                        <label for="UpdateTitle">title</label>
                        <input type="text" name="title" class="form-control" id="UpdateTitle"
                               placeholder="Title">
                    </div>
                    <div class="form-group">
                        <label for="update_id">content</label>
                        <textarea id="update_id" name="content" style="width:575px;height:352px;">
                        </textarea>
                    </div>
                    <div class="form-group">
                        <label for="UpdateCreateDate">createDate</label><br/>
                        <input type="date" name="createDate" class="form-control" id="UpdateCreateDate"
                               placeholder="CreateDate">
                    </div>
                </div>
                <div class="modal-footer" id="update_modal_footer" style="display: none;">
                    <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" onclick="updateRowDate();">update</button>
                </div>
            </form>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div>

<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab"
                                                  data-toggle="tab">展示文章</a></li>
        <li role="presentation"><button type="button" class="btn btn-primary btn-lg" data-toggle="modal" data-target="#myModal">
            Launch demo modal
        </button></li>
    </ul>

    <!-- Tab panes -->
    <div class="tab-content">
        <div class="tab-pane active" id="home">
            <table id="articleList"></table>
            <div id="articlePager" style="height: 50px"></div>
        </div>
    </div>
</div>