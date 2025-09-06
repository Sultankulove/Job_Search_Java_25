<#import "/spring.ftl" as spring>

<#macro layout title="JobSearch">
    <!DOCTYPE html>
    <html lang="ru" class="h-100">
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">

        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.10.5/font/bootstrap-icons.css" rel="stylesheet">

        <link rel="stylesheet" href="/static/styles/style.css">
    </head>

    <body class="d-flex flex-column h-100">

    <header class="mb-4">
        <nav class="navbar navbar-expand-lg navbar-dark">
            <a class="navbar-brand" href="/">
                <i class="bi bi-briefcase-fill"></i> JobSearch
            </a>

            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link" href="/vacancies">
                            <i class="bi bi-card-list"></i> Вакансии
                        </a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/resumes">
                            <i class="bi bi-person-lines-fill"></i> Резюме
                        </a>
                    </li>

                    <#if SPRING_SECURITY_CONTEXT?? &&
                    SPRING_SECURITY_CONTEXT.authentication?? &&
                    SPRING_SECURITY_CONTEXT.authentication.principal?has_content &&
                    SPRING_SECURITY_CONTEXT.authentication.principal != 'anonymousUser'>

                        <li class="nav-item">
                            <a class="nav-link" href="/profile">
                                <i class="bi bi-person-circle"></i> Профиль
                            </a>
                        </li>

                        <#assign roles = (SPRING_SECURITY_CONTEXT.authentication.authorities)![]>

                        <#list roles as a>
                            <#if a.authority?string == 'ROLE_EMPLOYER'>
                                <li class="nav-item">
                                    <a class="nav-link" href="/vacancy/new">
                                        <i class="bi bi-plus-square"></i> Создать вакансию
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="/profile/vacancies">
                                        <i class="bi bi-folder"></i> Мои вакансии
                                    </a>
                                </li>
                                <#break>
                            </#if>
                        </#list>

                        <#list roles as a>
                            <#if a.authority?string == 'ROLE_APPLICANT'>
                                <li class="nav-item">
                                    <a class="nav-link" href="/resume/new">
                                        <i class="bi bi-plus-square"></i> Создать резюме
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="/profile/resumes">
                                        <i class="bi bi-folder"></i> Мои резюме
                                    </a>
                                </li>
                                <#break>
                            </#if>
                        </#list>
                    </#if>
                </ul>

                <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
                    <#if SPRING_SECURITY_CONTEXT?? &&
                    SPRING_SECURITY_CONTEXT.authentication?? &&
                    SPRING_SECURITY_CONTEXT.authentication.principal?has_content &&
                    SPRING_SECURITY_CONTEXT.authentication.principal != 'anonymousUser'>
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
                                    <i class="bi bi-box-arrow-right"></i> Выйти
                                </button>
                            </form>
                        </li>
                    <#else>
                        <li class="nav-item">
                            <a class="nav-link" href="/auth/login">
                                <i class="bi bi-box-arrow-in-right"></i> Войти
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="/auth/chooseRole">
                                <i class="bi bi-person-plus"></i> Регистрация
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

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    </body>
    </html>
</#macro>
