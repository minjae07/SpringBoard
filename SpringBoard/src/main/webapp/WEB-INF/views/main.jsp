<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <title>Spring MVC02</title>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>
  <script type="text/javascript">
  	$(document).ready(function(){
  		 loadList();
  	});
  	function loadList() {
  		//서버와 통신 : 게시판 리스트 가져오기
  		$.ajax({
  			url : "boardList.do", 
  			type : "get",
  			dataType : "json",
  			success : makeView,	//콜백 함수
  			error : function(){	alert("error");	}
  		});
  	}
  	function makeView(data){	//JSON 데이터
  		var listHtml="<table class='table table-boardered'>";
  		listHtml+="<tr>";
  		listHtml+="<td>번호</td>";
  		listHtml+="<td>제목</td>";
  		listHtml+="<td>작성자</td>";
  		listHtml+="<td>작성일</td>";
  		listHtml+="<td>조회수</td>";
  		listHtml+="</tr>";
  		$.each(data, function(index,obj){
  			listHtml+="<tr>";
  			listHtml+="<td>"+obj.idx+"</td>";
  			listHtml+="<td id='_title"+obj.idx+"'><a href='javascript:goContent("+obj.idx+")'>"+obj.title+"</a></td>";
  			listHtml+="<td>"+obj.writer+"</td>";
  			listHtml+="<td>"+obj.indate+"</td>";
  			listHtml+="<td id='_hit"+obj.idx+"'>"+obj.count+"</td>";
  			listHtml+="</tr>";
  			
  			listHtml+="<tr id='content"+obj.idx+"' style='display:none'>";
  			listHtml+="<td>내용</td>";
  			listHtml+="<td colspan='4'>";
  			listHtml+="<textarea id='_text"+obj.idx+"' readonly rows='7' class='form-control'></textarea>";
  			listHtml+="<br/>";
  			listHtml+="<span id='_update"+obj.idx+"'><button class='btn btn-success btn-sm' onclick='goUpdateForm("+obj.idx+")'>수정화면</button></span>&nbsp;";
  			listHtml+="<button class='btn btn-warning btn-sm' onclick='goDelete("+obj.idx+")'>삭제</button>";
  			listHtml+="</td>";
  			listHtml+="</tr>";
  		} );
  		
  		listHtml+="<tr>";
  		listHtml+="<td colspan='5'>";
  		listHtml+="<button class='btn btn-primary btn-sm' onclick='goForm()'>글쓰기</button>";
  		listHtml+="</td>";
  		listHtml+="</tr>";
  		listHtml+="</table>";	
  		//$(): 선택자, 무엇을 선택할때 사용
  		$("#view").html(listHtml);
  		
  	  $("#view").css("display","block");
  	  $("#wfrom").css("display","none");
  	}
  	function goForm(){
  		$("#view").css("display","none");	//버튼 누를시 감춤
  		$("#wfrom").css("display","block");
  	}
	function goList(){
  		$("#view").css("display","block");	//버튼 누를시 감춤
  		$("#wfrom").css("display","none");
  	}
	
	function goInsert(){
		//var title = $("#title").val();
		//var content = $("#content").val();
		//var writer = $("#writer").val();
		
		var fData=$("#frm").serialize();
		
		$.ajax({
			url : "boardInsert.do",
			type : "post",
			data : fData,
			success : loadList,
			error : function() {
				alert("error");
			}
			//alert("글이 등록되었습니다");
		});
		
		//폼 초기화
		//$("#title").val("");
		//$("#content").val("");
		//$("#writer").val("");
		$("#fclear").trigger("click");
	}
	function goContent(idx){
		if($("#content"+idx).css("display")=="none"){
			
	
		$.ajax({
            url: "boardContent.do",
            type: "get",
            data: {"idx" : idx},
            dataType : "json",
            success: function(data){
            	$("#_text"+idx).val(data.content);
            },
            error : function() {alert("error");}
        });	
			
		$("#content"+idx).css("display","table-row");	//보이게
		$("#_text"+idx).attr("readonly",true);
	}else{
		$("#content"+idx).css("display","none");	//감추기
		$.ajax({
			url : "boardCount.do",
			type : "get",
			data : { "idx" : idx},
			dataType : "json",
			success : function(data) {
				$("#_hit"+idx).text(data.count);
			},
			error : function() {
				alert("error");
			}
		})
	}
	}
	function goDelete(idx){
		$.ajax({
			url : "boardDelete.do",
			type : "get",
			data : {"idx":idx},
			success : loadList,
			error : function() {alert("error");}
		});
	}	
	function goUpdateForm(idx){
		$("#_text"+idx).attr("readonly",false);	//텍스트 제거
		var title = $("#_title"+idx).text();
		 
		var newInput="<input type='text' id='_newtText"+idx+"' class='form-control' value='"+title+"'/>"	
		$("#_title"+idx).html(newInput);	//제목 수정
		
		var newButton="<button class='btn btn-primary btn-sm' onclick='goUpdate("+idx+")'>수정</button>"
		$("#_update"+idx).html(newButton);	//수정화면을 수정으로 바꿈
	}
	function goUpdate(idx){
		var title=$("#_newtText"+idx).val();
		var content=$("#_text"+idx).val();
		$.ajax({
			url: "boardUpdate.do",
			type : "post",
			data : {"idx":idx,"title":title,"content":content},
			success : loadList,
			error: function(){alert("error");}
		})
	}
	
	
  </script>
  

</head>
<body>
 
<div class="container">
  <h2>Spring MVC02</h2>
  <div class="panel panel-default">
    <div class="panel-heading">BOARD</div>
    <div class="panel-body" id="view" >Panel Content</div>
    <div class="panel-body" id="wfrom" style="display: none">
      <form id="frm">
					<table class="table">
						<tr>
							<td>제목</td>
							<td><input type="text" id="title" name="title" class="form-control" /></td>
						</tr>
						<tr>
							<td>내용</td>
							<td><textarea rows="7" id="content" name="content" class="form-control"></textarea></td>
						</tr>
						<tr>
							<td>작성자</td>
							<td><input type="text" id="writer" name="writer" class="form-control"/></td>
						</tr>
						<tr>
						<td colspan="2" align="center">
						<button type="button" class="btn btn-success btn-sm" onclick="goInsert()">등록</button>
						<button type="reset" class="btn btn-warning btn-sm" id="fclear">취소</button>
						<button type="button" class="btn btn-warning btn-sm" onclick="goList()">목록</button>
						</tr>
					</table>
				</form>
      </div>
    <div class="panel-footer">인프런_스프1탄_배민재</div>
  </div>
</div>

</body>
</html>
