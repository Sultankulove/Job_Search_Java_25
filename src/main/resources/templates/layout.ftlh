
<#import "/spring.ftl" as spring>

<#macro layout title="JobSearch">
    <!DOCTYPE html>
    <html lang="ru">
    <head>
        <meta charset="UTF-8">
        <title>${title}</title>
        <meta name="viewport" content="width=device-width, initial-scale=1" />
        <link rel="stylesheet" href="/static/styles/style.css">
    </head>
    <body>
    <header class="topbar">
        <div class="container topbar__inner">
            <a class="brand" href="/">JobSearch</a>
            <nav class="nav">
                <a href="/vacancies">Вакансии</a>
                <a href="/resumes">Резюме</a>



                <#if SPRING_SECURITY_CONTEXT??
                && SPRING_SECURITY_CONTEXT.authentication??
                && SPRING_SECURITY_CONTEXT.authentication.principal?has_content
                && SPRING_SECURITY_CONTEXT.authentication.principal != 'anonymousUser'>
                    <a href="/profile">Профиль</a>

                    <#assign roles = (SPRING_SECURITY_CONTEXT.authentication.authorities)![]>

                    <#list roles as a>
                        <#if a.authority?string == 'ROLE_EMPLOYER'>
                            <a class="btn" href="/vacancy/new">Создать вакансию</a>
                            <a class="btn" href="/profile/vacancies">Мои вакансии</a>
                            <#break>
                        </#if>
                    </#list>


                    <#list roles as a>
                        <#if a.authority?string == 'ROLE_APPLICANT'>
                            <a class="btn" href="/resume/new">Создать резюме</a>
                            <a class="btn" href="/profile/resumes">Мои резюме</a>
                            <#break>
                        </#if>
                    </#list>


                    <form action="/logout" method="post" class="inline">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        <button class="btn btn--ghost" type="submit">Выйти</button>
                    </form>
                <#else>
                    <a href="/auth/login">Войти</a>
                    <a href="/auth/register" class="btn btn--primary">Регистрация</a>
                </#if>

            </nav>
        </div>
    </header>

    <main class="container content">
        <#nested>
    </main>

    <footer class="footer">
        <div class="container">
            <p>© ${.now?string("yyyy")} JobSearch</p>
        </div>
    </footer>
    </body>
    </html>
</#macro>
