<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单问题列表</title>
<jsp:include page="common/public_js_css.jsp" flush="true"/>
<script type="text/javascript" src="../../style/js/echo-area-and-type.js"></script>
</head>
<body style="background-color: #FFFFFF;">
	<div class="mainBox">
		<h3>订单问题列表</h3>
		<form action="/hajOrderProblem/list.html" method="post" name="userForm" id="userForm">
			<div class="filter" >
				下单时间：
				<input type="button" class="btnGray" id="btn_today" value="今日" />
				<input type="text" class="inpMain short date_picker" id="beginTime" name="beginTime" value="${hajOrderProblem.beginTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" placeholder="开始时间" />
				-
				<input type="text" id="endTime" name="endTime" class="inpMain short date_picker" value="${hajOrderProblem.endTime}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" placeholder="结束时间" />

				处理时间：
				<input type="button" class="btnGray" id="btn_today1" value="今日" />
				<input type="text" class="inpMain short date_picker" id="beginTime1" name="beginTime1" value="${hajOrderProblem.beginTime1}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" placeholder="开始时间" />
				-
				<input type="text" id="endTime1" name="endTime1" class="inpMain short date_picker" value="${hajOrderProblem.endTime1}"
					onclick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',readOnly:'readOnly'})" readonly="readonly" placeholder="结束时间" />
			</div>
			
			<div class="filter" >
				<select name="obj" id="obj" style="vertical-align: middle; margin-right: 10px;"  onchange="selectObj();">
					<option value="">处理对象</option>
					<option value="0" <c:if test="${hajOrderProblem.obj==0}">selected</c:if>>整单</option>
					<option value="1" <c:if test="${hajOrderProblem.obj==1}">selected</c:if>>商品</option>
				</select>
				<select name="method" id="method" style="vertical-align: middle; margin-right: 10px;">
					<option value="">处理办法</option>
					<option value="0" <c:if test="${hajOrderProblem.method==0}">selected</c:if>>补发</option>
					<option value="1" <c:if test="${hajOrderProblem.method==1}">selected</c:if>>退款</option>
				</select>
				
				<select name="type" id="type" style="vertical-align: middle; margin-right: 10px;">
					<option value="">问题类型</option>
						<option value="0" <c:if test="${hajOrderProblem.type==0}">selected</c:if>>其他</option>
						<option value="1" <c:if test="${hajOrderProblem.type==1}">selected</c:if>>未送达</option>
						<option value="100" <c:if test="${hajOrderProblem.type==100}">selected</c:if>>未知</option>
						<option value="101" <c:if test="${hajOrderProblem.type==101}">selected</c:if>>少送</option>
						<option value="102" <c:if test="${hajOrderProblem.type==102}">selected</c:if>>错送</option>
						<option value="103" <c:if test="${hajOrderProblem.type==103}">selected</c:if>>重量不足</option>
						<option value="104" <c:if test="${hajOrderProblem.type==104}">selected</c:if>>品质问题</option>
						<option value="105" <c:if test="${hajOrderProblem.type==105}">selected</c:if>>受外力破坏</option>
						<option value="106" <c:if test="${hajOrderProblem.type==106}">selected</c:if>>过期</option>
						<option value="107" <c:if test="${hajOrderProblem.type==107}">selected</c:if>>未冷藏</option>
						<option value="108" <c:if test="${hajOrderProblem.type==108}">selected</c:if>>仓库缺货</option>
				</select>
				责任部门：
				<label><input type="checkbox" name="dept" value="1"/>生产部</label>
				<label><input type="checkbox" name="dept" value="2"/>质检部</label>
				<label><input type="checkbox" name="dept" value="3"/>配送部</label>
				<label><input type="checkbox" name="dept" value="4"/>采购部</label>
				<label><input type="checkbox" name="dept" value="5"/>仓储部</label>
				<label><input type="checkbox" name="dept" value="0"/>其他</label>
			
			</div>

			<div class="filter" >
				<input type="text" class="inpMain short" placeholder="小区名称 " name="residential" value="${hajOrderProblem.residential }" />

				<input type="text" class="inpMain short" placeholder="商品编号/商品名称" name="commodityNo" value="${hajOrderProblem.commodityNo }" />
				
				<input type="text" class="inpMain short" placeholder="用户编号/手机号码" name="mobilePhone" value="${hajOrderProblem.mobilePhone }" />

				<input type="text" class="inpMain short" placeholder="订单编号" name="orderNo" value="${hajOrderProblem.orderNo }" />

				<input class="btn" type="button" value="搜索" onclick="search();" />
			</div>
				<input type="hidden" id="depts" value="${hajOrderProblem.dept}	">
		</form>
		<div id="list">
		
			<form name="deleteForm" id="deleteForm">
				<table style="width: 100%;  " class="tableBasic list">
					<tr>
						
						<th>下单时间</th>
						<th>处理时间</th>
						<th>手机号码</th>
						<th>城市</th>
						<th>小区</th>
						<th>收货地址</th>
						<th>订单号</th>
						<th>处理对象</th>
						<th>商品名称</th>
						<th>处理数量</th>
						<th>处理办法</th>
						<th>问题类型</th>
						<th>责任部门</th>
						<th>凭证</th>
					</tr>
					<c:choose>
						<c:when test="${not empty hajOrderProblemList}">
							<c:forEach items="${hajOrderProblemList}" var="order" varStatus="vs">
								<tr class="main_info">
									
									<td>${order.createTime }</td>
									<td>${order.problemTime }</td>
									<td>${order.mobilePhone }</td>
									<td>
										<c:if test="${order.areaCode=='100002'}">深圳市 </c:if>
										<c:if test="${order.areaCode=='100010'}">广州市 </c:if>
									</td>
									<td>${order.residential }</td>
									<td>${order.address }</td>
									<td>${order.orderNo }</td>
									<td>
										<c:if test="${order.obj=='0'}">整单 </c:if>
										<c:if test="${order.obj=='1'}">商品 </c:if>
									</td>
									<td>${order.name }</td>
									<td>${order.number}</td>
									<td>
										<c:if test="${order.method=='0'}">补发 </c:if>
										<c:if test="${order.method=='1'}">退款 </c:if>
									</td>
									<td>
										<c:if test="${order.type=='0'}">其他</c:if>
										<c:if test="${order.type=='1'}">未送达</c:if>
										<c:if test="${order.type=='100'}">未知</c:if>
										<c:if test="${order.type=='101'}">少送</c:if>
										<c:if test="${order.type=='102'}">错送</c:if>
										<c:if test="${order.type=='103'}">重量不足</c:if>
										<c:if test="${order.type=='104'}">品质问题</c:if>
										<c:if test="${order.type=='105'}">受外力破坏</c:if>
										<c:if test="${order.type=='106'}">过期</c:if>
										<c:if test="${order.type=='107'}">未冷藏</c:if>
										<c:if test="${order.type=='108'}">仓库缺货</c:if>
									</td>
									<td>${order.dept }</td>
									
								
									<td>
										<c:if test="${not empty order.pic }">
											<a href="${order.pic}" target="_blank">查看详情</a>
											|
											重新上传:
											<input  id="${order.orderProblemId }" type="file"  accept=".jpg,.jpeg,.png" />
										</c:if>
										<c:if test="${empty order.pic }">
											上传:
											<input  id="${order.orderProblemId }" type="file"  accept=".jpg,.jpeg,.png" />

										</c:if>
									
									</td>
								</tr>
							</c:forEach>
						</c:when>
						<c:otherwise>
							<tr class="main_info">
								<td colspan="19">没有相关数据</td>
							</tr>
						</c:otherwise>
					</c:choose>
				</table>
			</form>
		</div>
		<div class="clear"></div>
		<div class="page_and_btn">
			<div>
				<input type="button" id="btn_excel" class="btn" value="批量导出" title="批量导出" />
		
			</div>
			${hajOrderProblem.page.pageStr }
		</div>
	</div>

	<script type="text/javascript" src="../../style/plugins/datePicker/WdatePicker.js"></script>
	<script type="text/javascript">
		function sltAllUser() {
			if ($("#sltAll").prop("checked")) {
				$("input[name='orderIds']").prop("checked", true);
			} else {
				$("input[name='orderIds']").prop("checked", false);
			}
		}
		
		function selectObj() {
			var obj = $("#obj").val();
			$("#type").empty();
			if(obj==0) {
				var str="<option value=''>问题类型</option><option value='0' <c:if test="${hajOrderProblem.type==0}">selected</c:if>>其他</option><option value='1' <c:if test="${hajOrderProblem.type==1}">selected</c:if>>未送达</option>";
				$("#type").append(str); 
			}else if(obj==1) {
				var str="<option value=''>问题类型</option>" +
					"<option value='100' <c:if test="${hajOrderProblem.type==100}">selected</c:if>>未知</option>"+
					"<option value='101' <c:if test="${hajOrderProblem.type==101}">selected</c:if>>少送</option>"+
					"<option value='102' <c:if test="${hajOrderProblem.type==102}">selected</c:if>>错送</option>"+
					"<option value='103' <c:if test="${hajOrderProblem.type==103}">selected</c:if>>重量不足</option>"+
					"<option value='104' <c:if test="${hajOrderProblem.type==104}">selected</c:if>>品质问题</option>"+
					"<option value='105' <c:if test="${hajOrderProblem.type==105}">selected</c:if>>受外力破坏</option>"+
					"<option value='106' <c:if test="${hajOrderProblem.type==106}">selected</c:if>>过期</option>"+
					"<option value='107' <c:if test="${hajOrderProblem.type==107}">selected</c:if>>未冷藏</option>"+
					"<option value='108' <c:if test="${hajOrderProblem.type==107}">selected</c:if>>仓库缺货</option>"
				;
				$("#type").append(str);
			}
		}

		function search() {
			$("#userForm").submit();
		}

		$(function() {
			selectObj();
		});
		$(function() {
			// 批量导出
			$('#btn_excel').click(function(){
				btn_disable($('#btn_excel'));
				var residential = $("input[name='residential']").val();
				var commodityNo = $("input[name='commodityNo']").val();
				var mobilePhone = $("input[name='mobilePhone']").val();
				var orderNo = $("input[name='orderNo']").val();
				
				var beginTime1 = $("input[name='beginTime1']").val();
				var endTime1 = $("input[name='endTime1']").val();
				
				var beginTime = $("input[name='beginTime']").val();
				var endTime = $("input[name='endTime']").val();
	
				var method =   $("#method").val();
				var obj = $("#obj").val();
				var type =   $("#type").val();
	
				var chk_value =[]; 
				$('input[name="dept"]:checked').each(function(){ 
				chk_value.push($(this).val()); 
				}); 
				
				document.location = "/hajOrderProblem/excelOrderProblem.html?residential=" + residential + "&commodityNo=" + commodityNo
					+ "&mobilePhone=" + mobilePhone + "&orderNo=" + orderNo + "&beginTime1=" + beginTime1
					+ "&endTime1=" + endTime1 + "&beginTime=" + beginTime+"&endTime="+endTime
					+"&method="+method+"&obj="+obj+"&type="+type+"&dept="+chk_value;

				setTimeout('btn_enable($("#btn_excel"))', 10000);
			});
		});
		
	
		$(function(){
			$('#btn_today').click(function(){
				var today = date_format(new Date(), '-');
				$('#beginTime').val(today + ' 00:00:00');
				$('#endTime').val(today + ' 23:59:59');
			});
		});
		
		$(function(){
			$('#btn_today1').click(function(){
				var today = date_format(new Date(), '-');
				$('#beginTime1').val(today + ' 00:00:00');
				$('#endTime1').val(today + ' 23:59:59');
			});
		});
	</script>
	
	<script>
    var codeArr = $("#depts").val();
    if(codeArr.length>0)
    {
    	  for(var i=0;i<codeArr.length;i++){
    	        var tem = codeArr[i];
    	        $("input:checkbox[value='"+tem+"']").attr("checked","checked");
    	    }
    }

</script>

<script>
	//图片上传
    $(":file").change(function() {

        //创建FormData对象
        var data = new FormData();
		var  isPicture = true;
        //为FormData对象添加数据
        $.each($(this)[0].files, function(i, file) {

			var fileName =  file.name;
            if(!/\.(gif|jpg|jpeg|png|GIF|JPG|PNG|bmp)$/.test(fileName)){
                isPicture = false;
                return false;
			}
            if (file.size/1024 > 1024) {
                alert('图片大小不能大于1M');
                return false;
            } else {
                data.append('fileName', file);
            }
        });
		if(!isPicture){
			alert("只能上传图片");
		    return ;
		}
        data.append('id',$(this).attr("id"));

        $.ajax({
            url : '/hajOrderProblem/orderProblemPicUpload.html',
            type : 'POST',
            data : data,
            cache : false,
            contentType : false, //不可缺
            processData : false, //不可缺
            success : function(result) {
                if (result.result = 'success') {
                    alert("上传成功");
                    window.location.reload();
                } else {
                    alert('我的天呐，上传失败了，赶紧问一下程序员GG');
                }
            },
            error : function() {
                alert('请先选择您要上传的图片');
            }
        });
    });
</script>
</body>
</html>
