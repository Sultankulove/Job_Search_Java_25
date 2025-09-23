<#import "layout.ftlh" as l>
<#import "/spring.ftl" as spring>

<@l.layout title="${title! 'Список'}">

    <head>
        <link rel="stylesheet" href="/static/styles/list.css">
        <style>
            .clickable-row {
                cursor: pointer;
            }
            .text-bright {
                color: #fff !important;
                font-weight: 600;
            }


        </style>
        <title></title>
    </head>

    <div class="container">
        <div class="row mb-4">
            <div class="col">
                <h1 class="mb-3">${title! 'Список'}</h1>

                <#assign filterAction = (filterAction)!springMacroRequestContext.requestUri>
                <#assign params = params!{'categoryId': ''}>
                <#assign selectedCategoryId = (params.categoryId!'')?string>

                    <form id="vacancy-filter" action="${filterAction}" class="row g-2 mb-3">
                        <label for="categoryId" class="form-label"><@spring.message "list.filter.category"/></label>

                        <div class="col-sm-3">
                            <select class="form-select" id="categoryId" name="categoryId">
                                <option value=""><@spring.message "list.filter.category.all"/></option>
                                <#list categories as c>
                                    <option value="${c.id}"
                                            <#if selectedCategoryId == c.id?string>selected</#if>>${c.name}</option>
                                </#list>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <input name="salaryFrom" type="number" min="0" class="form-control"
                                   placeholder="З/п от" value="${salaryFrom!}">
                        </div>
                        <div class="col-sm-2">
                            <input name="salaryTo" type="number" min="0" class="form-control"
                                   placeholder="З/п до" value="${salaryTo!}">
                        </div>

                        <div class="col-12 d-flex gap-2">
                            <div class="col-md-2 align-self-end">
                                <button type="submit" class="btn btn-primary w-100">
                                    <i class="bi bi-funnel-fill"></i> <@spring.message "list.filter.button"/>
                                </button>
                            </div>
                            <button class="btn btn-outline-secondary" type="button" id="resetFilter">Сброс</button>
                            <span class="ms-auto small text-bright">Показано: <b id="shownCount">${list.content?size}</b> из <b id="totalCount">${list.totalElements}</b></span>
                        </div>
                    </form>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <table class="table table-dark table-striped table-hover align-middle" id="vacancyTable">
                    <thead>
                    <tr>
                        <#list headers as h>
                            <th class="text-start">${h}</th></#list>
                    </tr>
                    </thead>

                    <tbody>

                    <#list list.content as item>
                    <#--                         <tr> -->
                        <tr class="clickable-row"
                            data-category-name="${(item.categoryName!'')?lower_case}"
                            data-salary="${item.salary!0}"
                            data-name="${(item.name!'')?lower_case}"
                            data-active="${(item.active?c)!'false'}"
                            data-href="/${type}/${item.id}">
                            <td>${item.name!'-'}</td>
                            <td>${item.categoryName!'—'}</td>
                            <td><#if item.salary??>${item.salary?string["#,##0"]} $<#else>—</#if></td>
                            <td>
                                <#if item.updateTime?has_content>
                                    <#if item.updateTime?is_date || item.updateTime?is_datetime>
                                        ${item.updateTime?string("yyyy-MM-dd HH:mm")}
                                    <#elseif item.updateTime?is_string>
                                        ${item.updateTime?replace("T"," ")}
                                    <#else>${item.updateTime}</#if>
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
                    <nav aria-label="<@spring.message "pagination.label"/>">
                        <ul class="pagination justify-content-center">
                            <#if list.hasPrevious()>
                                <li class="page-item">
                                    <a class="btn btn-outline-primary"
                                       href="${filterAction}?categoryId=${selectedCategoryId}&page=${list.number - 1}">
                                        ← <@spring.message "pagination.previous"/>
                                    </a>
                                </li>
                            </#if>
                            <li class="page-item active"><span
                                        class="btn btn-primary disabled">${list.number + 1}</span></li>
                            <#if list.hasNext()>
                                <li class="page-item">
                                    <a class="btn btn-outline-primary"
                                       href="${filterAction}?categoryId=${selectedCategoryId}&page=${list.number + 1}">
                                        <@spring.message "pagination.next"/> →
                                    </a>
                                </li>
                            </#if>
                        </ul>
                    </nav>
                </div>
            </div>
        </#if>
    </div>

    <script>

        document.addEventListener("DOMContentLoaded", function () {
            // Кликабельные строки
            document.querySelectorAll(".clickable-row").forEach(row => {
                row.addEventListener("click", () => {
                    const href = row.getAttribute("data-href");
                    if (href) window.location.href = href;
                });
            });

            const form = document.getElementById("vacancy-filter");
            const storageKey = "vacancyFilter";

            const saved = localStorage.getItem(storageKey);
            if (saved) {
                const data = JSON.parse(saved);
                for (const [k, v] of Object.entries(data)) {
                    const el = form.elements.namedItem(k);
                    if (!el) continue;

                    if (el.type === "checkbox") {
                        el.checked = v === true || v === "true";
                    } else {
                        el.value = v ?? "";
                    }
                }
            }

            form.addEventListener("submit", () => {
                const data = {};
                for (const el of form.elements) {
                    if (!el.name) continue;
                    if (el.type === "checkbox") {
                        data[el.name] = el.checked;
                    } else {
                        data[el.name] = el.value;
                    }
                }
                localStorage.setItem(storageKey, JSON.stringify(data));
            });

            document.getElementById("resetFilter").addEventListener("click", () => {
                localStorage.removeItem(storageKey);
                form.reset();
                window.location = form.action;
            });
        });
    </script>
</@l.layout>
