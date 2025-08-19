<#import "../layout.ftlh" as main>
<#import "/spring.ftl" as spring>
<#assign roleLabel = {'APPLICANT':'Соискатель','EMPLOYER':'Работодатель'}>

<@main.layout title="Регистрация">
    <div class="card" style="max-width:640px;margin:0 auto;">
        <h1>Регистрация</h1>
        <p class="muted">Заполните поля ниже. Поле с фото — необязательно (добавите позже в профиле).</p>

        <form action="/auth/register" method="post" class="form">
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

            <@spring.bind "dto.accountType"/>
            <label>Тип аккаунта
                <select name="${spring.status.expression}" required>
                    <option value="">Выберите…</option>
                    <#list (accountTypes![]) as t>
                        <option value="${t}"
                                <#if (spring.status.value!'')?string == t?string>selected</#if>>
                            ${roleLabel[t?string]}
                        </option>
                    </#list>
                </select>
            </label>
            <#if spring.status.errorMessages?has_content>
                <div class="error">
                    <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>
                </div>
            </#if>

            <@spring.bind "dto.name"/>
            <label>Имя
                <input name="${spring.status.expression}" value="${spring.status.value!}">
            </label>
            <#if spring.status.errorMessages?has_content>
                <div class="error"><#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list></div>
            </#if>

            <@spring.bind "dto.surname"/>
            <label>Фамилия
                <input name="${spring.status.expression}" value="${spring.status.value!}">
            </label>
            <#if spring.status.errorMessages?has_content>
                <div class="error"><#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list></div>
            </#if>

            <@spring.bind "dto.age"/>
            <label>Возраст
                <input type="number" name="${spring.status.expression}" value="${spring.status.value!}">
            </label>
            <#if spring.status.errorMessages?has_content>
                <div class="error"><#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list></div>
            </#if>

            <@spring.bind "dto.email"/>
            <label>Email
                <input type="email" name="${spring.status.expression}" value="${spring.status.value!}">
            </label>
            <#if spring.status.errorMessages?has_content>
                <div class="error"><#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list></div>
            </#if>

            <@spring.bind "dto.phoneNumber"/>
            <label>Телефон
                <input name="${spring.status.expression}" value="${spring.status.value!}">
            </label>
            <#if spring.status.errorMessages?has_content>
                <div class="error"><#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list></div>
            </#if>

            <@spring.bind "dto.password"/>
            <label>Пароль
                <input type="password" name="${spring.status.expression}">
            </label>
            <#if spring.status.errorMessages?has_content>
                <div class="error"><#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list></div>
            </#if>

            <#--
            <@spring.bind "dto.passwordConfirm"/>
            <label>Повторите пароль
              <input type="password" name="${spring.status.expression}">
            </label>
            <#if spring.status.errorMessages?has_content>
              <div class="error"><#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list></div>
            </#if>
            -->

            <button class="btn btn--primary" type="submit">Зарегистрироваться</button>

            <p class="muted" style="margin-top:8px">
                Уже есть аккаунт? <a href="/auth/login">Войти</a>
            </p>
        </form>
    </div>
</@main.layout>
