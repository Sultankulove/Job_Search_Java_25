
<#import "layout.ftlh" as l>
<@l.layout title="${title! 'Список'}">
    <div class="card">
        <div class="card__header">
            <h1>${title! 'Список'}</h1>
            <div class="filters">
<#--                <form method="get" action="/vacancies">-->
<#--                    <select name="categoryId">-->
<#--                        <option value="">Все категории</option>-->
<#--                        <#list categories as c>-->
<#--                            <option value="${c.id}"-->
<#--                                    <#if params.categoryId?? && (params.categoryId?string == c.id?string)>selected</#if>>-->
<#--                                ${c.name}-->
<#--                            </option>-->
<#--                        </#list>-->
<#--                    </select>-->
<#--                    <button type="submit">Фильтровать</button>-->
<#--                </form>-->

                <#assign params = params!{'categoryId': ''}>
                <#assign selectedCategoryId = (params.categoryId!'')?string>


                <form method="get" action="/vacancies">
                    <#assign selCatId = (params?has_content && params.categoryId?has_content)?then(params.categoryId?string, "")>
                    <select name="categoryId">
                        <option value="">Все категории</option>
                        <#list categories as c>
                            <option value="${c.id}" <#if selCatId == c.id?string>selected</#if>>${c.name}</option>
                        </#list>
                    </select>


                    <button type="submit">Фильтровать</button>
                </form>







            </div>
        </div>

        <table class="table">
            <thead>
            <tr>
                <#list headers as h><th>${h}</th></#list>
            </tr>
            </thead>

            <#assign catNameById = {} />
            <#list categories as c>
                <#assign catNameById = catNameById + { (c.id?string): c.name } />
            </#list>

<#--            <td>${catNameById[(item.categoryId??)?then(item.categoryId?string,'')]!'-'}</td>-->


            <tbody>
            <#list list as item>
                <tr>
                    <td>${item.name!'-'}</td>

                    <td>
                        <#assign catName = '—'>
                        <#if item.categoryId??>
                            <#list categories as c>
                                <#if c.id?string == item.categoryId?string>
                                    <#assign catName = c.name>
                                    <#break>
                                </#if>
                            </#list>
                        </#if>
                        ${catName}
                    </td>


                    <td><#if item.salary??>${item.salary?string["#,##0"]}<#else>—</#if></td>

                    <td>
                        <#if item.updateTime?has_content>
                            <#if item.updateTime?is_date || item.updateTime?is_datetime>
                                ${item.updateTime?string("yyyy-MM-dd HH:mm")}
                            <#elseif item.updateTime?is_string>
                                ${item.updateTime?replace("T"," ")}
                            <#else>
                                ${item.updateTime}
                            </#if>
                        <#else>-</#if>
                    </td>
                </tr>
            </#list>
            </tbody>

            </tbody>

        </table>
    </div>
</@l.layout>
