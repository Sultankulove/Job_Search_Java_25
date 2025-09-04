<#import "layout.ftlh" as l>
<#import "spring.ftl" as spring>

<@l.layout title="Создать">

    <form action="/vacancy/new" method="post" class="form">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <@spring.bind "dto.name"/>
        <div class="mb-3">
            <label class="form-label">Имя</label>
            <input class="form-control bg-dark text-light border-secondary" name="${spring.status.expression}" value="${spring.status.value!}">
            <#if spring.status.errorMessages?has_content>
                <div class="form-text text-danger small mt-1">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>
        </div>

        <@spring.bind "dto.description"/>
        <div class="mb-3">
            <label class="form-label">Описание</label>
            <input class="form-control bg-dark text-light border-secondary" name="${spring.status.expression}" value="${spring.status.value!}">
            <#if spring.status.errorMessages?has_content>
                <div class="form-text text-danger small mt-1">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>
        </div>

        <@spring.bind "dto.categoryId"/>
        <div class="mb-3">
            <label class="form-label">Категория</label>
            <input class="form-control bg-dark text-light border-secondary" name="${spring.status.expression}" value="${spring.status.value!}">
            <#if spring.status.errorMessages?has_content>
                <div class="form-text text-danger small mt-1">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>
        </div>

        <@spring.bind "dto.salary"/>
        <div class="mb-3">
            <label class="form-label">Зарплата</label>
            <input type="email" class="form-control bg-dark text-light border-secondary" name="${spring.status.expression}" value="${spring.status.value!}">
            <#if spring.status.errorMessages?has_content>
                <div class="form-text text-danger small mt-1">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>
        </div>

        <@spring.bind "dto.expFrom"/>
        <div class="mb-3">
            <label class="form-label">expFrom</label>
            <input class="form-control bg-dark text-light border-secondary" name="${spring.status.expression}" value="${spring.status.value!}">
            <#if spring.status.errorMessages?has_content>
                <div class="form-text text-danger small mt-1">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>
        </div>

        <@spring.bind "dto.expTo"/>
        <div class="mb-4">
            <label class="form-label">expTo</label>
            <input type="password" class="form-control bg-dark text-light border-secondary" name="${spring.status.expression}">
            <#if spring.status.errorMessages?has_content>
                <div class="form-text text-danger small mt-1">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>
        </div>

        <@spring.bind "dto.isActive"/>
        <div class="mb-4">
            <label class="form-label">isActive</label>
            <input type="password" class="form-control bg-dark text-light border-secondary" name="${spring.status.expression}">
            <#if spring.status.errorMessages?has_content>
                <div class="form-text text-danger small mt-1">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>
        </div>

        <button class="btn btn-primary w-100" type="submit">
            <i class="bi bi-person-plus-fill"></i> Создать
        </button>
    </form>


</@l.layout>
