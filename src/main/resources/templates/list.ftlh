<#import "layout.ftlh" as l>
<#import "/spring.ftl" as spring>

<@l.layout title="${title! springMacroRequestContext.getMessage('list.title')!'Лист'}">

    <head>
        <link rel="stylesheet" href="/static/styles/list.css">
        <style>
            .clickable-row { cursor: pointer; }
        </style>
        <title></title>
    </head>

    <div class="container">
        <div class="row mb-4">
            <div class="col">
                <h1 class="mb-3">${title! springMacroRequestContext.getMessage('list.title')}</h1>

                <#assign params = params!{'categoryId': ''}>
                <#assign selectedCategoryId = (params.categoryId!'')?string>
                <#assign filterAction = (filterAction)!springMacroRequestContext.requestUri>

                <form id="vacancy-filter" action="${filterAction}" class="row g-3 mb-3">
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
                               placeholder="${springMacroRequestContext.getMessage('list.filter.salaryFrom')!'З/п от'}" value="${salaryFrom!}">
                    </div>
                    <div class="col-sm-2">
                        <input name="salaryTo" type="number" min="0" class="form-control"
                               placeholder="${springMacroRequestContext.getMessage('list.filter.salaryTo'!'З/п до')}" value="${salaryTo!}">
                    </div>

                    <div class="col-sm-3">
                        <input type="search" class="form-control" id="term" name="term"
                               placeholder="${springMacroRequestContext.getMessage('list.filter.term')!'Поиск'}" value="${searchTerm!''}">
                    </div>

                    <div class="col-sm-2">
                        <select class="form-select" id="sort" name="sort">
                            <option value="-updateTime" <#if (currentSort!'-updateTime') == '-updateTime'>selected</#if>>
                                <@spring.message 'list.sort.newest'/>
                            </option>
                            <option value="salary" <#if currentSort=='salary'>selected</#if>>
                                <@spring.message 'list.sort.salary.asc'/>
                            </option>
                            <option value="-salary" <#if currentSort=='-salary'>selected</#if>>
                                <@spring.message 'list.sort.salary.desc'/>
                            </option>
                            <option value="name" <#if currentSort=='name'>selected</#if>>
                                <@spring.message 'list.sort.name'/>
                            </option>
                        </select>
                    </div>

                    <div class="col-12 d-flex gap-2 align-items-end">
                        <div class="col-md-2">
                            <button type="submit" class="btn btn-primary w-100">
                                <i class="bi bi-funnel-fill"></i> <@spring.message "list.filter.button"/>
                            </button>
                        </div>
                        <button class="btn btn-outline-secondary" type="button" id="resetFilter">
                            <@spring.message "list.filter.reset"/>
                        </button>
                        <span class="ms-auto small text-bright">
                            <@spring.message "list.counter.shown"/>:
                            <b id="shownCount">${list.content?size}</b>
                            <@spring.message "list.counter.of"/>
                            <b id="totalCount">${list.totalElements}</b>
                        </span>
                    </div>
                </form>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <table class="table table-dark table-striped table-hover align-middle" id="vacancyTable" data-entity="${type}">
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
                        <tr class="clickable-row" data-href="/${type}/${item.id}">
                            <td>${item.name!'-'}</td>

                            <td>${item.categoryName!'—'}</td>

                            <td>
                                <#if item.salary??>
                                    ${item.salary?string["#,##0"]} $
                                <#else>—</#if>
                            </td>

                            <td>
                                <#if item.updateTime?has_content>
                                    <#if item.updateTime?is_date || item.updateTime?is_datetime>
                                        ${item.updateTime}
                                    <#elseif item.updateTime?is_string>
                                        ${item.updateTime}
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
                    <nav aria-label="<@spring.message 'pagination.label'/>">
                        <ul class="pagination justify-content-center">
                            <#if list.hasPrevious()>
                                <li class="page-item">
                                    <a class="btn btn-outline-primary" href="?categoryId=${selectedCategoryId}&page=${list.number - 1}">
                                        ← <@spring.message "pagination.previous"/>
                                    </a>
                                </li>
                            </#if>

                            <li class="page-item active" aria-current="page">
                                <span class="btn btn-primary disabled">${list.number + 1}</span>
                            </li>

                            <#if list.hasNext()>
                                <li class="page-item">
                                    <a class="btn btn-outline-primary" href="?categoryId=${selectedCategoryId}&page=${list.number + 1}">
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
            const form = document.getElementById("vacancy-filter");
            const storageKey = "vacancyFilter";

            const bindRowClick = (root = document) => {
                root.querySelectorAll(".clickable-row").forEach(row => {
                    row.addEventListener("click", () => {
                        const href = row.getAttribute("data-href");
                        if (href) window.location.href = href;
                    });
                });
            };
            bindRowClick();

            const saved = localStorage.getItem(storageKey);
            if (saved && form) {
                try {
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
                } catch (e) {
                    console.warn('Failed to parse saved filters', e);
                }
            }

            form?.addEventListener("submit", () => {
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

            const resetBtn = document.getElementById("resetFilter");
            resetBtn?.addEventListener("click", () => {
                localStorage.removeItem(storageKey);
                form?.reset();
                window.location = form?.action || window.location.pathname;
            });

            const table = document.getElementById("vacancyTable");
            const entity = table?.dataset.entity;
            if (entity === "vacancy" && form && table) {
                const searchInput = document.getElementById("term");
                const sortSelect = document.getElementById("sort");
                const tbody = table.querySelector("tbody");
                const shownCount = document.getElementById("shownCount");
                const totalCount = document.getElementById("totalCount");
                const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
                const csrfToken = document.querySelector('meta[name="_csrf"]')?.content;

                const debounce = (fn, delay = 300) => {
                    let timer;
                    return (...args) => {
                        clearTimeout(timer);
                        timer = setTimeout(() => fn(...args), delay);
                    };
                };

                const renderRows = items => {
                    if (!tbody) return;
                    tbody.innerHTML = '';
                    items.forEach(item => {
                        const tr = document.createElement('tr');
                        tr.className = 'clickable-row';
                        tr.dataset.href = '/vacancy/'+ item.id;
                        const salary = item.salary != null ? new Intl.NumberFormat('ru-RU').format(item.salary) + ' $' : '—';
                        const updated = item.updateTime ? String(item.updateTime).replace('T', ' ') : '';
                        tr.innerHTML =
                            '<td>' + item.name + '</td>' +
                            '<td>' + item.categoryName + '</td>' +
                            '<td>' + salary + '</td>' +
                            '<td>' + updated + '</td>';
                        tbody.appendChild(tr);
                    });
                    bindRowClick(tbody);
                };

                const performLiveSearch = () => {
                    const params = new URLSearchParams(new FormData(form));
                    if (searchInput) params.set('term', searchInput.value.trim());
                    params.set('size', '20');
                    params.set('activeOnly', 'true');
                    const headers = {};
                    if (csrfHeader && csrfToken) headers[csrfHeader] = csrfToken;
                    fetch('/api/vacancies/search?' + params.toString(), { headers })
                        .then(resp => resp.ok ? resp.json() : [])
                        .then(items => {
                            renderRows(items);
                            if (shownCount) shownCount.textContent = items.length;
                            if (totalCount) totalCount.textContent = items.length;
                        })
                        .catch(err => console.error('Live search failed', err));
                };

                if (searchInput) {
                    searchInput.addEventListener('input', debounce(performLiveSearch));
                }

                sortSelect?.addEventListener('change', () => form.requestSubmit());
            }
        });
    </script>

</@l.layout>
