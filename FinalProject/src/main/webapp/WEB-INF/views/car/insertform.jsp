<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html data-ng-app="myApp">
<head>
<meta charset="UTF-8">
<title>차 판매등록 페이지</title>
<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/bootstrap.min.css" />
</head>
<body>
	<form action="insert.do" method="post">
		<label for="title">제목</label>
		<input type="text" id="title" name="title" />
		<br />
		<label for="car_num">차량번호</label>
		<input type="text" id="car_num" name="car_num" />
		<br />
		<label for="m_name">차량</label>
		<input type="text" id="m_name_view" disabled/>
		<button type="button" id="searchBtn">검색</button>
		<input type="hidden" id="m_name" name="m_name"/>
		<br />
		<label for="color">색상</label>
		<select id="color" name="color">
			<option value="" selected>선택</option>
			<option value="blue">파랑색</option>
			<option value="red">적색</option>
			<option value="white">흰섹</option>
			<option value="gray">회색</option>
			<option value="black">검은색</option>
			<option value="green">녹색</option>
			<option value="etc">기타</option>
		</select>
		<br />
		<label for="seater">탑승인원</label>
		<select id="seater" name="seater">
			<option value="" selected>선택</option>
			<option value="4">4 인승</option>
			<option value="5">5 인승</option>
			<option value="7">7 인승</option>
			<option value="9">9 인승</option>
			<option value="11">11 인승</option>
		</select>
		<br />
		<label for="automotive_fuel">연료</label>
		<select id="automotive_fuel" name="automotive_fuel">
			<option value="" selected>선택</option>
			<option value="가솔린">가솔린</option>
			<option value="디젤">디젤</option>
			<option value="LPG">LPG</option>
			<option value="전기">전기</option>
			<option value="LNG">LNG</option>
			<option value="하이브리드">하이브리드</option>
		</select>
		<br />
		<label for="performance">자가 성능 진단</label>
		<select id="performance" name="performance">
			<option value="" selected>선택</option>
			<option value="1">양호</option>
			<option value="0">불량</option>
		</select>
		<br />
		<label for="expendables">자가 소모품 진단</label>
		<select id="expendables" name="expendables">
			<option value="" selected>선택</option>
			<option value="1">양호</option>
			<option value="0">불량</option>
		</select>
		<br />
		<label for="vehical_gear">기어</label>
		<select id="vehical_gear" name="vehical_gear">
			<option value="" selected>선택</option>
			<option value="1">자동</option>
			<option value="0">수동</option>
		</select>
		<br />
		<label for="car_year">연식</label>
		<input type="text" id="car_year" name="car_year" />년식
		<br />
		<label for="vehical_mile">주행거리</label>
		<input type="text" id="vehical_mile" name="vehical_mile" />KM
		<br />
		<label for="car_option">옵션</label>
		<select id="car_option" name="car_option">
			<option value="" selected>선택</option>
			<option value="모던">모던</option>
			<option value="스마트">스마트</option>
			<option value="인스퍼레이션">인스퍼레이션</option>
		</select>
		<br />
		<label for="c_history">사고이력</label>
		<input type="text" id="c_history" name="c_history" />
		<br />
		<label for="s_price">가격</label>
		<input type="text" id="s_price" name="s_price" />(단위:만원)
		<br />
		
		<form action="upload.do" id="uploadForm" method="post" enctype="multipart/form-data">
			<div class="form-group">
				<label for="myFile">첨부 파일</label>
				<input multiple="multiple" class="form-control" type="file" name="file" id="file" accept="image/*"/>
			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>no</th>
						<th>파일명</th>
						<th>삭제</th>
					</tr>
				</thead>
				<tbody id="file_input">
					
				</tbody>
			</table>
		</form>
		
		<form data-ng-controller="MyCtrl" name="form">
		    <!-- <div class="button" data-ng-model="file" name="file" data-ngf-select data-ngf-pattern="'image/*'" data-ngf-accept="'image/*'" data-ngf-max-size="20MB">파일선택</div> -->
		    <input type="file" multiple="multiple" data-ng-model="file" name="file" accept="image/*" data-ng-pattern="image/*"/>
		    <button type="submit" data-ng-click="submit()">업로드</button>
		</form>
		
		<button type="submit">등록</button>
	</form>
	
	<script src="${pageContext.request.contextPath}/resources/js/jquery-3.5.1.js"></script>
	<script src="${pageContext.request.contextPath}/resources/js/angular.min.js"></script>
	<script>
		$("#searchBtn").on("click",function() {
			var data=$("#car_search").val();
			var url="search_car.do";
			window.open(url,"search","height=600,width=400");
		});
		
		$("#file").on("change",function() {
			var myForm=$("#uploadForm")[0];
			var formData=new FormData(myForm);
			
			for(var tmp in $("#file")[0].files) {
				console.log($("#file")[0].files[tmp]);
				formData.append("file",$("#file")[0].files[tmp]);
			}
			
			
			$.ajax({
				url:"file_upload.do",
				method:"post",
				processData: false,
				contentType: false,
				data:formData,
				success: function(data) {
					console.log(data);
					for(tmp in data) {
						var tr=$("<tr/>").addClass(data[tmp].fileName);
						var num_td=$("<td/>").text(tmp);
						var name_td=$("<td/>").text(data[tmp].orgName);
						var delBtn=$("<button/>").text("X").addClass("delBtn").attr("type","button");
						var del_td=$("<td/>").append(delBtn);
						
						$(tr).append(num_td).append(name_td).append(del_td).appendTo("#file_input");
						
					}
				}
			});
		});
	
		$(document).on("click",".delBtn",function() {
			var column=$(this).parent("td").parent("tr");		
			var fileName=$(column).attr("class");
			console.log(column);
			
			$.ajax({
				url:"file_delete.do",
				method:"post",
				data:{saveFileName:fileName},
				success: function(data) {
					console.log(data);
					if(data) {
						$(column).remove();
					}
				}
			});
		});
		
		var app = angular.module('myApp', []);
		 
		app.controller('MyCtrl', function ($scope, $http) {
		    $scope.submit = function() {
		      console.log($scope.file);
		      if ($scope.form.file.$valid && $scope.file) {
		        $scope.upload($scope.file);
		        console.log($scope.file);
		      }
		    };
		    
		    $scope.upload = function (file) {
		    	$http({
					url:"file_upload.do",
					method:"post",
					params:{file:file},
					headers: {'Content-Type': 'application/json; charset=utf-8'}
				}).success(function(data){
					console.log(data);
				});	
		    };
		});
		
	</script>
	
</body>
</html>