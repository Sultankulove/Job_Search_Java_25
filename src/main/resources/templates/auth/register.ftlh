<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>

<@main.layout title="<@spring.message 'register.title'/>">
    <div class="card bg-dark text-light shadow-lg border-secondary rounded-4" style="max-width: 640px; margin: 0 auto;">
        <div class="card-body p-4">
            <h1 class="mb-3"><@spring.message "register.heading"/></h1>
            <p class="text-secondary"><@spring.message "register.subtitle"/></p>

            <form action="/auth/register" method="post" enctype="multipart/form-data" class="form">
                <#if _csrf??>
                    <input
                            type="hidden"
                            name="${(_csrf.parameterName)!'csrf-param-name'}"
                            value="${(_csrf.token)!'csrf-token'}"/>
                </#if>
                <#assign currentRole = (role!'applicant')?lower_case>
                <div class="mb-3">
                    <label class="form-label d-block"><@spring.message "register.label.role"/></label>
                    <div class="btn-group" role="group" aria-label="<@spring.message 'register.label.role.aria'/>">
                        <input class="btn-check" type="radio" name="role" id="roleApplicant" value="applicant" <#if currentRole == 'applicant'>checked</#if>>
                        <label class="btn btn-outline-light" for="roleApplicant">
                            <i class="bi bi-person-vcard-fill"></i>
                            <span class="ms-1"><@spring.message "register.role.applicant"/></span>
                        </label>

                        <input class="btn-check" type="radio" name="role" id="roleEmployer" value="employer" <#if currentRole == 'employer'>checked</#if>>
                        <label class="btn btn-outline-light" for="roleEmployer">
                            <i class="bi bi-buildings"></i>
                            <span class="ms-1"><@spring.message "register.role.employer"/></span>
                        </label>
                    </div>
                </div>

                <@spring.bind "dto.name"/>
                <div class="mb-3">
                    <label class="form-label" id="nameLabel"><@spring.message "register.label.name.person"/></label>
                    <input class="form-control bg-dark text-light border-secondary" name="${spring.status.expression}"
                           value="${spring.status.value!}">
                    <#if spring.status.errorMessages?has_content>
                        <div class="form-text text-danger small mt-1">
                            <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                        </div>
                    </#if>
                </div>

                <@spring.bind "dto.surname"/>
                <div class="mb-3">
                    <label class="form-label"><@spring.message "register.label.surname"/></label>
                    <input class="form-control bg-dark text-light border-secondary" name="${spring.status.expression}"
                           value="${spring.status.value!}">
                    <#if spring.status.errorMessages?has_content>
                        <div class="form-text text-danger small mt-1">
                            <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                        </div>
                    </#if>
                </div>

                <@spring.bind "dto.age"/>
                <div class="mb-3">
                    <label class="form-label"><@spring.message "register.label.age"/></label>
                    <input type="number" class="form-control bg-dark text-light border-secondary"
                           name="${spring.status.expression}" value="${spring.status.value!}">
                    <#if spring.status.errorMessages?has_content>
                        <div class="form-text text-danger small mt-1">
                            <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                        </div>
                    </#if>
                </div>

                <@spring.bind "dto.email"/>
                <div class="mb-3">
                    <label class="form-label"><@spring.message "register.label.email"/></label>
                    <input type="email" class="form-control bg-dark text-light border-secondary"
                           name="${spring.status.expression}" value="${spring.status.value!}">
                    <#if spring.status.errorMessages?has_content>
                        <div class="form-text text-danger small mt-1">
                            <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                        </div>
                    </#if>
                </div>

                <@spring.bind "dto.phoneNumber"/>
                <div class="mb-3">
                    <label class="form-label"><@spring.message "register.label.phone"/></label>
                    <input class="form-control bg-dark text-light border-secondary" name="${spring.status.expression}"
                           value="${spring.status.value!}">
                    <#if spring.status.errorMessages?has_content>
                        <div class="form-text text-danger small mt-1">
                            <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                        </div>
                    </#if>
                </div>

                <div class="mb-3">
                    <label class="form-label"><@spring.message "register.label.avatar"/></label>
                    <input type="file" class="form-control bg-dark text-light border-secondary" name="avatarFile" accept="image/*">
                    <div class="form-text text-secondary small"><@spring.message "register.label.avatar.hint"/></div>
                </div>

                <@spring.bind "dto.password"/>
                <div class="mb-4">
                    <label class="form-label"><@spring.message "register.label.password"/></label>
                    <input type="password" class="form-control bg-dark text-light border-secondary"
                           name="${spring.status.expression}">
                    <#if spring.status.errorMessages?has_content>
                        <div class="form-text text-danger small mt-1">
                            <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                        </div>
                    </#if>
                </div>

                <button class="btn btn-primary w-100" type="submit">
                    <i class="bi bi-person-plus-fill"></i> <@spring.message "register.button.submit"/>
                </button>

                <p class="text-light text-center mt-3">
                    <@spring.message "register.haveaccount"/> <a href="/auth/login" class="link-light text-decoration-underline"><@spring.message "register.login.link"/></a>
                </p>
            </form>
        </div>
    </div>
    <#assign _nameApplicant = springMacroRequestContext.getMessage('register.label.name.person')>
    <#assign _nameEmployer  = springMacroRequestContext.getMessage('register.label.name.company')>
    <script>
        document.addEventListener('DOMContentLoaded', function () {
            const radios = Array.from(document.querySelectorAll('input[name="role"]'));
            if (radios.length === 0) return;
            const labelEl = document.getElementById('nameLabel');
            if (!labelEl) return;
            const labelByRole = {
                applicant: '${_nameApplicant?js_string}',
                employer: '${_nameEmployer?js_string}'
            };
            function syncLabel() {
                const checked = radios.find(r => r.checked);
                const role = checked ? checked.value : 'applicant';
                labelEl.textContent = labelByRole[role] || labelByRole.applicant;
            }
            radios.forEach(r => r.addEventListener('change', syncLabel));
            syncLabel();
        });
    </script>
</@main.layout>
