<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
  <title>선수 데이터 테이블</title>
  <style>
    body { font-family: sans-serif; margin: 20px; }
    h1 { font-size: 24px; margin-bottom: 15px; }
    #status { margin: 8px 0 16px; color: #d32f2f; }
    table { border-collapse: collapse; width: 100%; max-width: 1200px; }
    th, td { border: 1px solid #ccc; padding: 8px 10px; text-align: left; font-size: 14px; }
    th { background: #f5f5f5; }
    .small { font-size: 12px; color: #666; }
  </style>
  <script src="https://code.jquery.com/jquery-3.7.1.min.js"
          integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo="
          crossorigin="anonymous"></script>
</head>
<body>
  <h1>선수 데이터 테이블</h1>
  <div id="status" class="small">불러오는 중…</div>

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
    <tbody></tbody>
  </table>

  <script>
    // ① 여기를 본인 닷홈 URL로 교체하세요 (예: http://heelack.dothome.co.kr/players.json)
    const REMOTE_URL = 'http://heelack.dothome.co.kr/players.json';

    // ② 폴백용(동일 JSON을 아래 경로에도 넣어두세요: /src/main/webapp/resources/players.json)
    const LOCAL_URL  = '${contextPath}/resources/players.json';

    function normalizeRows(data) {
      if (Array.isArray(data)) return data;
      if (data && Array.isArray(data.players)) return data.players;
      return [];
    }

    function safe(v) {
      return (v === null || v === undefined) ? '' : v;
    }

    function renderTable(rows) {
      const $tbody = $('#player-table tbody');
      $tbody.empty();

      if (!rows.length) {
        $('#status').text('표시할 데이터가 없습니다.');
        return;
      }

      rows.forEach(function(p) {
        const tr = $('<tr/>');
        tr.append('<td>' + safe(p.player_id) + '</td>');
        tr.append('<td>' + safe(p.team_id) + '</td>');
        tr.append('<td>' + safe(p.player_name) + '</td>');
        tr.append('<td>' + safe(p.age) + '</td>');
        tr.append('<td>' + safe(p.height) + '</td>');
        tr.append('<td>' + safe(p.weight) + '</td>');
        tr.append('<td>' + safe(p.speed_100m) + '</td>');
        tr.append('<td>' + safe(p.salary) + '</td>');
        tr.append('<td>' + safe(p.experience_years) + '</td>');
        tr.append('<td>' + safe(p.goals) + '</td>');
        tr.append('<td>' + safe(p.assists) + '</td>');
        tr.append('<td>' + safe(p.defense_ability) + '</td>');
        $tbody.append(tr);
      });

      $('#status').text(''); // 성공 시 상태 지우기
    }

    function loadJSON(url, onSuccess, onError) {
      $.ajax({
        url: url,
        method: 'GET',
        dataType: 'json',
        success: function (data) {
          onSuccess(normalizeRows(data));
        },
        error: function (xhr, status, err) {
          onError(err || status);
        }
      });
    }

    $(function () {
      // 1순위: 닷홈 → 실패하면 2순위: 로컬 정적파일
      loadJSON(REMOTE_URL, renderTable, function () {
        loadJSON(LOCAL_URL, renderTable, function (err2) {
          $('#status').text('데이터를 불러올 수 없습니다. (' + err2 + ')');
        });
      });
    });
  </script>
</body>
</html>
