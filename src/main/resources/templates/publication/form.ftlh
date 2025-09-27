<#import "../layout.ftlh" as l>
<#import "/spring.ftl" as spring>

<@l.layout title="<@spring.message 'publication.form.title'/>">
    <div class="container py-4">
        <h2 class="mb-4">
            <#if detail??>
                <@spring.message 'publication.form.edit'/>
            <#else>
                <@spring.message 'publication.form.create'/>
            </#if>
        </h2>

        <form method="post" action="/publications" enctype="multipart/form-data">
            <#if _csrf??>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>

            <@spring.bind "dto.categoryId"/>
            <div class="mb-3">
                <label class="form-label"><@spring.message 'publication.form.category'/></label>
                <select class="form-select" name="${spring.status.expression}" required>
                    <option value=""><@spring.message 'list.filter.category.all'/></option>
                    <#list categories as c>
                        <option value="${c.id}"
                                <#if spring.status.value?has_content && spring.status.value?string == c.id?string>selected</#if>>${c.name}</option>
                    </#list>
                </select>
                <#if spring.status.errorMessages?has_content>
                    <div class="text-danger small">
                        <#list spring.status.errorMessages as err>${err}<#if err_has_next>, </#if></#list>
                    </div>
                </#if>
            </div>

            <@spring.bind "dto.title"/>
            <div class="mb-3">
                <label class="form-label"><@spring.message 'publication.form.title.label'/></label>
                <input class="form-control" name="${spring.status.expression}" value="${spring.status.value!''}" maxlength="255" required>
                <#if spring.status.errorMessages?has_content>
                    <div class="text-danger small">
                        <#list spring.status.errorMessages as err>${err}<#if err_has_next>, </#if></#list>
                    </div>
                </#if>
            </div>

            <@spring.bind "dto.content"/>
            <div class="mb-3">
                <label class="form-label"><@spring.message 'publication.form.content'/></label>
                <textarea class="form-control" name="${spring.status.expression}" rows="8" required>${spring.status.value!''}</textarea>
                <#if spring.status.errorMessages?has_content>
                    <div class="text-danger small">
                        <#list spring.status.errorMessages as err>${err}<#if err_has_next>, </#if></#list>
                    </div>
                </#if>
            </div>

            <div class="mb-3">
                <label class="form-label"><@spring.message 'publication.form.cover'/></label>
                <input class="form-control" type="file" name="cover" accept="image/*">
                <#if detail?? && detail.cover??>
                    <div class="form-check mt-2">
                        <input class="form-check-input" type="checkbox" id="removeCover" name="removeCover" value="true">
                        <label class="form-check-label" for="removeCover"><@spring.message 'publication.form.cover.remove'/></label>
                    </div>
                    <div class="mt-2">
                        <img src="/publications/${detail.id}/cover" alt="cover" class="img-fluid rounded" style="max-height: 180px;">
                    </div>
                </#if>
            </div>

            <button class="btn btn-primary" type="submit">
                <i class="bi bi-save"></i>
                <#if detail??>
                    <@spring.message 'publication.form.save'/>
                </#if>
                <#if !detail??>
                    <@spring.message 'publication.form.publish'/>
                </#if>
            </button>
        </form>
    </div>
</@l.layout>
