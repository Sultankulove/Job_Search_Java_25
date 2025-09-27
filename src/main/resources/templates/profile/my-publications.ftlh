<#import "../layout.ftlh" as l>
<#import "/spring.ftl" as spring>

<@l.layout title="Мои публикации">
    <div class="container py-3">
        <div class="d-flex align-items-center justify-content-between mb-3">
            <h1 class="h4 mb-0"><@spring.message 'mypubs.heading'/></h1>
            <a class="btn btn-primary" href="/publications/new">
                <i class="bi bi-plus-circle"></i> <@spring.message 'mypubs.create'/>
            </a>
        </div>

        <form class="row g-2 mb-3">
            <div class="col-sm-6 col-md-7">
                <input class="form-control" type="search" name="q" value="${q!}" placeholder="<@spring.message 'mypubs.search.ph'/>">
            </div>
            <div class="col-sm-4 col-md-3">
                <select class="form-select" name="sort">
                    <option value="-updatedAt"${((sort!'-updatedAt') == '-updatedAt')?then(' selected','')}>⬇ <@spring.message 'mypubs.sort.updated.desc'/></option>
                    <option value="updatedAt"${((sort!'') == 'updatedAt')?then(' selected','')}>⬆ <@spring.message 'mypubs.sort.updated.asc'/></option>
                    <option value="title"${((sort!'') == 'title')?then(' selected','')}>A→Z <@spring.message 'mypubs.sort.title.asc'/></option>

                </select>
            </div>
            <div class="col-sm-2 col-md-2">
                <button class="btn btn-outline-secondary w-100"><@spring.message 'common.apply'/></button>
            </div>
        </form>

        <#if page.content?size == 0>
            <div class="alert alert-secondary"><@spring.message 'mypubs.empty'/></div>
        <#else>
            <div class="list-group">
                <#list page.content as p>
                    <div class="list-group-item">
                        <div class="d-flex justify-content-between align-items-start">
                            <div>
                                <a class="fw-semibold" href="/publications/${p.id}">${p.title}</a>
                                <div class="small text-secondary">
                                    <@spring.message 'mypubs.updated'/>:
                                    ${p.updatedAt} ·
                                    <@spring.message 'mypubs.comments'/>: ${p.visibleComments!0}
                                </div>
                            </div>
                            <div class="btn-group">
                                <a class="btn btn-sm btn-outline-primary" href="/publications/${p.id}/edit">
                                    <i class="bi bi-pencil-square"></i> <@spring.message 'common.edit'/>
                                </a>
                                <form method="post" action="/publications/${p.id}/delete" onsubmit="return confirm('<@spring.message 'mypubs.delete.confirm'/>')">
                                    <#if _csrf??>
                                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                    </#if>
                                    <button class="btn btn-sm btn-outline-danger">
                                        <i class="bi bi-trash"></i> <@spring.message 'common.delete'/>
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>

        <#-- Пагинация -->
            <nav class="mt-3">
                <ul class="pagination">
                    <#assign current = page.number, total = page.totalPages>
                    <li class="page-item ${(current==0)?then('disabled','')}">
                        <a class="page-link" href="?page=${current-1}&size=${page.size}&q=${q!}&sort=${sort!}">&laquo;</a>
                    </li>
                    <#list 0..(total-1) as i>
                        <li class="page-item ${(i==current)?then('active','')}">
                            <a class="page-link" href="?page=${i}&size=${page.size}&q=${q!}&sort=${sort!}">${i+1}</a>
                        </li>
                    </#list>
                    <li class="page-item ${((current+1)>=total)?then('disabled','')}">
                        <a class="page-link" href="?page=${current+1}&size=${page.size}&q=${q!}&sort=${sort!}">&raquo;</a>
                    </li>
                </ul>
            </nav>
        </#if>
    </div>
</@l.layout>
