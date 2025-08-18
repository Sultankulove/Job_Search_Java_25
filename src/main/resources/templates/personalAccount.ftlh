<#import "layout.ftlh" as main>

<@main.layout ; spring>



    <#if SPRING_SECURITY_CONTEXT?? && SPRING_SECURITY_CONTEXT.authentication??>
        <#assign user = SPRING_SECURITY_CONTEXT.authentication.principal>
        <#if user?? && user.authorities?has_content>
            <#list user.authorities as a>
                <#assign role = a.authority!a?string>
                <#if role == "ROLE_EMPLOYER">
                    <h1>Личный кабинет Работодателя</h1>
                <#elseif role == "ROLE_APPLICANT">
                    <h1>Личный кабинет Соискателя</h1>
                </#if>
            </#list>
        </#if>
    </#if>


    <div class="container text-center">
        <div class="row">
            <div class="col">
                <img src="/api/profile/avatar" class="img-thumbnail" width="300" height="200" alt="avatar">
            </div>
            <div class="col">
                <fieldset>
                    <legend>${myUser.name} ${myUser.surname}</legend>
                    <legend>Возраст: ${myUser.age}</legend>
                </fieldset>

                <a class="btn btn-outline-secondary" href="/profile">Редактировать</a>
            </div>
            <div class="col">
                <a class="btn btn-outline-secondary" href="/resume/new">Создать резюме</a>
            </div>
            <div class="col">
<#--                Надо доработать-->
                <a class="btn btn-outline-secondary" href="#">Отклики</a>
            </div>
        </div>


        <h2>Резюме</h2>

<#--        <#list resumes as resume>-->
<#--            <h3>${resume.name}</h3>-->

<#--            <p>Название резюме</p>-->
<#--            <p>${resume.updateTime}</p>-->
<#--        </#list>-->

        <table class="table table-success table-striped">
            <thead>
            <tr>
                <th>Название резюме</th>
                <th>Дата добавления</th>
<#--                <th></th>-->
            </tr>
            </thead>
            <tbody>
            <#list resumes as resume>
                <tr>
                    <td>${resume.name}</td>
                    <td>${resume.updateTime}</td>

                </tr>
            </#list>
            </tbody>
        </table>

    </div>




</@main.layout>
