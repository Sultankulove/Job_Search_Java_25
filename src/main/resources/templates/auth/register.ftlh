<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<#assign roleLabel = {'APPLICANT':'Соискатель','EMPLOYER':'Работодатель'}>

<@main.layout title="Регистрация">
    <div class="card bg-dark text-light shadow-lg border-secondary rounded-4" style="max-width: 640px; margin: 0 auto;">
        <div class="card-body p-4">
            <h1 class="mb-3">Регистрация</h1>
            <p class="text-secondary">Заполните поля ниже. Поле с фото — необязательно (добавите позже в профиле).</p>

            <form action="/auth/register" method="post" class="form">
                <#if _csrf??>
                    <input
                            type="hidden"
                            name="${(_csrf.parameterName)!'csrf-param-name'}"
                            value="${(_csrf.token)!'csrf-token'}"/>

                </#if>
                <#if role??>
                    <input type="hidden" name="role" value="${role}">
                    <div class="mb-3 text-secondary">
                        Вы регистрируетесь как: <strong>${roleLabel[role?upper_case]}</strong>
                    </div>
                </#if>

                <#--                Имя -->
                <@spring.bind "dto.name"/>
                <div class="mb-3">
                    <label class="form-label">Имя</label>
                    <input class="form-control bg-dark text-light border-secondary" name="${spring.status.expression}"
                           value="${spring.status.value!}">
                    <#if spring.status.errorMessages?has_content>
                        <div class="form-text text-danger small mt-1">
                            <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                        </div>
                    </#if>
                </div>

                <#--                 Фамилия -->
                <@spring.bind "dto.surname"/>
                <div class="mb-3">
                    <label class="form-label">Фамилия</label>
                    <input class="form-control bg-dark text-light border-secondary" name="${spring.status.expression}"
                           value="${spring.status.value!}">
                    <#if spring.status.errorMessages?has_content>
                        <div class="form-text text-danger small mt-1">
                            <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                        </div>
                    </#if>
                </div>

                <#--                 Возраст -->
                <@spring.bind "dto.age"/>
                <div class="mb-3">
                    <label class="form-label">Возраст</label>
                    <input type="number" class="form-control bg-dark text-light border-secondary"
                           name="${spring.status.expression}" value="${spring.status.value!}">
                    <#if spring.status.errorMessages?has_content>
                        <div class="form-text text-danger small mt-1">
                            <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                        </div>
                    </#if>
                </div>

                <#--                 Email -->
                <@spring.bind "dto.email"/>
                <div class="mb-3">
                    <label class="form-label">Email</label>
                    <input type="email" class="form-control bg-dark text-light border-secondary"
                           name="${spring.status.expression}" value="${spring.status.value!}">
                    <#if spring.status.errorMessages?has_content>
                        <div class="form-text text-danger small mt-1">
                            <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                        </div>
                    </#if>
                </div>

                <#--                Телефон-->
                <@spring.bind "dto.phoneNumber"/>
                <div class="mb-3">
                    <label class="form-label">Телефон</label>
                    <input class="form-control bg-dark text-light border-secondary" name="${spring.status.expression}"
                           value="${spring.status.value!}">
                    <#if spring.status.errorMessages?has_content>
                        <div class="form-text text-danger small mt-1">
                            <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                        </div>
                    </#if>
                </div>

                <#--                Пароль-->
                <@spring.bind "dto.password"/>
                <div class="mb-4">
                    <label class="form-label">Пароль</label>
                    <input type="password" class="form-control bg-dark text-light border-secondary"
                           name="${spring.status.expression}">
                    <#if spring.status.errorMessages?has_content>
                        <div class="form-text text-danger small mt-1">
                            <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                        </div>
                    </#if>
                </div>

                <button class="btn btn-primary w-100" type="submit">
                    <i class="bi bi-person-plus-fill"></i> Зарегистрироваться
                </button>

                <p class="text-light text-center mt-3">
                    Уже есть аккаунт? <a href="/auth/login" class="link-light text-decoration-underline">Войти</a>
                </p>
            </form>
        </div>
    </div>
</@main.layout>
