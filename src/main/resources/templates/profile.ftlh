<#import "layout.ftlh" as main>

<@main.layout ; spring>

<#--     <#if SPRING_SECURITY_CONTEXT?? && SPRING_SECURITY_CONTEXT.authentication??>-->
<#--        <#assign user = SPRING_SECURITY_CONTEXT.authentication.principal>-->
<#--    <#assign br = .data_model["org.springframework.validation.BindingResult.dto"]! />-->

<#--    <div class="row">-->
<#--            <label for="avatar">Фото профиля (необязательно)</label>-->
<#--            <input id="avatar" name="avatar" type="file" accept="image/*">-->
<#--            <#if br?? && br.hasFieldErrors("avatar")>-->
<#--                <div class="error"><#list br.getFieldErrors("avatar") as e>${e.defaultMessage}<#if e_has_next></#if></#list></div>-->
<#--            </#if>-->
<#--        </div>-->
<#--     </#if>-->


<#--    <#import "/org/springframework/web/servlet/view/freemarker/spring.ftl" as spring>-->

<#--    <div class="col">-->
<#--        <img src="/api/profile/avatar" class="img-thumbnail" width="300" height="200" alt="avatar">-->
<#--    </div>-->


<div class="container text-center">
    <div class="col">
        <div class="row">
            <img src="/api/profile/avatar" class="img-thumbnail" width="300" height="200" alt="avatar">
        </div>
        <div class="row">
            <fieldset>
<#--                <p>Имя Фамилия</p>-->
                <legend>${user.name} ${user.surname}</legend>


<#--                    <#if user??>-->
<#--                                <#list usr.authorities as auth>-->
<#--                                    <legend>Роль: ${auth}</legend>-->
<#--                                    <#if auth?has_next></#if></#list>-->
<#--                    </#if>-->

                <#if SPRING_SECURITY_CONTEXT?? && SPRING_SECURITY_CONTEXT.authentication??>
<#--                    <#assign usr = SPRING_SECURITY_CONTEXT.authentication.principal name = usr.getUsername()>-->

                    <#assign usr = SPRING_SECURITY_CONTEXT.authentication.principal>
                    <#if usr?? && usr.authorities?has_content>
                        <#list usr.authorities as a>
                            <#assign role = a.authority!a?string>
                            <#if role == "ROLE_EMPLOYER">
                                <legend>Роль: работодатель</legend>
                            <#elseif role == "ROLE_APPLICANT">
                                <legend>Роль: соискатель</legend>
                            </#if>
                        </#list>
                    </#if>
                </#if>




                <legend>Email: ${user.email}</legend>
                <legend>Телефон: ${user.phoneNumber}</legend>
                <legend>Возраст: ${user.age}</legend>
<#--                <legend>Тип аккаунта: ${spring.}</legend>-->

<#--                <#assign known = SPRING_SECURITY_CONTEXT?? >-->


            </fieldset>

            <a class="btn btn-outline-secondary" href="/profile/edit">Редактировать</a>
        </div>

    </div>


<#--    <form action="/profile/avatar" method="post" enctype="multipart/form-data">-->
<#--&lt;#&ndash;        <input type="hidden">&ndash;&gt;-->
<#--        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->

<#--        <label for="avatar">Фото профиля (необязательно)</label>-->
<#--        <input id="avatar" name="avatar" type="file" accept="image/*"/>-->

<#--        <@spring.bind "user.avatar"/>-->
<#--        <#if spring.status.errorMessages?has_content>-->
<#--            <div class="error">-->
<#--                <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>-->
<#--            </div>-->
<#--        </#if>-->
<#--        <br>-->
<#--        <button type="submit">Сохранить</button>-->
<#--    </form>-->


</@main.layout>
