<#import "../layout.ftlh" as l>
<#import "/spring.ftl" as spring>

<@l.layout title="Публикации">
    <div class="container py-3">
        <div class="d-flex justify-content-between align-items-center mb-3">
            <h1 class="mb-0"><@spring.message 'publication.list.title'!'Публикации'/></h1>
            <#assign auth = (SPRING_SECURITY_CONTEXT.authentication)!>
            <#assign isAuth = auth?? && auth.principal?has_content && auth.principal != 'anonymousUser'>
            <#if isAuth>
                <a class="btn btn-primary" href="/publications/new">
                    <i class="bi bi-plus-circle"></i> <@spring.message 'publication.list.create'!'Новая публикация'/>
                </a>
            </#if>
        </div>

        <#-- дефолты для отсутствующих значений -->
        <#assign selectedCategoryId = (selectedCategoryId!'')?string>
        <#assign searchTermSafe = (searchTerm!'')?string>
        <#assign sortValue = (currentSort!'-updatedAt')?string>
        <#assign filterAction = (filterAction!'/publications')?string>

        <form id="publication-filter" class="row g-3 mb-4" method="get" action="${filterAction}">
            <div class="col-sm-3">
                <label for="categoryId" class="form-label"><@spring.message 'list.filter.category'!'Категория'/></label>
                <select class="form-select" id="categoryId" name="categoryId">
                    <option value=""><@spring.message 'list.filter.category.all'!'Все категории'/></option>
                    <#list categories as c>
                        <option value="${c.id}"
                                <#if selectedCategoryId == c.id?string>selected</#if>>${c.name}</option>
                    </#list>
                </select>
            </div>

            <div class="col-sm-3">
                <label for="term" class="form-label"><@spring.message 'list.filter.term'!'Поиск'/></label>
                <input id="term" name="term" type="search" class="form-control" value="${searchTermSafe}">
            </div>

            <div class="col-sm-3">
                <label for="sort" class="form-label"><@spring.message 'publication.list.sort'!'Сортировка'/></label>
                <select class="form-select" id="sort" name="sort">
                    <option value="-updatedAt" <#if sortValue == '-updatedAt'>selected</#if>>
                        <@spring.message 'publication.list.sort.updated.desc'!'По обновлению (сначала новые)'/>
                    </option>
                    <option value="updatedAt" <#if sortValue == 'updatedAt'>selected</#if>>
                        <@spring.message 'publication.list.sort.updated.asc'!'По обновлению (сначала старые)'/>
                    </option>
                    <option value="-createdAt" <#if sortValue == '-createdAt'>selected</#if>>
                        <@spring.message 'publication.list.sort.created.desc'!'По дате создания (новые)'/>
                    </option>
                    <option value="createdAt" <#if sortValue == 'createdAt'>selected</#if>>
                        <@spring.message 'publication.list.sort.created.asc'!'По дате создания (старые)'/>
                    </option>
                    <option value="title" <#if sortValue == 'title'>selected</#if>>
                        <@spring.message 'publication.list.sort.title'!'По заголовку'/>
                    </option>
                </select>
            </div>

            <div class="col-sm-3 d-flex align-items-end gap-2">
                <button type="submit" class="btn btn-primary">
                    <i class="bi bi-funnel"></i> <@spring.message 'list.filter.button'!'Фильтровать'/>
                </button>
                <button type="button" class="btn btn-outline-secondary" id="resetPublicationFilter">
                    <@spring.message 'list.filter.reset'!'Сбросить'/>
                </button>
            </div>
        </form>

        <#if list.content?size == 0>
            <div class="alert alert-secondary"><@spring.message 'publication.list.empty'!'Ничего не найдено'/></div>
        <#else>
            <div class="row row-cols-1 row-cols-md-2 g-3">
                <#list list.content as item>
                    <div class="col">
                        <div class="card h-100 bg-dark border-secondary text-light shadow-sm">
                            <div class="card-body d-flex flex-column">
                                <h5 class="card-title">${item.title!'-'}</h5>
                                <p class="card-text text-secondary small mb-2">
                                    ${item.categoryName!'—'} • ${item.authorName!'—'}
                                </p>
                                <p class="card-text text-secondary small mb-3">
                                    <@spring.message 'publication.card.updated'!'Обновлено'/>:
                                    <#if item.updatedAt??>${item.updatedAt}<#else>-</#if>
                                </p>
                                <div class="mt-auto d-flex justify-content-between align-items-center">
                                    <span class="badge bg-secondary">
                                        <i class="bi bi-chat-dots"></i> ${item.commentCount!0}
                                    </span>
                                    <a class="btn btn-outline-light btn-sm" href="/publications/${item.id}">
                                        <@spring.message 'publication.card.read'!'Читать'/>
                                    </a>
                                </div>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        </#if>

        <#if list.totalPages gt 1>
            <nav class="mt-4" aria-label="<@spring.message 'pagination.label'!'Пагинация'/>">
                <ul class="pagination justify-content-center">
                    <#if list.hasPrevious()>
                        <li class="page-item">
                            <a class="page-link"
                               href="?page=${list.number - 1}&categoryId=${selectedCategoryId?url}&term=${searchTermSafe?url}&sort=${sortValue?url}">
                                <@spring.message 'pagination.previous'!'Назад'/>
                            </a>
                        </li>
                    </#if>
                    <li class="page-item active"><span class="page-link">${list.number + 1}</span></li>
                    <#if list.hasNext()>
                        <li class="page-item">
                            <a class="page-link"
                               href="?page=${list.number + 1}&categoryId=${selectedCategoryId?url}&term=${searchTermSafe?url}&sort=${sortValue?url}">
                                <@spring.message 'pagination.next'!'Вперёд'/>
                            </a>
                        </li>
                    </#if>
                </ul>
            </nav>
        </#if>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', () => {
            const form = document.getElementById('publication-filter');
            const resetBtn = document.getElementById('resetPublicationFilter');
            const storageKey = 'publicationFilter';

            const saved = localStorage.getItem(storageKey);
            if (saved && form) {
                try {
                    const data = JSON.parse(saved);
                    for (const [key, value] of Object.entries(data)) {
                        const el = form.elements.namedItem(key);
                        if (!el) continue;
                        el.value = value ?? '';
                    }
                } catch (e) {
                    console.warn('Invalid stored publication filter', e);
                }
            }

            form?.addEventListener('submit', () => {
                const payload = {};
                for (const control of form.elements) {
                    if (!control.name) continue;
                    payload[control.name] = control.value;
                }
                localStorage.setItem(storageKey, JSON.stringify(payload));
            });

            resetBtn?.addEventListener('click', () => {
                localStorage.removeItem(storageKey);
                form.reset();
                window.location = form.action || window.location.pathname;
            });
        });
    </script>
</@l.layout>
