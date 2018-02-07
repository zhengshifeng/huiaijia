<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8" isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>邀请管理</title>
	<jsp:include page="../common/public_js_css.jsp" flush="true"/>
	<script type="text/javascript"
			src="../../../style/plugins/datePicker/WdatePicker.js"></script>
	<style>
		div.filter > .inpMain, div.filter > select {
			margin-right: 20px;
		}

		.btn-green {
			background: green;
		}


	</style>
</head>
<body style="background-color: #FFFFFF;">
<div class="mainBox">
	<h3>邀请管理</h3>
	<form action="/invitations/list.html" method="post" name="searchForm"
		  id="searchForm">
		<div class="filter">
			邀请人：<input class="inpMain short" type="text" name="inviter"
					   id="invitePerson" placeholder="邀请人" value="${inviter}"/>
			邀请码：<input type="text" class="inpMain short " name="inviteCode"
					   style="min-width: 70px;" value="${inviteCode}" placeholder="邀请码"/>
			邀请状态：<select name="status" id="status" style="vertical-align: middle;">
			<option value="">请选择</option>
			<option value="1" <c:if test="${status==1}">selected</c:if>>成功</option>
			<option value="0" <c:if test="${status==0}">selected</c:if>>未成功</option>
		</select>
			邀请时间：<select name="ascOrDesc" id="ascOrDesc"
						 style="vertical-align: middle;">
			<option value="">请选择</option>
			<option value="desc" <c:if test="${ascOrDesc=='desc'}">selected</c:if>>从早到晚</option>
			<option value="asc" <c:if test="${ascOrDesc=='asc'}">selected</c:if>>从晚到早</option>
		</select> <input class="btn" type="submit" value="搜索"/>
		</div>
	</form>
	<div id="list">
		<form name="deleteForm" id="deleteForm">
			<table style="width: 100%;" class="tableBasic list tablist">
				<tr>
					<th style="width:60px;">
						<label><input type="checkbox" class="checkAll">全选</label>
					</th>
					<th>邀请人ID</th>
					<th>邀请码</th>
					<th>被邀请人ID</th>
					<th>邀请时间</th>
					<th>是否成功</th>
					<th>操作</th>
				</tr>
				<c:choose>
					<c:when test="${not empty page.content }">
						<c:forEach items="${page.content}" var="mi">
							<tr>
								<td>
									<input type="checkbox" class="checkSub" value="${mi.id}">
								</td>
								<td>${mi.inviter}</td>
								<td>${mi.inviteCode}</td>
								<td>${mi.invitee}</td>
								<td><fmt:formatDate value="${mi.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
								<td><c:if test="${mi.status==0}">未成功</c:if><c:if test="${mi.status==1}">成功</c:if></td>
								<td><c:if test="${mi.optStatus==0}">未处理</c:if><c:if
										test="${mi.optStatus==1}">已处理</c:if></td>
							</tr>
						</c:forEach>
					</c:when>
					<c:otherwise>
						<tr class="main_info">
							<td colspan="7">小橙没能给主子找到您要的数据o(&gt;﹏&lt;)o</td>
						</tr>
					</c:otherwise>
				</c:choose>
			</table>
		</form>
	</div>
	<div class="clear"></div>
	<div class="page_and_btn">
		<div class="action">

			调整为：<select name="optStatus" id="optStatus"
						style="vertical-align: middle;">
			<option value="">请选择</option>
			<option value="1">已处理</option>
			<option value="0">未处理</option>
		</select> <input id="btn_optStatus" class="btn btn-green" type="button" value="确认调整"/>
			<input type="button" id="btn_statistics" class="btn" value="统计" title="统计">
			<b id="invite_userId" style="padding-right: 15px;">邀请人用户id：0</b>
			<b id="invite_number" style="padding-right: 15px;">邀请人数：0人</b>
			<b id="invite_suc_number" style="padding-right: 15px;">成功邀请：0人</b>
		</div>
		<ul class="page">
			<li class="pageinfo">
				${page.currentPage}/${page.pageTotal}
			</li>
			<li class="pageinfo">
				<a href="${pageContext.request.contextPath}/invitations/list.html?currentPage=${page.firstPage}&pageSize=${page.pageSize}&inviter=${inviter}&inviteCode=${inviteCode}&status=${status}&ascOrDesc=${ascOrDesc}">首页</a>
			</li>
			<li class="pageinfo">
				<a href="${pageContext.request.contextPath}/invitations/list.html?currentPage=${page.previousPage}&pageSize=${page.pageSize}&inviter=${inviter}&inviteCode=${inviteCode}&status=${status}&ascOrDesc=${ascOrDesc}">上一页</a>
			</li>
			<li class="pageinfo">
				<a href="${pageContext.request.contextPath}/invitations/list.html?currentPage=${page.nextPage}&pageSize=${page.pageSize}&inviter=${inviter}&inviteCode=${inviteCode}&status=${status}&ascOrDesc=${ascOrDesc}">下一页</a>
			</li>
			<li class="pageinfo">
				<a href="${pageContext.request.contextPath}/invitations/list.html?currentPage=${page.lastPage}&pageSize=${page.pageSize}&inviter=${inviter}&inviteCode=${inviteCode}&status=${status}&ascOrDesc=${ascOrDesc}">尾页</a>
			</li>
			<li class="pageinfo">
				<input type="text" id="goPageNum"
					   style="width:20px;height:20px;line-height:20px;vertical-align: text-bottom;"/>
			</li>
			<li class="pageinfo">
				<a href="javascript:void(0);" id="pageGo">GO</a>
			</li>
		</ul>
	</div>
