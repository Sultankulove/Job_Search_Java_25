<#import "layout.ftlh" as main>

<@main.layout ; spring>
<#--    <!DOCTYPE html>-->
<#--    <html lang="ru">-->
<#--    <head>-->
<#--        <meta charset="UTF-8">-->
<#--        <title>Резюме</title>-->
<#--        <link rel="stylesheet" href="/static/styles/style.css">-->
<#--        <style>-->
<#--            .container{max-width:1000px;margin:32px auto;padding:0 16px}-->
<#--            .toolbar{display:flex;justify-content:space-between;align-items:center;margin-bottom:16px}-->
<#--            .btn{display:inline-block;padding:8px 12px;border-radius:8px;border:1px solid #ddd;text-decoration:none}-->
<#--            .table{width:100%;border-collapse:collapse}-->
<#--            .table th,.table td{border-bottom:1px solid #eee;padding:8px 10px;text-align:left}-->
<#--            .muted{color:#666}-->
<#--            .badge{padding:2px 8px;border-radius:12px;font-size:12px}-->
<#--            .badge-on{background:#e7f6ec}-->
<#--            .badge-off{background:#f1f1f1}-->
<#--            .empty{padding:24px;border:1px dashed #ddd;border-radius:8px;color:#777}-->
<#--        </style>-->
<#--    </head>-->
<#--    <body>-->
<#--    <div class="container">-->
<#--        <div class="toolbar">-->
<#--            <h1>Резюме</h1>-->
<#--            <a class="btn" href="/resume/new">+ Создать резюме</a>-->
<#--        </div>-->

<#--        <p class="muted">Найдено: ${resumes?size?c}</p>-->

<#--        <#if resumes?has_content>-->
<#--            <table class="table">-->
<#--                <thead>-->
<#--                <tr>-->
<#--                    <th>Название</th>-->
<#--                    <th>Категория (ID)</th>-->
<#--                    <th>Зарплата</th>-->
<#--                    <th>Активно</th>-->
<#--                    <th>Создано</th>-->
<#--                    <th>Обновлено</th>-->
<#--                </tr>-->
<#--                </thead>-->
<#--                <tbody>-->
<#--                <#list resumes as r>-->
<#--                    <tr>-->
<#--                        <td>${r.name!"—"}</td>-->
<#--                        <td>${r.categoryId!"—"}</td>-->
<#--                        <td>-->
<#--                            <#if r.salary??>${r.salary}<#else>-</#if>-->

<#--                        </td>-->
<#--                        <td>-->
<#--                            <#assign active = (r.active)!((r.active)!false)>-->
<#--                            <span class="badge ${active?then('badge-on','badge-off')}">-->
<#--                            ${active?string('Активно','Не активно')}-->
<#--                        </span>-->
<#--                        </td>-->
<#--                        <td>-->
<#--                            <#if r.createdDate??>-->
<#--                                ${r.createdDate}-->
<#--                            <#else>—</#if>-->
<#--                        </td>-->
<#--                        <td>-->
<#--                            <#if r.updateTime??>-->
<#--                                ${r.updateTime}-->
<#--                            <#else>—</#if>-->
<#--                        </td>-->
<#--                    </tr>-->
<#--                </#list>-->
<#--                </tbody>-->
<#--            </table>-->
<#--        <#else>-->
<#--            <div class="empty">Пока нет резюме.</div>-->
<#--        </#if>-->
<#--    </div>-->
<#--    </body>-->
<#--    </html>-->


<#-- templates/resumes/list.ftlh -->
    <#import "/spring.ftl" as spring>
    <!DOCTYPE html>
    <html>
    <head>
        <meta charset="UTF-8"/>
        <title>Резюме</title>
        <link rel="stylesheet" href="/static/styles/style.css"/>
    </head>
    <body>
    <h1>Список резюме</h1>

    <#if resumes?has_content>
        <table>
            <thead>
            <tr>
                <th>Название</th>
                <th>Категория (id)</th>
                <th>Зарплата</th>
                <th>Активно</th>
                <th>Обновлено</th>
            </tr>
            </thead>
            <tbody>
            <#list resumes as r>
                <tr>
                    <td>${r.name!}</td>
                    <td>${r.categoryId!}</td>
                    <td>
                        <#if r.salary??>${r.salary}<#else>-</#if>
                    </td>
                    <td><#if r.active?? && r.active>Да<#else>Нет</#if></td>
                    <td>${r.updateTime}</td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <p>Резюме не найдены.</p>
    </#if>

    </body>
    </html>

</@main.layout>