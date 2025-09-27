<#import "layout.ftlh" as l>
<#import "/spring.ftl" as spring>

<@l.layout title="<@spring.message 'chats.title'/>">
    <div class="container py-3">
        <h1 class="mb-4"><@spring.message 'chats.heading'/></h1>

        <#if role?? && role == 'ROLE_APPLICANT'>
            <h2 class="h4"><@spring.message 'chats.applicant.title'/></h2>
            <#if applicantResponses?? && applicantResponses?size gt 0>
                <div class="list-group mb-4">
                    <#list applicantResponses as resp>
                        <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                           href="/chat/${resp.id}">
                            <div>
                                <div class="fw-semibold">${resp.vacancyName!'-'}</div>
                                <div class="small text-secondary">
                                    <@spring.message 'chats.resume'/>: ${resp.resumeName!'-'}
                                </div>
                            </div>
                            <span class="small text-secondary">
                                ${resp.createdDate}
                            </span>
                        </a>
                    </#list>
                </div>
            <#else>
                <div class="alert alert-secondary">
                    <@spring.message 'chats.applicant.empty'/>
                </div>
            </#if>
        </#if>

        <#if role?? && role == 'ROLE_EMPLOYER'>
            <h2 class="h4"><@spring.message 'chats.employer.title'/></h2>
            <#if employerResponses?? && employerResponses?size gt 0>
                <div class="list-group">
                    <#list employerResponses as resp>
                        <a class="list-group-item list-group-item-action d-flex justify-content-between align-items-center"
                           href="/chat/${resp.id}">
                            <div>
                                <div class="fw-semibold">${resp.resumeName!'-'}</div>
                                <div class="small text-secondary">
                                    <@spring.message 'chats.vacancy'/>: ${resp.vacancyName!'-'}
                                </div>
                            </div>
                            <span class="small text-secondary">
                                ${resp.createdDate}
                            </span>
                        </a>
                    </#list>
                </div>
            <#else>
                <div class="alert alert-secondary">
                    <@spring.message 'chats.employer.empty'/>
                </div>
            </#if>
        </#if>
    </div>
</@l.layout>
