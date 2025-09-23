<#import "layout.ftlh" as l>
<#import "/spring.ftl" as spring>

<@l.layout title="<@spring.message 'form.title'/>">
    <form action="${formAction}" method="post" class="form">

        <#if _csrf??>
            <input
                    type="hidden"
                    name="${(_csrf.parameterName)!'csrf-param-name'}"
                    value="${(_csrf.token)!'csrf-token'}"/>
        </#if>

        <@spring.bind "${dtoName}.name"/>
        <div class="mb-3">
            <label class="form-label"><@spring.message "form.label.name"/></label>
            <input class="form-control" name="${spring.status.expression}" value="${spring.status.value!}">
        </div>

        <@spring.bind "${dtoName}.description"/>
        <div class="mb-3">
            <label class="form-label"><@spring.message "form.label.description"/></label>
            <textarea class="form-control" name="${spring.status.expression}" rows="5">${spring.status.value!}</textarea>
        </div>

        <@spring.bind "${dtoName}.categoryId"/>
        <div class="mb-3">
            <label class="form-label"><@spring.message "form.label.category"/></label>
            <select class="form-select" name="${spring.status.expression}">
                <option value=""><@spring.message "form.option.select_category"/></option>
                <#list categories as category>
                    <option value="${category.id}" <#if spring.status.value?? && spring.status.value?string == category.id?string>selected</#if>>
                        ${category.name}
                    </option>
                </#list>
            </select>
        </div>

        <@spring.bind "${dtoName}.salary"/>
        <div class="mb-3">
            <label class="form-label"><@spring.message "form.label.salary"/></label>
            <input type="number" class="form-control" name="${spring.status.expression}" value="${spring.status.value!}" min="0">
        </div>

        <div class="row">
            <div class="col-md-6">
                <@spring.bind "${dtoName}.expFrom"/>
                <label class="form-label"><@spring.message "form.label.exp_from"/></label>
                <input type="number" class="form-control" name="${spring.status.expression}" value="${spring.status.value!}" min="0">
            </div>
            <div class="col-md-6">
                <@spring.bind "${dtoName}.expTo"/>
                <label class="form-label"><@spring.message "form.label.exp_to"/></label>
                <input type="number" class="form-control" name="${spring.status.expression}" value="${spring.status.value!}" min="0">
            </div>
        </div>

        <@spring.bind "${dtoName}.active"/>
        <div class="form-check my-4">
            <input type="hidden" name="_${spring.status.expression}" value="on">
            <input type="checkbox"
                   class="form-check-input"
                   id="active"
                   name="${spring.status.expression}"
                   value="true"
                   <#if spring.status.value?string == "true">checked</#if>>
            <label class="form-check-label" for="active">
                <@spring.messageText code="form.label.active" text=""/>
            </label>
        </div>

        <button class="btn btn-primary w-100" type="submit">
            <i class="bi bi-check-circle"></i> <@spring.message "form.button.save"/>
        </button>
    </form>
</@l.layout>
