<#import "../layout.ftlh" as main>

<@main.layout ; spring>


<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>Регистрация</title>
    <link rel="stylesheet" href="/static/styles/style.css">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <style>
        .form {max-width: 520px; margin: 32px auto; padding: 24px; background:#fff; border-radius: 12px; box-shadow:0 6px 24px rgba(0,0,0,.06)}
        .row{margin-bottom:14px}
        .row label{display:block; font-weight:600; margin-bottom:6px}
        .row input, .row select {width:100%; padding:10px 12px; border:1px solid #d7dbe0; border-radius:8px; outline:0}
        .error {color:#b00020; font-size:13px; margin-top:6px}
        .alert {background:#fff4f4; color:#b00020; border:1px solid #ffd7d7; padding:10px 12px; border-radius:8px; margin-bottom:12px}
        .actions{display:flex; gap:12px; align-items:center; margin-top:8px}
        .btn{padding:10px 16px; border-radius:10px; border:0; cursor:pointer}
        .btn-primary{background:#2563eb; color:#fff}
    </style>
</head>
<body>

<div class="form">
    <h1 style="margin:0 0 8px">Регистрация</h1>
    <p style="margin-top:0;color:#555">Заполните поля ниже. Поле с фото — необязательно.</p>

    <#assign br = .data_model["org.springframework.validation.BindingResult.dto"]! />

    <#if br?? && br.globalErrors?has_content>
        <div class="alert">
            <#list br.globalErrors as ge>${ge.defaultMessage}<#if ge_has_next><br/></#if></#list>
        </div>
    </#if>

    <form action="/auth/register" method="post" enctype="multipart/form-data" novalidate>
        <#if _csrf??>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        </#if>

        <div class="row">
            <label for="accountType">Тип аккаунта</label>
            <select id="accountType" name="accountType" required>
                <option value="APPLICANT">Соискатель</option>
                <option value="EMPLOYER">Работодатель</option>
            </select>
            <#if br?? && br.hasFieldErrors("accountType")>
                <div class="error"><#list br.getFieldErrors("accountType") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list></div>
            </#if>
        </div>

        <div class="row">
            <label id="nameLabel" for="name">
<#--                <#if dto?has_content && (dto.accountType?string?lower_case?contains("EMPLOYER"))>-->
<#--                    Имя компании-->
<#--                <#else>-->
<#--                    Имя пользователя-->
<#--                </#if>-->
                Имя
            </label>
            <input id="name" name="name" type="text" value="${(dto.name)!}" required>
            <#if br?? && br.hasFieldErrors("name")>
                <div class="error"><#list br.getFieldErrors("name") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list></div>
            </#if>
        </div>

        <div class="row">
            <label for="email">Email</label>
            <input id="email" name="email" type="email" value="${(dto.email)!}" required>
            <#if br?? && br.hasFieldErrors("email")>
                <div class="error"><#list br.getFieldErrors("email") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list></div>
            </#if>
        </div>


        <div class="row">
            <label for="phoneNumber">Телефон</label>
            <input id="phoneNumber" name="phoneNumber" type="tel" value="${(dto.phoneNumber)!}" required>
            <#if br?? && br.hasFieldErrors("phone")>
                <div class="error"><#list br.getFieldErrors("phoneNumber") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list></div>
            </#if>
        </div>

        <div class="row">
            <label for="password">Пароль</label>
            <input id="password" name="password" type="password" required>
            <#if br?? && br.hasFieldErrors("password")>
                <div class="error"><#list br.getFieldErrors("password") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list></div>
            </#if>
        </div>

        <div class="row">
            <label for="confirmPassword">Подтверждение пароля</label>
            <input id="confirmPassword" name="confirmPassword" type="password" required>
            <#if br?? && br.hasFieldErrors("confirmPassword")>
                <div class="error"><#list br.getFieldErrors("confirmPassword") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list></div>
            </#if>
        </div>

        <div class="row">
            <label for="avatar">Фото профиля (необязательно)</label>
            <input id="avatar" name="avatar" type="file" accept="image/*">
            <#if br?? && br.hasFieldErrors("avatar")>
                <div class="error"><#list br.getFieldErrors("avatar") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list></div>
            </#if>
        </div>

        <div class="actions">
            <button type="submit" class="btn btn-primary">Зарегистрироваться</button>
            <a href="/auth/login">Уже есть аккаунт?</a>
        </div>
    </form>
</div>

</body>
</html>

</@main.layout>
