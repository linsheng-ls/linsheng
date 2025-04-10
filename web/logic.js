
    // -------------log--------------------------------------------------------
 function logToServer(message) {
    fetch('http://localhost:8080/log', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ log: message })
    }).catch(err => {});  
  }

  if (!console._patched) {  // 防止重复 patch
  const oldLog = console.log;
  console.log = function (...args) {
    oldLog(...args);
    const message = args.join(' ');
    logToServer(message);
  };
  console._patched = true;  // 标记已经重写过
}
  console.log('Front-end loaded at ' + new Date().toLocaleString());

// -------------------后端--------------------------------------------------

// -------------------------留言-----------------------------------------
function sendInput() {
  const input = document.getElementById('userInput');
  const btn = document.getElementById('sendBtn');

  if (input.value.trim() === '') return; // 防止空发送

  console.log('User input:', input.value);


  // 按钮变灰
  btn.textContent = "已发送";
  btn.classList.add("sent");
  btn.disabled = true;

  // 3 秒后恢复
  setTimeout(() => {
    btn.textContent = "留言";
    btn.classList.remove("sent");
    btn.disabled = false;
    input.value = ""; // 清空输入框
  }, 1500);
}
//--------------------------------------------------------
    let stompClient = null;

    function connect() {
        const socket = new SockJS('http://localhost:8080/chat');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log('Connected: ' + frame);

            // 订阅消息
            stompClient.subscribe('/topic/messages', function (msg) {
                const body = JSON.parse(msg.body);
                const chatBox = document.getElementById('chatBox');
                chatBox.innerHTML += `<p><strong>${body.sender}:</strong> ${body.content}</p>`;
                chatBox.scrollTop = chatBox.scrollHeight;
            });
        });
    }

    // 发送消息
    function sendMessage() {
        const nickname = document.getElementById('nickname').value.trim();
        const message = document.getElementById('messageInput').value.trim();

        if (!nickname) {
            alert('请先输入昵称');
            return;
        }
        if (!message) return;

        stompClient.send("/app/chat", {}, JSON.stringify({
            sender: nickname,
            content: message
        }));

        document.getElementById('messageInput').value = '';
    }

    // 页面加载自动连接
    window.onload = connect;



   // -----------------------------------------------------------
   document.addEventListener('DOMContentLoaded', () => {
    const form = document.getElementById('trackForm');
    const urlInput = document.getElementById('houseUrl');
    const resultBox = document.getElementById('result'); // 保留提示用的 box
  
    form.addEventListener('submit', async (e) => {
      e.preventDefault();
      const url = urlInput.value;
  
      try {
        const response = await fetch(`http://localhost:8080/scrape-house?urlRaw=${encodeURIComponent(url)}`);
  
        if (!response.ok) {
          if (response.status === 409) {
            throw new Error("房源已存在，不能重复添加");
          } else {
            throw new Error("你网站有没输错啊?");
          }
        }
  
        // 成功提示
        resultBox.style.display = 'block';
        resultBox.textContent = '✅ 已成功记录该房源链接，开始追踪价格变化。';
  
      } catch (error) {
        // 统一处理错误信息，包括 409 或其他错误
        resultBox.style.display = 'block';
        resultBox.textContent = `⚠️ ${error.message}`;
      }
    });
  });
  
  //--------------------------------------------------------------
  document.addEventListener('DOMContentLoaded', () => {
    loadUrlOptions();  // 页面加载完成后调用
  });
  
  const urlSelect = document.getElementById('houseUrlSelect');
  const table = document.getElementById('houseTable');
  const tbody = table.querySelector('tbody');
  
  // 加载房源URL下拉选项
  async function loadUrlOptions() {
    const res = await fetch('http://localhost:8080/get-house');
    const houses = await res.json(); // 这是一个 SuumoHouseInfo 的数组
  
    houses.forEach(house => {
      const option = document.createElement('option');
      option.value = house.url;  // 选择时仍可以用 URL 做标识
      option.textContent = `${house.address} - ${house.price}`; // 显示地址和价格
      urlSelect.appendChild(option);
    });
  }
  

  // 根据选中 URL 加载对应记录
  async function loadHouseRecords(url) {
    const res = await fetch(`http://localhost:8080/api/house-records?url=${encodeURIComponent(url)}`);
    const records = await res.json();
  
    tbody.innerHTML = '';
    table.style.display = 'table';
  
    records.forEach(item => {
      const row = document.createElement('tr');
      row.innerHTML = `
        <td>${item.crawlDate || ''}</td>
        <td>${item.price || ''}</td>
        <td>${item.houseName || ''}</td>
        <td>${item.layout || ''}</td>
        <td>${item.landArea || ''}</td>
        <td>${item.buildingArea || ''}</td>
        <td>${item.buildYearMonth || ''}</td>
        <td>${item.address || ''}</td>
      `;
      tbody.appendChild(row);
    });
  }

  urlSelect.addEventListener('change', () => {
    const selectedUrl = urlSelect.value;
    if (selectedUrl) {
      loadHouseRecords(selectedUrl);
    } else {
      table.style.display = 'none';
    }
  });

  document.addEventListener('DOMContentLoaded', loadUrlOptions);