<#import "../layout.ftlh" as l>
<#import "/spring.ftl" as spring>

<@l.layout title="${(detail.title)!''}">
    <div class="container py-4 publication-view">
        <div class="d-flex justify-content-between align-items-start flex-wrap gap-3 mb-3">
            <div>
                <h1 class="mb-1">${detail.title!'-'}</h1>
                <p class="text-secondary mb-1">
                    <i class="bi bi-person-circle"></i> ${detail.authorName!'—'} •
                    <i class="bi bi-folder"></i> ${detail.categoryName!'—'}
                </p>
                <p class="text-secondary small mb-0">
                    <@spring.message 'publication.card.updated'/>:
                    <#if detail.updatedAt??>${detail.updatedAt}<#else>-</#if>
                </p>
            </div>
            <div class="d-flex gap-2">
                <#if isAuthor?? && isAuthor>
                    <a class="btn btn-outline-light" href="/publications/${detail.id}/edit">
                        <i class="bi bi-pencil-square"></i> <@spring.message 'publication.view.edit'/>
                    </a>
                </#if>
                <#if (isAuthor?? && isAuthor) || (isModerator?? && isModerator)>
                    <form method="post" action="/publications/${detail.id}/delete"
                          onsubmit="return confirm('<@spring.message "publication.view.delete.confirm"/>');">
                        <#if _csrf??>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                        </#if>
                        <button class="btn btn-danger" type="submit">
                            <i class="bi bi-archive"></i> <@spring.message 'publication.view.delete'/>
                        </button>
                    </form>
                </#if>
            </div>
        </div>

        <#if success??>
            <div class="alert alert-success"><@spring.message 'success'!'Выполнено'/></div>
        </#if>
        <#if error??>
            <div class="alert alert-danger"><@spring.message 'error'!'Ошибка'/></div>
        </#if>

        <#if detail.deleted>
            <div class="alert alert-warning">
                <@spring.message 'publication.view.deleted'/>
            </div>
        </#if>

        <#if detail.cover??>
            <div class="mb-4">
                <img src="/publications/${detail.id}/cover" class="img-fluid rounded" alt="cover">
            </div>
        </#if>

        <article class="mb-5 lead">
            ${detail.content?replace("
", "<br>")?no_esc}
        </article>

        <section id="comments" class="mb-4">
            <h3 class="mb-3"><@spring.message 'publication.view.comments'/></h3>

            <#if commentDto?? && (SPRING_SECURITY_CONTEXT.authentication.principal!'anonymousUser') != 'anonymousUser'>
                <form method="post" action="/publications/${detail.id}/comments" class="mb-4">
                    <#if _csrf??>
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                    </#if>
                    <div class="mb-2">
                        <textarea class="form-control" name="content" rows="3"
                                  placeholder="<@spring.message 'publication.comment.placeholder'/>"
                                  required></textarea>
                    </div>
                    <button class="btn btn-primary" type="submit">
                        <i class="bi bi-chat-text"></i> <@spring.message 'publication.comment.submit'/>
                    </button>
                </form>
            <#else>
                <div class="alert alert-secondary">
                    <@spring.message 'publication.comment.login'/>
                </div>
            </#if>

            <#if detail.comments?size == 0>
                <div class="alert alert-secondary"><@spring.message 'publication.comment.empty'/></div>
            <#else>
                <div class="list-group">
                    <#list detail.comments as comment>
                        <div class="list-group-item bg-dark text-light border-secondary mb-2 rounded">
                            <div class="d-flex justify-content-between align-items-start">
                                <div>
                                    <div class="fw-semibold">${comment.authorName!'—'}</div>
                                    <div class="text-secondary small">
                                        <#if comment.createdAt??>${comment.createdAt}<#else>-</#if>
                                    </div>
                                </div>
                                <#if comment.canDelete>
                                    <form method="post"
                                          action="/publications/${detail.id}/comments/${comment.id}/delete"
                                          onsubmit="return confirm('<@spring.message 'publication.comment.delete.confirm'/>)">
                                        <#if _csrf??>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                        </#if>
                                        <button class="btn btn-sm btn-outline-danger" type="submit">
                                            <i class="bi bi-trash"></i>
                                        </button>
                                    </form>
                                </#if>
                            </div>
                            <div class="mt-2">
                                <#if comment.deleted>
                                    <span class="text-muted fst-italic">
                                        <#if comment.deletedByModerator>
                                            <@spring.message 'publication.comment.deleted.moderator'/>
                                        <#else>
                                            <@spring.message 'publication.comment.deleted.author'/>
                                        </#if>
                                    </span>
                                <#else>
                                    ${comment.content!''}
                                </#if>
                            </div>
                        </div>
                    </#list>
                </div>
            </#if>
        </section>
    </div>
</@l.layout>
