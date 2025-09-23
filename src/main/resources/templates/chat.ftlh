<#import "layout.ftlh" as main>
<@main.layout title="Чат">
    <div class="row">
        <div class="col-12 col-md-8 col-lg-6 mx-auto">
            <h5 class="mb-3">Чат по отклику #${responseId}</h5>

            <div id="chatBox" class="border rounded p-3 mb-3" style="height: 45vh; overflow-y: auto;">
                <#list messages as m>
                    <div class="mb-2">
                        <div class="small text-muted">${m.timestamp?string("dd.MM.yyyy HH:mm")}</div>
                        <div>${m.content}</div>
                    </div>
                </#list>
            </div>

            <div class="input-group">
                <input id="msgInput" class="form-control" type="text" placeholder="Сообщение..." />
                <button id="sendBtn" class="btn btn-primary">Отправить</button>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/stompjs@2.3.3/lib/stomp.min.js"></script>
    <script>
        (function () {
            const responseId = ${responseId};
            const chatBox = document.getElementById('chatBox');
            const input = document.getElementById('msgInput');
            const btn = document.getElementById('sendBtn');

            function appendMsg(msg) {
                const wrap = document.createElement('div');
                wrap.className = 'mb-2';
                const ts = new Date(msg.timestamp || Date.now());
                wrap.innerHTML =
                    '<div class="small text-muted">' + ts.toLocaleString() + '</div>' +
                    '<div>' + (msg.content || '').replace(/[<&>]/g, s => ({
                        '<': '&lt;',
                        '>': '&gt;',
                        '&': '&amp;'
                    }[s])) + '</div>';
                chatBox.appendChild(wrap);
                chatBox.scrollTop = chatBox.scrollHeight;
            }

            const socket = new SockJS('/ws');
            const stomp = Stomp.over(socket);


            stomp.connect({}, function () {

                stomp.subscribe('/topic/chat.' + responseId, function (frame) {
                    const msg = JSON.parse(frame.body);
                    appendMsg(msg);
                });

                btn.addEventListener('click', function () {
                    const text = input.value.trim();
                    if (!text) return;
                    stomp.send('/app/chat/' + responseId + '/send', {}, JSON.stringify({content: text}));
                    input.value = '';
                    input.focus();
                });

                input.addEventListener('keydown', function (e) {
                    if (e.key === 'Enter') btn.click();
                });
            });
        })();
    </script>
</@main.layout>
