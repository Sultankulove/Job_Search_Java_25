<#import "/spring.ftl" as spring>
<#include "layout.ftlh">

<@layout title="Просмотр">
    <div class="container mt-4">
        <#if r??>
            <h2>Резюме: ${r.name}</h2>
            <p>Категория: ${r.category.name}</p>
            <p>Желаемая зарплата: ${r.salary}</p>
            <p>Статус: ${r.active?string("Активно","Не активно")}</p>
            <p>Обновлено:
                <#if r.updateTime?has_content>
                    <#if r.updateTime?is_date_like>
                        ${r.updateTime?datetime?string("dd.MM.yyyy HH:mm")}
                    <#else>
                        ${r.updateTime}
                    </#if>
                </#if>
            </p>

            <hr>

            <#if r.educationInfos??>
                <h3>Образование </h3>
            </#if>
            <#list r.educationInfos as educationInfo >
                <p>Учебное заведение: ${educationInfo.institution}</p>
                <p>Программа: ${educationInfo.program}</p>
                <p>Степень: ${educationInfo.degree}</p>
                <p>Дата начала: ${educationInfo.startDate}</p>
                <p>Дата конца: ${educationInfo.endDate}</p>
            </#list>

            <hr>

            <#if r.workExperiences??>
                <h3>Опыт работы</h3>
            </#if>
            <#list r.workExperiences as workExp>
                <p>Компания: ${workExp.companyName}</p>
                <p>Позиция: ${workExp.position}</p>
                <p>Обязанности: ${workExp.responsibilities}</p>
                <p>Сколько лет: ${workExp.years}</p>
            </#list>

            <hr>
            <#if r.contactInfos??>
                <h3>Контакты</h3>
            </#if>
            <#list r.contactInfos as contacts>
                <#list contact as c>
                    <#if c.id==contacts.typeId>
                        <p>${c.name}: ${contacts.contactValue}</p>
                    </#if>
                </#list>
            </#list>

        <#--            <#list r.-->
        <#--             <p>Обновлено: -->
        <#--                 <#if v.updateTime??> -->
        <#--                         ${v.updateTime!''} -->
        <#--                 <#else>-</#if> -->
        <#--             </p> -->

        </#if>

        <#if v??>
            <h2>Вакансия: ${v.name}</h2>
            <p>Категория: ${v.categoryName}</p>
            <p>Зарплата: ${v.salary}</p>
            <p>Опыт: ${v.expFrom}–${v.expTo} лет</p>
            <p>Описание: ${v.description}</p>
            <p>Обновлено: ${v.updateTime}</p>

            <#if auth && role == "ROLE_APPLICANT">
                <#if responded?? && responded>
                    <button class="btn btn-secondary" disabled>Вы уже откликнулись</button>
                <#else>
                    <form method="post" action="/vacancy/${v.id}/respond">
                        <#if _csrf??>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        </#if>

                        <button class="btn btn-primary">Откликнуться</button>
                    </form>
                </#if>
            <#elseif !auth>
                <a href="/login" class="btn btn-warning">Войти, чтобы откликнуться</a>
            </#if>

            <#if role == "ROLE_EMPLOYER" && responses??>
                <h4 class="mt-4">Отклики:</h4>
                <ul>
                    <#list responses as resp>
                        <li>
                            Соискатель: ${resp.applicantName}
                            <form method="post" action="/chat/start/${resp.id}" style="display:inline">
                                <#if _csrf??>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                </#if>

                                <button class="btn btn-sm btn-success">Начать чат</button>
                            </form>
                        </li>
                    </#list>
                </ul>
            </#if>
        </#if>
    </div>
</@layout>
