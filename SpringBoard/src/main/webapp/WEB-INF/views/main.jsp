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
  			listHtml+="<td>"+obj.title+"</td>";
  			listHtml+="<td>"+obj.writer+"</td>";
  			listHtml+="<td>"+obj.indate+"</td>";
  			listHtml+="<td>"+obj.count+"</td>";
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
  	}
  	function goForm(){
  		$("#view").css("display","none");	//버튼 누를시 감춤
  		$("#wfrom").css("display","block");
  	}
	function goList(){
  		$("#view").css("display","block");	//버튼 누를시 감춤
  		$("#wfrom").css("display","none");
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
      <form action="boardInsert.do" method="post">
					<table class="table">
						<tr>
							<td>제목</td>
							<td><input type="text" name="title" class="form-control" /></td>
						</tr>
						<tr>
							<td>내용</td>
							<td><textarea rows="7" class="form-control" name="content"></textarea></td>
						</tr>
						<tr>
							<td>작성자</td>
							<td><input type="text" name="writer" class="form-control"/></td>
						</tr>
						<tr>
						<td colspan="2" align="center">
						<button type="submit" class="btn btn-success btn-sm">등록</button>
						<button type="reset" class="btn btn-warning btn-sm">취소</button>
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

