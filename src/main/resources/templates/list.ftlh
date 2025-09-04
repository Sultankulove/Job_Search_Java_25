<#import "layout.ftlh" as l>
<@l.layout title="${title! 'Список'}">

    <head>
        <link rel="stylesheet" href="/static/styles/list.css">
        <title></title>
    </head>

    <div class="container">
        <div class="row mb-4">
            <div class="col">
                <h1 class="mb-3">${title! 'Список'}</h1>

                <#assign params = params!{'categoryId': ''}>
                <#assign selectedCategoryId = (params.categoryId!'')?string>

                <form method="get" action="/resumes" class="row g-2 align-items-center">
                    <div class="col-md-4">
                        <label for="categoryId" class="form-label">Категория</label>
                        <select class="form-select" id="categoryId" name="categoryId">
                            <option value="">Все категории</option>
                            <#list categories as c>
                                <option value="${c.id}" <#if selectedCategoryId == c.id?string>selected</#if>>
                                    ${c.name}
                                </option>
                            </#list>
                        </select>
                    </div>

                    <div class="col-md-2 align-self-end">
                        <button type="submit" class="btn btn-primary w-100">
                            <i class="bi bi-funnel-fill"></i> Фильтровать
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <table class="table table-dark table-striped table-hover align-middle">
                    <thead>
                    <tr>
                        <#list headers as h>
                            <th class="text-start">${h}</th>
                        </#list>
                    </tr>
                    </thead>

                    <#assign catNameById = {} />
                    <#list categories as c>
                        <#assign catNameById = catNameById + { (c.id?string): c.name } />
                    </#list>

                    <tbody>
                    <#list list.content as item>
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

                            <td>
                                <#if item.salary??>
                                    ${item.salary?string["#,##0"]} $
                                <#else>—</#if>
                            </td>

                            <td>
                                <#if item.updateTime?has_content>
                                    <#if item.updateTime?is_date || item.updateTime?is_datetime>
                                        ${item.updateTime?string("yyyy-MM-dd HH:mm")}
                                    <#elseif item.updateTime?is_string>
                                        ${item.updateTime?replace("T", " ")}
                                    <#else>
                                        ${item.updateTime}
                                    </#if>
                                <#else>-</#if>
                            </td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>

        <#if list.totalPages gt 1>
            <div class="row mt-4">
                <div class="col">
                    <nav aria-label="Пагинация">
                        <ul class="pagination justify-content-center">
                            <#if list.hasPrevious()>
                                <li class="page-item">
                                    <a class="btn btn-outline-primary" href="?categoryId=${selectedCategoryId}&page=${list.number - 1}">
                                        ← Назад
                                    </a>
                                </li>
                            </#if>

                            <li class="page-item active" aria-current="page">
                                <span class="btn btn-primary disabled">${list.number + 1}</span>
                            </li>

                            <#if list.hasNext()>
                                <li class="page-item">
                                    <a class="btn btn-outline-primary" href="?categoryId=${selectedCategoryId}&page=${list.number + 1}">
                                        Далее →
                                    </a>
                                </li>
                            </#if>
                        </ul>
                    </nav>
                </div>
            </div>
        </#if>
    </div>
</@l.layout>
