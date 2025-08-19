
<#import "layout.ftlh" as l>
<#import "spring.ftl" as spring>


<@l.layout title="Редактирование профиля">
    <div class="card">
        <h1>Редактирование профиля</h1>

        <form action="/profile/edit" method="post" class="form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

            <@spring.bind "dto.name"/>
            <label>Имя
                <input name="${spring.status.expression}" value="${spring.status.value!}">
            </label>
            <#if spring.status.errorMessages?has_content>
                <div class="error"><#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list></div>
            </#if>

            <@spring.bind "dto.surname"/>
            <label>Фамилия
                <input name="${spring.status.expression}" value="${spring.status.value!}">
            </label>

            <@spring.bind "dto.age"/>
            <label>Возраст
                <input type="number" name="${spring.status.expression}" value="${spring.status.value!}">
            </label>

            <@spring.bind "dto.phoneNumber"/>
            <label>Телефон
                <input name="${spring.status.expression}" value="${spring.status.value!}">
            </label>

            <@spring.bind "dto.email"/>
            <label>Email
                <input type="email" name="${spring.status.expression}" value="${spring.status.value!}" readonly>
            </label>

            <button class="btn btn--primary" type="submit">Сохранить</button>
        </form>

        <hr>

        <form action="/profile/avatar" method="post" enctype="multipart/form-data" class="form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            <div class="avatar-upload">
                <img class="avatar" src="${(dto.avatar)!'/images/default-avatar.png'}" alt="">
                <input type="file" name="avatar" accept="image/*">
            </div>
            <button class="btn" type="submit">Загрузить фото</button>
        </form>
    </div>
</@l.layout>
