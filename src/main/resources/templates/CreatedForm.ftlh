<#import "layout.ftlh" as l>
<#import "spring.ftl" as spring>

<@l.layout title="Создать">

    <form action="/vacancy/new" method="post" class="form">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <@spring.bind "dto.name"/>
        <div class="mb-3">
            <label class="form-label">Имя</label>
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
            <label class="form-label">Описание</label>
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
            <label class="form-label">Категория</label>
            <select class="form-select bg-dark text-light border-secondary" name="${spring.status.expression}">
                <option value="">Выберите категорию</option>
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
            <label class="form-label">Зарплата</label>
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
            <label class="form-label">Опыт от</label>
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
            <label class="form-label">Опыт до</label>
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
                Активна?
            </label>

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
