<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>mysite</title>
<meta http-equiv="content-type" content="text/html; charset=utf-8">
<link href="${pageContext.request.contextPath}/assets/css/main.css"
	rel="stylesheet" type="text/css">
<script src="${pageContext.request.contextPath}/assets/js/jquery/jquery-1.9.0.js"></script>
<script>


	<!-- JS에서 api 받는 방법. 함수는 DOM이 다 만들어진 후 실행된다.-->
	$(function(){
		$("#btn-ajax").click(function(){
			
			$.ajax({
				url: "${pageContext.request.contextPath}/msg03",    // 서버에서 JSP가 코드를 바꾼후 브라우저로 보내주는거다. 자바스크립트 코드와 혼용할 수 있는 게 아니다. 
				dataType: "json",
				success: function(response){  // 성공시 response에 객체 줌.
					
					var htmls = "<h4>"+response.name+"</h4>"
					$("#div-user").html(htmls);
				}
			})
		})
	});
</script>
</head>
<body>
	<div id="container">
		<jsp:include page="/WEB-INF/views/includes/header.jsp" />
		<div id="wrapper">
			<div id="content">
				<div id="site-introduction">
					<img id="profile"
						src="${pageContext.request.contextPath}/assets/images/IMG_0006.JPG"
						style="width: 150px">
					<h2>안녕하세요. 김모세의 mysite에 오신 것을 환영합니다.</h2>
					<p>
						이 사이트는 웹 프로그램밍 실습과제 예제 사이트입니다.<br> 메뉴는 사이트 소개, 방명록, 게시판이 있구요.
						Java 수업 + 데이터베이스 수업 + 웹프로그래밍 수업 배운 거 있는거 없는 거 다 합쳐서 만들어 놓은 사이트
						입니다.<br> <br> <a href="${pageContext.request.contextPath}/guestbook">방명록</a>에 글 남기기<br>
						<br><br>
						<button id="btn-ajax">Ajax Test</button>
						<div id="div-user">
							<h4></h4>
						</div>
					</p>
				</div>
			</div>
		</div>
		<jsp:include page="/WEB-INF/views/includes/navigation.jsp" />
		<jsp:include page="/WEB-INF/views/includes/footer.jsp" />
	</div>
</body>
</html>