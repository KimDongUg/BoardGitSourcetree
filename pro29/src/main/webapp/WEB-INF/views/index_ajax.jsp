<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>선수 데이터 테이블</title>
<style>
  body {
    font-family: sans-serif;
    margin: 20px;
  }
  h1 {
    font-size: 24px;
    margin-bottom: 15px;
  }
  #status {
    font-style: italic;
    color: #666;
    margin-bottom: 20px;
  }
  table {
    border-collapse: collapse;
    width: 100%;
    max-width: 1000px;
  }
  th, td {
    border: 1px solid #ccc;
    padding: 8px 10px;
    text-align: left;
    font-size: 14px;
  }
  th {
    background: #f0f0f0;
  }
</style>
<!-- jQuery CDN -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />
</head>
<body>

<h1>선수 목록</h1>
<p id="status">데이터를 불러오는 중...</p>
<table id="player-table">
  <thead>
    <tr>
      <th>player_id</th>
      <th>team_id</th>
      <th>player_name</th>
      <th>age</th>
      <th>height</th>
      <th>weight</th>
      <th>speed_100m</th>
      <th>salary</th>
      <th>experience_years</th>
      <th>goals</th>
      <th>assists</th>
      <th>defense_ability</th>
    </tr>
  </thead>
  <tbody>
  <!-- AJAX로 불러온 데이터가 여기 들어감 -->
  </tbody>
</table>

<script>
$(document).ready(function() {
  $.ajax({
    url: 'http://aro124.dothome.co.kr/players.json', 
    method: 'GET',
    dataType: 'json',
    success: function(data) {
      $('#status').text('');
      console.log(data);
      if ($.isArray(data) && data.length > 0) {
        var tbody = $('#player-table tbody');
        data.forEach(function(player) {
          var tr = $('<tr></tr>');
          tr.append('<td>' + player.player_id + '</td>');
          tr.append('<td>' + player.team_id + '</td>');
          tr.append('<td>' + player.player_name + '</td>');
          ~~~

          tbody.append(tr);
        });
      } else {
        $('#status').text('데이터를 불러올 수 없습니다.');
      }
    },
    error: function(xhr, status, error) {
      $('#status').text('에러 발생: ' + error);
    }
  });
});
</script>
</body>
</html>
