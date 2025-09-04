<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isELIgnored="true" %>
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8" />
  <title>index_ajax_ryu (씰지지 몬스터 뷰)</title>
  <style>
    :root{
      --bg:#111; --ink:#eee; --card:#1a1a1a; --line:#2a2a2a;
      --brand:#c0392b; --muted:#888;
    }
    body { background: var(--bg); color: var(--ink); font-family: system-ui, sans-serif; margin: 32px; }
    h1 { margin-bottom: 12px; }
    .bar { display: flex; gap: 8px; align-items: center; margin-bottom: 16px; }
    input[type="text"], select {
      padding: 8px 10px; border: 1px solid var(--line); background: #0f0f0f; color: var(--ink); border-radius: 10px;
    }
    button {
      padding: 8px 14px; border: 1px solid var(--line); background: #171717; color: var(--ink); border-radius: 10px; cursor: pointer;
    }
    .grid {
      display: grid; gap: 16px;
      grid-template-columns: repeat(auto-fill, minmax(260px, 1fr));
    }
    .card {
      background: var(--card); border: 1px solid var(--line); border-radius: 16px; padding: 16px;
      transition: transform .15s ease, box-shadow .15s ease;
    }
    .card:hover { transform: translateY(-2px); box-shadow: 0 8px 24px rgba(0,0,0,.35); }
    .name { font-weight: 700; font-size: 18px; margin-bottom: 6px; color:#fff; }
    .region { color: var(--muted); font-size: 13px; margin-bottom: 10px; }
    .stats { display: grid; grid-template-columns: 1fr 1fr; gap: 6px; font-size: 14px; }
    .chip { display:inline-block; padding:4px 8px; border:1px solid var(--line); border-radius:999px; font-size:12px; margin-right:6px; margin-top:6px;}
    .status { margin: 10px 0; color: var(--muted); font-size: 14px; }
    .empty { color: var(--muted); padding: 20px; border:1px dashed var(--line); border-radius: 12px; text-align: center; }
  </style>
</head>
<body>
  <h1>index_ajax_ryu.jsp — 몬스터 목록 (AJAX)</h1>

  <div class="bar">
    <label>지역:</label>
    <select id="region">
      <option value="all">전체</option>
      <option value="엘림">엘림</option>
      <option value="웨인">웨인</option>
      <option value="라비아">라비아</option>
    </select>

    <label>검색:</label>
    <input id="kw" type="text" placeholder="몬스터 이름 검색" />
    <button id="btnReload">새로고침</button>
  </div>

  <div class="status" id="status">준비됨</div>

  <div id="grid" class="grid"></div>
  <div id="empty" class="empty" style="display:none;">표시할 몬스터가 없습니다.</div>

  <script>
    // ⬇⬇⬇ 본인 도메인으로 바꿔서 제출
    const URL = 'http://heelack.dothome.co.kr/monsters.json';
    // 예: const URL = 'http://heelack.dothome.co.kr/monsters.json';

    const $status = document.getElementById('status');
    const $grid   = document.getElementById('grid');
    const $empty  = document.getElementById('empty');
    const $region = document.getElementById('region');
    const $kw     = document.getElementById('kw');
    const $btn    = document.getElementById('btnReload');

    let data = [];

    async function load() {
      $status.textContent = '로딩 중...';
      try {
        const res = await fetch(URL, { cache: 'no-store' });
        if (!res.ok) throw new Error('HTTP ' + res.status);
        data = await res.json();
        $status.textContent = `불러오기 성공 (총 ${data.length}마리)`;
        render();
      } catch (err) {
        console.error(err);
        $status.textContent = '불러오기 실패: ' + err.message + ' (CORS 가능성)';
        $grid.innerHTML = '';
        $empty.style.display = 'block';
      }
    }

    function render() {
      const r = $region.value;
      const k = $kw.value.trim().toLowerCase();

      const filtered = data.filter(m => {
        const regionOk = (r === 'all') || (m.region === r);
        const keywordOk = !k || String(m.name).toLowerCase().includes(k);
        return regionOk && keywordOk;
      });

      if (filtered.length === 0) {
        $grid.innerHTML = '';
        $empty.style.display = 'block';
        return;
      }
      $empty.style.display = 'none';

      $grid.innerHTML = filtered.map(m => `
        <div class="card">
          <div class="name">${m.name ?? '-'}</div>
          <div class="region">출현지역: ${m.region ?? '-'}</div>
          <div class="stats">
            <div>HP: ${m.hp ?? '-'}</div>
            <div>DEF: ${m.def ?? '-'}</div>
            <div>ATK: ${m.atk ?? '-'}</div>
            <div>이동속도: ${m.move ?? '-'}</div>
          </div>
          <div style="margin-top:10px;">
            ${(m.drops || []).map(d => `<span class="chip">${d}</span>`).join('')}
          </div>
        </div>
      `).join('');
    }

    $region.addEventListener('change', render);
    $kw.addEventListener('input', render);
    $btn.addEventListener('click', load);

    // 최초 1회 로드
    load();
  </script>
</body>
</html>
