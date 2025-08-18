<#import "spring.ftl" as spring>
<#macro layout>

    <!DOCTYPE html>
    <html lang="en">

    <head>
        <meta charset="UTF-8">
        <title>Login form</title>
        <link rel="stylesheet" href="../static/styles/style.css">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
              integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
                crossorigin="anonymous"></script>
    </head>

    <body>
    <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <div class="container-fluid">
            <a class="navbar-brand" href="/">Job Search</a>
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                    <li class="nav-item">
                        <a class="nav-link active" aria-current="page" href="/vacancies">Cоискателям</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/resumes">у</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="/auth/login">Войти</a>
                    </li>



                    <li class="nav-item">
                        <#if SPRING_SECURITY_CONTEXT?? && SPRING_SECURITY_CONTEXT.authentication??>
                            <#assign user = SPRING_SECURITY_CONTEXT.authentication.principal>
                            <#if user?? && user.authorities?has_content>
                                <#list user.authorities as a>
                                    <#assign role = a.authority!a?string>
                                    <#if role == "ROLE_EMPLOYER">
                                        <a class="nav-link" href="/resumes">Список резюме</a>
                                    <#elseif role == "ROLE_APPLICANT">
                                        <a class="nav-link" href="/vacancies">Список вакансий</a>
                                    </#if>
                                </#list>
                            </#if>
                        </#if>
                    </li>

<#--                    <li class="nav-item dropdown">-->
<#--                        <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"-->
<#--                           data-bs-toggle="dropdown" aria-expanded="false">-->
<#--                            Профиль-->
<#--                        </a>-->
<#--                        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">-->
<#--                            <li><a class="dropdown-item" href="/resumes">Список резюме</a></li>-->

<#--                            <li><a class="dropdown-item" href="/vacancies">Список вакансий</a></li>-->
<#--                            <li>-->
<#--                                <hr class="dropdown-divider">-->
<#--                            </li>-->
<#--                            <li><a class="dropdown-item" href="/logout">Выйти</a></li>-->
<#--                        </ul>-->
<#--                    </li>-->
                </ul>
                <form class="d-flex">
                    <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                    <button class="btn btn-outline-success" type="submit">Поиск</button>
                </form>
            </div>

            <#assign known = SPRING_SECURITY_CONTEXT?? >
            <#if known>
                <#assign
                user = SPRING_SECURITY_CONTEXT.authentication.principal
                name = user.getUsername()
                >
                <#if user??>
                    <div class="btn btn-outline-success">
                        <a href="/profile" class="text-decoration-none">
                            ${name}
                            <#list user.authorities as auth>${auth}
                                <#if auth?has_next></#if></#list>
                        </a>
                    </div>

                    <form action="/logout" method="post">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                        <button type="submit" class="btn btn-outline-info m-2"
                                id="logout">
                            Logout
                        </button>
                    </form>
                </#if>
            <#else>
                <a href="/auth/login"
                   class="btn btn-outline-info m-2">Login</a>
                <a href="/auth/register"
                   class="btn btn-outline-info m-2">Register</a>
            </#if>

        </div>
    </nav>


    <div class="container">
        <#nested spring>
    </div>
    </body>
    <script src="http://localhost:35729/livereload.js?snipver=1"></script>

    </html>

</#macro>