</div>
<script>
    $(function () {
        var $tablist = $(".tablist");
        var $checkAll = $(".checkAll");
        var $checkSubs = $tablist.find("input.checkSub")

        //checkAll or uncheck
        var checkFlag = false;
        $checkAll.click(function () {
            switch (checkFlag) {
                case true:
                    $checkSubs.prop('checked', false);
                    checkFlag = !checkFlag;
                    break;
                default:
                    $checkSubs.prop('checked', true);
                    checkFlag = !checkFlag;
            }

        });

        //处理操作
        $("#btn_optStatus").click(function () {
            var ids = [];
            $('input:checkbox[class=checkSub]:checked').each(function (index, element) {
                //console.info(index+"  --  "+$(element).val())
                ids.push($(element).val())
            });
            //console.info(ids.toString());
            var optVal = $("#optStatus").val();
            //console.info(optVal);
            if (optVal == '' || ids.length == 0) {
                alert('请选择需要处理的数据');
                return;
            }
            $.ajax({
                type: "get",
                url: "/invitations/optStatus.action",
                data: "ids=" + ids.toString() + "&opt=" + optVal,
                dataType: "json",
                success: function (msg) {
                    console.info(msg)
                    if (msg.code == 0) {
                        var href = '${pageContext.request.contextPath}/invitations/list.html?currentPage=${page.currentPage}&pageSize=${page.pageSize}&inviter=${inviter}&inviteCode=${inviteCode}&status=${status}&ascOrDesc=${ascOrDesc}'
                        window.location.href = href;
                    } else if (msg.code == -1) {
                        alert("更新失败！" + msg.msg);
                    }
                }
            });
        });

        //Go
        $("#pageGo").click(function () {
            var $pageNum = $("#goPageNum").val();
            var href = '${pageContext.request.contextPath}/invitations/list.html?pageSize=${page.pageSize}&inviter=${inviter}&inviteCode=${inviteCode}&status=${status}&ascOrDesc=${ascOrDesc}'
            window.location.href = href + "&currentPage=" + $pageNum;
        });

        $('#btn_statistics').click(function () {
            var invitePerson = $("#invitePerson").val();
            if (invitePerson == '') {
                alert('请输入邀请人用户id');
                return false;
            }

            btn_disable($('#btn_statistics'));
            var url = '/invitations/totalInvitePerson.html';
            var data = $('#searchForm').serialize();
            $.post(url, data, function (result) {
                btn_enable($('#btn_statistics'));
                $('#invite_userId').text('邀请人用户id:' + result.inviter);
                $('#invite_number').text('邀请人数:' + result.invitee + '人');
                $('#invite_suc_number').text('成功邀请：' + result.sucInvitee + '人');
            });
        })

    })
</script>
</body>
</html>
