<#import "/spring.ftl" as spring>

<#macro layout title="JobSearch">
    <!DOCTYPE html>
    <html lang="ru" class="h-100">
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <#if _csrf??>
            <meta name="_csrf" content="${_csrf.token}">
            <meta name="_csrf_header" content="${_csrf.headerName}">
        </#if>

        <link rel="stylesheet"
              href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css">

        <link rel="stylesheet"
              href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.3/font/bootstrap-icons.min.css">

        <link rel="stylesheet" href="/static/styles/style.css">
        <link rel="icon" href="/static/favicon.ico" type="image/x-icon">

    </head>

    <body class="d-flex flex-column h-100">

    <header class="mb-4">
        <nav class="navbar navbar-expand-lg navbar-dark">
            <a class="navbar-brand" href="/">
                <i class="bi bi-briefcase-fill"></i> JobSearch
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">

                <#assign _auth = (SPRING_SECURITY_CONTEXT.authentication)!>
                <#assign _isAuth = (_auth?? && _auth.principal?has_content && _auth.principal != 'anonymousUser')>
                <#assign _authorities = (_auth.authorities)![]>
                <#assign _roleNames = []>
                <#list _authorities as a>
                    <#assign _roleNames = _roleNames + [(a.authority!'')?string]>
                </#list>
                <#assign _isApplicant = _roleNames?seq_contains('ROLE_APPLICANT')>
                <#assign _isEmployer  = _roleNames?seq_contains('ROLE_EMPLOYER')>

                <#-- локализованные подписи ролей -->
                <#assign _appLabel = springMacroRequestContext.getMessage('layout.role.applicant')>
                <#assign _empLabel = springMacroRequestContext.getMessage('layout.role.employer')>
                <#assign _accountTypeLabel = _isApplicant?then(_appLabel, _isEmployer?then(_empLabel,''))>

                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" id="langDropdown"
                           data-bs-toggle="dropdown" aria-expanded="false">
                            <i class="bi bi-translate"></i> <@spring.message "layout.navbar.chng_lang"/>
                        </a>
                        <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="langDropdown">
                            <li><a class="dropdown-item"
                                   href="/?lang=en"><@spring.message "layout.navbar.chng_lang.en"/></a></li>
                            <li><a class="dropdown-item"
                                   href="/?lang=ru"><@spring.message "layout.navbar.chng_lang.ru"/></a></li>
                        </ul>
                    </li>

                    <#if _isAuth>

                        <#if !_isApplicant>
                            <li class="nav-item">
                                <a class="nav-link" href="/resumes">
                                    <i class="bi bi-person-lines-fill"></i> <@spring.message "layout.resume"/>
                                </a>
                            </li>
                        </#if>

                        <#if !_isEmployer>
                            <li class="nav-item">
                                <a class="nav-link" href="/vacancies">
                                    <i class="bi bi-card-list"></i> <@spring.message "layout.vacancy"/>
                                </a>
                            </li>
                        </#if>

                        <li class="nav-item">
                            <a class="nav-link" href="/profile">
                                <i class="bi bi-person-circle"></i> <@spring.message "layout.profile"/>
                            </a>
                        </li>

                        <#if _isEmployer>
                            <li class="nav-item">
                                <a class="nav-link" href="/vacancy/new">
                                    <i class="bi bi-plus-square"></i> <@spring.message "layout.create_vacancy"/>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/profile/vacancies">
                                    <i class="bi bi-folder"></i> <@spring.message "layout.my_vacancies"/>
                                </a>
                            </li>
                        </#if>

                        <#if _isApplicant>
                            <li class="nav-item">
                                <a class="nav-link" href="/resume/new">
                                    <i class="bi bi-plus-square"></i> <@spring.message "layout.create_resume"/>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a class="nav-link" href="/profile/resumes">
                                    <i class="bi bi-folder"></i> <@spring.message "layout.my_resume"/>
                                </a>
                            </li>
                        </#if>
                    </#if>
                </ul>

                <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                    <#if _isAuth>
                        <li class="nav-item position-relative">
                            <a class="nav-link d-flex align-items-center gap-1" href="/chats"
                               id="chatLink"
                               data-role="${_isEmployer?then('EMPLOYER', _isApplicant?then('APPLICANT','GUEST'))}">
                                <i class="bi bi-chat-dots"></i>
                                <span id="chatBadge"
                                      class="badge rounded-pill text-bg-danger ms-1"
                                      style="display:none; min-width: 1.5rem; font-weight:600;">0</span>
                            </a>
                        </li>

                        <li class="nav-item">
                            <span class="nav-link disabled">${_accountTypeLabel}</span>
                        </li>
                        <li class="nav-item">
                            <form action="/logout" method="post" class="d-inline">
                                <#if _csrf??>
                                    <input
                                            type="hidden"
                                            name="${(_csrf.parameterName)!'csrf-param-name'}"
                                            value="${(_csrf.token)!'csrf-token'}"
                                    />
                                </#if>
                                <button type="submit" class="btn btn-outline-light btn-sm">
                                    <i class="bi bi-box-arrow-right"></i> <@spring.message "layout.logout"/>
                                </button>
                            </form>
                        </li>
                    <#else>
                        <li class="nav-item">
                            <a class="nav-link" href="/auth/login">
                                <i class="bi bi-box-arrow-in-right"></i> <@spring.message "layout.login"/>
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/auth/chooseRole">
                                <i class="bi bi-person-plus"></i> <@spring.message "layout.registration"/>
                            </a>
                        </li>
                    </#if>
                </ul>
            </div>
        </nav>
    </header>

    <main class="container flex-grow-1 mb-5">
        <#nested>
    </main>

    <footer class="footer text-center py-3 mt-auto">
        <div class="container">
            <p class="mb-0">© ${.now?string("yyyy")} JobSearch</p>
        </div>
    </footer>
    <script>
        (function () {
            const link  = document.getElementById('chatLink');
            if (!link) return;

            const badge = document.getElementById('chatBadge');
            const role  = link.getAttribute('data-role') || 'GUEST';

            const csrfToken  = document.querySelector('meta[name="_csrf"]')?.getAttribute('content');
            const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.getAttribute('content');

            // ---- i18n для тултипа
            const I18N = {
                unread: "<@spring.message 'layout.chat.unread'/>",
                responses: "<@spring.message 'layout.chat.responses'/>",
                answers: "<@spring.message 'layout.chat.answers'/>"
            };

            // ---- Настрой здесь реальные эндпоинты своей бэкенд-логики ----
            // Дай два счётчика: непрочитанные сообщения и число откликов/ответов
            const ENDPOINTS = {
                messages: '/api/chat/unread-count',                // для всех ролей
                employerResponds: '/api/respond/pending-count',    // для EMPLOYER
                applicantAnswers: '/api/respond/answers-count'     // для APPLICANT
            };

            function apiGet(url) {
                const opt = { method: 'GET', headers: {} };
                if (csrfHeader && csrfToken) opt.headers[csrfHeader] = csrfToken;
                return fetch(url, opt).then(r => r.ok ? r.json() : { count: 0 })
                    .catch(() => ({ count: 0 }));
            }

            Promise.all([
                apiGet(ENDPOINTS.messages),
                role === 'EMPLOYER'  ? apiGet(ENDPOINTS.employerResponds) :
                    role === 'APPLICANT' ? apiGet(ENDPOINTS.applicantAnswers) :
                        Promise.resolve({ count: 0 })
            ]).then(([m, r]) => {
                const mCount = Number(m.count || 0);
                const rCount = Number(r.count || 0);
                const total  = mCount + rCount;

                if (total > 0) {
                    badge.textContent = (total > 99 ? '99+' : String(total));
                    badge.style.display = '';
                    link.title = role === 'EMPLOYER'
                        ? I18N.unread + ': ' + mCount + ', ' + I18N.responses + ': ' + rCount
                        : I18N.unread + ': ' + mCount + ', ' + I18N.answers + ': ' + rCount;

                } else {
                    badge.style.display = 'none';
                    link.removeAttribute('title');
                }
            });
        })();
    </script>

    <script defer src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
            crossorigin="anonymous"></script>
    </body>
    </html>
</#macro>
