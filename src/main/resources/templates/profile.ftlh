<#import "layout.ftlh" as l>
<#import "spring.ftl" as spring>

<@l.layout title="Профиль">
    <div class="card profile">
        <div class="profile__left">
        </div>
        <div class="profile__right">
            <div class="">
                <img class="avatar" src="${(user.avatar)!'/images/default-avatar.png'}" alt="avatar">
            </div>
            <div class="">
                <h1>${(user.name)!'—'} ${(user.surname)!''}</h1>
                <p>Email: ${(user.email)!'—'}</p>
                <p>Телефон: ${(user.phoneNumber)!'—'}</p>
                <p>Возраст: ${(user.age)!'—'}</p>
            </div>

            <a href="/profile/edit" class="btn btn--primary">Редактировать</a>

            <#assign roles = (SPRING_SECURITY_CONTEXT.authentication.authorities)![]>
            <#assign isEmployer = false>
            <#assign isApplicant = false>
            <#list roles as a>
                <#if a.authority?string == 'ROLE_EMPLOYER'><#assign isEmployer = true><#break></#if>
            </#list>
            <#if !isEmployer>
                <#list roles as a>
                    <#if a.authority?string == 'ROLE_APPLICANT'><#assign isApplicant = true><#break></#if>
                </#list>
            </#if>

            <#if isEmployer>
                <a class="btn" href="/vacancy/new">Создать вакансию</a>
                <a class="btn" href="/profile/vacancies">Мои вакансии</a>
            <#elseif isApplicant>
                <a class="btn" href="/resume/new">Создать резюме</a>
                <a class="btn" href="/resumes?mine=1">Мои резюме</a>
            </#if>
        </div>
    </div>
</@l.layout>
