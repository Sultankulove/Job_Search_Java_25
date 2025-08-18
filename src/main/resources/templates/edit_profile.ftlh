<#import "layout.ftlh" as main>

<@main.layout ; spring>

    <div class="col">
        <img src="/api/profile/avatar" class="img-thumbnail" width="300" height="200" alt="avatar">
    </div>

<#--    <form action="/profile/avatar" method="post" enctype="multipart/form-data">-->
<#--                <input type="hidden">-->
<#--        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>-->
<#---->
<#--        <label for="avatar">Фото профиля (необязательно)</label>-->
<#--        <input id="avatar" name="avatar" type="file" accept="image/*"/>-->
<#---->
<#--        <@spring.bind "user.avatar"/>-->
<#--        <#if spring.status.errorMessages?has_content>-->
<#--            <div class="error">-->
<#--                <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>-->
<#--            </div>-->
<#--        </#if>-->
<#--        <br>-->
<#--        <button type="submit">Сохранить</button>-->
<#--    </form>-->


    <#assign br = .data_model["org.springframework.validation.BindingResult.dto"]! />

    <#if br?? && br.globalErrors?has_content>
        <div class="alert">
            <#list br.globalErrors as ge>${ge.defaultMessage}<#if ge_has_next><br/></#if></#list>
        </div>
    </#if>

<#--        <form action="/profile/avatar" method="post" enctype="multipart/form-data">-->

<#--            <label for="avatar">Фото профиля</label>&ndash;&gt;-->
<#--            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">-->
<#--            <input id="avatar" name="avatar" type="file" accept="image/*"/>-->

<#--                    <@spring.bind "dto.avatar"/>-->
<#--                    <#if spring.status.errorMessages?has_content>-->
<#--                        <div class="error">-->
<#--                            <#list spring.status.errorMessages as m>${m}<#if m_has_next>, </#if></#list>-->
<#--                        </div>-->
<#--                    </#if>-->

<#--                    <br>-->
<#--                    <button type="submit">Сохранить</button>-->
<#--        </form>-->

    <form action="/profile/edit" method="post" enctype="multipart/form-data" novalidate>
        <#if _csrf??>
            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        </#if>


        <div class="row">
            <label id="nameLabel" for="name">Имя</label>
            <input id="name" name="name" type="text" value="${(dto.name)!}" required>
            <#if br?? && br.hasFieldErrors("name")>
                <div class="error"><#list br.getFieldErrors("name") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list></div>
            </#if>
        </div>

        <div class="row">
            <label id="nameLabel" for="surname">Фамилия</label>
            <input id="surname" name="surname" type="text" value="${(dto.surname)!}" required>
            <#if br?? && br.hasFieldErrors("suranme")>
                <div class="error"><#list br.getFieldErrors("surname") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list></div>
            </#if>
        </div>

        <div class="row">
            <label for="age">Возраст</label>
            <@spring.bind "dto.age"/>
            <input id="age" name="age" type="number" min="18" max="100" step="1"
                   value="${(spring.status.value)!''}" required>
            <#if spring.status.errorMessages?has_content>
                <div class="error">${spring.status.errorMessages?join(", ")}</div>
            </#if>
        </div>

<#--        <div class="row">-->
<#--            <label for="email">Email</label>-->
<#--            <input id="email" name="email" type="email" value="${(dto.email)!}" required>-->
<#--            <#if br?? && br.hasFieldErrors("email")>-->
<#--                <div class="error"><#list br.getFieldErrors("email") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list></div>-->
<#--            </#if>-->
<#--        </div>-->


        <div class="row">
            <label for="phoneNumber">Телефон</label>
            <input id="phoneNumber" name="phoneNumber" type="tel" value="${(dto.phoneNumber)!}" required>
            <#if br?? && br.hasFieldErrors("phoneNumber")>
                <div class="error"><#list br.getFieldErrors("phoneNumber") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list></div>
            </#if>
        </div>

        <div class="row">
            <label for="password">Пароль</label>
            <input id="password" name="password" type="password" required>
            <#if br?? && br.hasFieldErrors("password")>
                <div class="error"><#list br.getFieldErrors("password") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list></div>
            </#if>
        </div>


        <div class="actions">
            <button type="submit" class="btn btn-primary">Сохранить</button>
        </div>
    </form>



</@main.layout>
