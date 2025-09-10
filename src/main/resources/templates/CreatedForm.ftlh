<#import "layout.ftlh" as l>
<#import "spring.ftl" as spring>

<@l.layout title="<@spring.message 'vacancy.create.title'/>">

    <form action="/vacancy/new" method="post" class="form">
        <#if _csrf??>
            <input
                    type="hidden"
                    name="${(_csrf.parameterName)!'csrf-param-name'}"
                    value="${(_csrf.token)!'csrf-token'}"/>
        </#if>

        <@spring.bind "dto.name"/>
        <div class="mb-3">
            <label class="form-label"><@spring.message "vacancy.create.label.name"/></label>
            <input class="form-control bg-dark text-light border-secondary"
                   name="${spring.status.expression}"
                   value="${spring.status.value!}">
            <#if spring.status.errorMessages?has_content>
                <div class="form-text text-danger small mt-1">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>
        </div>

        <@spring.bind "dto.description"/>
        <div class="mb-3">
            <label class="form-label"><@spring.message "vacancy.create.label.description"/></label>
            <textarea class="form-control bg-dark text-light border-secondary"
                      name="${spring.status.expression}">${spring.status.value!}</textarea>
            <#if spring.status.errorMessages?has_content>
                <div class="form-text text-danger small mt-1">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>
        </div>

        <@spring.bind "dto.categoryId"/>
        <div class="mb-3">
            <label class="form-label"><@spring.message "vacancy.create.label.category"/></label>
            <select class="form-select bg-dark text-light border-secondary" name="${spring.status.expression}">
                <option value=""> <@spring.message "vacancy.create.option.select_category"/> </option>
                <#list categories as category>
                    <option value="${category.id}">
                        ${category.name}
                    </option>
                </#list>
            </select>

            <#if spring.status.errorMessages?has_content>
                <div class="form-text text-danger small mt-1">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>
        </div>

        <@spring.bind "dto.salary"/>
        <div class="mb-3">
            <label class="form-label"><@spring.message "vacancy.create.label.salary"/></label>
            <input type="number"
                   class="form-control bg-dark text-light border-secondary"
                   name="${spring.status.expression}"
                   value="${spring.status.value!}"
                   min="0">
            <#if spring.status.errorMessages?has_content>
                <div class="form-text text-danger small mt-1">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>
        </div>

        <@spring.bind "dto.expFrom"/>
        <div class="mb-3">
            <label class="form-label"><@spring.message "vacancy.create.label.exp_from"/></label>
            <input type="number"
                   class="form-control bg-dark text-light border-secondary"
                   name="${spring.status.expression}"
                   value="${spring.status.value!}"
                   min="0">
            <#if spring.status.errorMessages?has_content>
                <div class="form-text text-danger small mt-1">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>
        </div>

        <@spring.bind "dto.expTo"/>
        <div class="mb-3">
            <label class="form-label"><@spring.message "vacancy.create.label.exp_to"/></label>
            <input type="number"
                   class="form-control bg-dark text-light border-secondary"
                   name="${spring.status.expression}"
                   value="${spring.status.value!}"
                   min="0">
            <#if spring.status.errorMessages?has_content>
                <div class="form-text text-danger small mt-1">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>
        </div>

        <@spring.bind "dto.active"/>
        <div class="form-check mb-4">
            <input type="checkbox"
                   class="form-check-input"
                   id="active"
                   name="${spring.status.expression}"
                   value="true"
                   <#if spring.status.value?? && (spring.status.value)?string == "true">checked</#if>>
            <label class="form-check-label" for="active">
                <@spring.message "vacancy.create.label.active"/>
            </label>

            <#if spring.status.errorMessages?has_content>
                <div class="form-text text-danger small mt-1">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>
        </div>

        <button class="btn btn-primary w-100" type="submit">
            <i class="bi bi-person-plus-fill"></i> <@spring.message "vacancy.create.button.submit"/>
        </button>
    </form>

</@l.layout>
