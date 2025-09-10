<#import "layout.ftlh" as l>
<#import "spring.ftl" as spring>

<@l.layout title="<@spring.message 'profile.title'/>">
    <div class="card shadow-lg border-0 rounded-4 bg-dark text-light p-4">
        <div class="row">
            <div class="col-md-4 d-flex justify-content-center align-items-start flex-column">
                <div class="profile-avatar text-center w-100 mb-3">
                    <#if user?? && user.avatar?has_content>
                        <img class="rounded-circle border border-light" src="/api/profile/avatar" alt="avatar" style="width: 200px; height: 200px;">
                    <#else>
                        <img class="rounded-circle border border-secondary" src="/static/images/default-avatar.png" alt="avatar" style="width: 200px; height: 200px;">
                    </#if>
                </div>
            </div>

            <div class="col-md-8">
                <h2 class="fw-bold">${(user.name)!'—'} ${(user.surname)!''}</h2>
                <p class="mb-1"><i class="bi bi-envelope"></i> <strong><@spring.message 'profile.email'/>:</strong> ${(user.email)!'—'}</p>
                <p class="mb-1"><i class="bi bi-telephone"></i> <strong><@spring.message 'profile.phone'/>:</strong> ${(user.phoneNumber)!'—'}</p>
                <p class="mb-3"><i class="bi bi-cake2"></i> <strong><@spring.message 'profile.age'/>:</strong> ${(user.age)!'—'}</p>

                <a href="/profile/edit" class="btn btn-outline-light mb-3">
                    <i class="bi bi-pencil"></i> <@spring.message 'profile.edit'/>
                </a>

                <#assign roles = (SPRING_SECURITY_CONTEXT.authentication.authorities)![]>
                <#assign isEmployer = false>
                <#assign isApplicant = false>
                <#list roles as a>
                    <#if a.authority?string == 'ROLE_EMPLOYER'>
                        <#assign isEmployer = true>
                        <#break>
                    </#if>
                </#list>
                <#if !isEmployer>
                    <#list roles as a>
                        <#if a.authority?string == 'ROLE_APPLICANT'>
                            <#assign isApplicant = true>
                            <#break>
                        </#if>
                    </#list>
                </#if>

                <div class="mt-2">
                    <#if isEmployer>
                        <a class="btn btn-primary me-2 mb-2" href="/vacancy/new">
                            <i class="bi bi-plus-circle"></i> <@spring.message 'profile.create_vacancy'/>
                        </a>
                        <a class="btn btn-secondary mb-2" href="/profile/vacancies">
                            <i class="bi bi-folder2-open"></i> <@spring.message 'profile.my_vacancies'/>
                        </a>
                    <#elseif isApplicant>
                        <a class="btn btn-primary me-2 mb-2" href="/resume/new">
                            <i class="bi bi-plus-circle"></i> <@spring.message 'profile.create_resume'/>
                        </a>
                        <a class="btn btn-secondary mb-2" href="/profile/resumes">
                            <i class="bi bi-folder2-open"></i> <@spring.message 'profile.my_resumes'/>
                        </a>
                    </#if>
                </div>
            </div>
        </div>
    </div>
</@l.layout>
