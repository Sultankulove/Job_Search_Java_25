<#import "layout.ftlh" as l>
<#import "spring.ftl" as spring>

<@l.layout title="<@spring.message 'profile.edit.title'/>">
    <div class="container mt-4">
        <div class="card bg-dark text-light shadow-lg">
            <div class="card-header border-0 pb-0">
                <h2 class="text-center"><@spring.message "profile.edit.header"/></h2>
            </div>

            <div class="card-body">
                <form action="/profile/edit" method="post" class="row g-3">
                    <#if _csrf??>
                        <input
                                type="hidden"
                                name="${(_csrf.parameterName)!'csrf-param-name'}"
                                value="${(_csrf.token)!'csrf-token'}"/>
                    </#if>
                    <div class="col-md-6">
                        <@spring.bind "dto.name"/>
                        <label class="form-label"><@spring.message "profile.edit.label.name"/></label>
                        <input name="${spring.status.expression}" value="${spring.status.value!}" class="form-control <#if spring.status.errorMessages?has_content>is-invalid</#if>">
                        <#if spring.status.errorMessages?has_content>
                            <div class="invalid-feedback"><#list spring.status.errorMessages as m>${m}</#list></div>
                        </#if>
                    </div>

                    <div class="col-md-6">
                        <@spring.bind "dto.surname"/>
                        <label class="form-label"><@spring.message "profile.edit.label.surname"/></label>
                        <input name="${spring.status.expression}" value="${spring.status.value!}" class="form-control">
                    </div>

                    <div class="col-md-4">
                        <@spring.bind "dto.age"/>
                        <label class="form-label"><@spring.message "profile.edit.label.age"/></label>
                        <input type="number" name="${spring.status.expression}" value="${spring.status.value!}" class="form-control">
                    </div>

                    <div class="col-md-4">
                        <@spring.bind "dto.phoneNumber"/>
                        <label class="form-label"><@spring.message "profile.edit.label.phone"/></label>
                        <input name="${spring.status.expression}" value="${spring.status.value!}" class="form-control">
                    </div>

                    <div class="col-md-4">
                        <@spring.bind "dto.email"/>
                        <label class="form-label"><@spring.message "profile.edit.label.email"/></label>
                        <input type="email" name="${spring.status.expression}" value="${spring.status.value!}" class="form-control" readonly>
                    </div>

                    <div class="col-12 text-end mt-3">
                        <button class="btn btn-outline-light" type="submit">
                            <i class="bi bi-save"></i> <@spring.message "profile.edit.button.save"/>
                        </button>
                    </div>
                </form>

                <hr class="bg-secondary my-4">

                <h4 class="mb-3"><@spring.message "profile.avatar.upload_header"/></h4>
                <form action="/profile/avatar" method="post" enctype="multipart/form-data" class="row g-3 align-items-center">
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

                    <div class="col-auto">
                        <img class="rounded-circle shadow" src="/api/profile/avatar" alt="<@spring.message 'profile.avatar.alt'/>" width="100" height="100">
                    </div>

                    <div class="col">
                        <input type="file" name="avatar" accept="image/*" class="form-control">
                    </div>

                    <div class="col-auto">
                        <button class="btn btn-secondary" type="submit">
                            <i class="bi bi-upload"></i> <@spring.message "profile.avatar.upload_button"/>
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</@l.layout>


