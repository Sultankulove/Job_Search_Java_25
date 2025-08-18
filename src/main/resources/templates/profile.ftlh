<#import "layout.ftlh" as main>

<@main.layout ; spring>


<div class="container text-center">
    <div class="col">
        <div class="row">
            <img src="/api/profile/avatar" class="img-thumbnail" width="300" height="200" alt="avatar">
        </div>
        <div class="row">
            <fieldset>
                <legend>${user.name} ${user.surname}</legend>



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

                <#if user?? && (user.age)??>
                    Возраст: ${user.age}
                <#else>
                    Возраст не указан
                </#if>

            </fieldset>
            <a class="btn btn-outline-secondary" href="/profile/edit">Редактировать</a>
        </div>
    </div>


</@main.layout>
