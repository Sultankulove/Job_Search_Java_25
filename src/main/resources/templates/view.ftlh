<#import "/spring.ftl" as spring>
<#include "layout.ftlh">

<@layout title="Просмотр">
    <div class="container mt-4">
        <#if r??>
            <h2>Резюме: ${r.name}</h2>
            <p>Категория: ${r.category.name}</p>
            <p>Желаемая зарплата: ${r.salary}</p>
            <p>Статус: ${r.active?string("Активно","Не активно")}</p>
            <p>Обновлено:
                <#if r.updateTime?has_content>
                    <#if r.updateTime?is_date_like>
                        ${r.updateTime?datetime?string("dd.MM.yyyy HH:mm")}
                    <#else>
                        ${r.updateTime}
                    </#if>
                </#if>
            </p>

            <hr>

            <#if r.educationInfos??>
                <h3>Образование </h3>
            </#if>
            <#list r.educationInfos as educationInfo >
                <p>Учебное заведение: ${educationInfo.institution}</p>
                <p>Программа: ${educationInfo.program}</p>
                <p>Степень: ${educationInfo.degree}</p>
                <p>Дата начала: ${educationInfo.startDate}</p>
                <p>Дата конца: ${educationInfo.endDate}</p>
            </#list>

            <hr>

            <#if r.workExperiences??>
                <h3>Опыт работы</h3>
            </#if>
            <#list r.workExperiences as workExp>
                <p>Компания: ${workExp.companyName}</p>
                <p>Позиция: ${workExp.position}</p>
                <p>Обязанности: ${workExp.responsibilities}</p>
                <p>Сколько лет: ${workExp.years}</p>
            </#list>

            <hr>
            <#if r.contactInfos??>
                <h3>Контакты</h3>
            </#if>
            <#list r.contactInfos as contacts>
                <#list contact as c>
                    <#if c.id==contacts.typeId>
                        <p>${c.name}: ${contacts.contactValue}</p>
                    </#if>
                </#list>
            </#list>


        </#if>

        <#if v??>
            <h2>Вакансия: ${v.name}</h2>
            <p>Категория: ${v.categoryName}</p>
            <p>Зарплата: ${v.salary}</p>
            <p>Опыт: ${v.expFrom}–${v.expTo} лет</p>
            <p>Описание: ${v.description}</p>
            <p>Обновлено: ${v.updateTime}</p>


            <#if auth && role == "ROLE_APPLICANT">
                <#if respondSuccess??>
                    <div class="alert alert-success mt-3"><@spring.message respondSuccess/></div>
                </#if>
                <#if respondError??>
                    <div class="alert alert-danger mt-3"><@spring.message respondError/></div>
                </#if>

                <#if responded?? && responded>
                    <div class="alert alert-info mt-3 d-flex align-items-center gap-2">
                        <span><@spring.message "response.already.exists"/></span>
                        <a class="btn btn-sm btn-outline-light" href="/chats">
                            <i class="bi bi-chat-dots"></i> <@spring.message "response.open.chat"/>
                        </a>
                    </div>

                <#else>
                    <#if myResumes?? && myResumes?size gt 0>
                        <button class="btn btn-primary mt-3"
                                data-bs-toggle="modal" data-bs-target="#respondModal">
                            <i class="bi bi-envelope-plus"></i> <@spring.message "response.respond.cta"/>
                        </button>

                        <div class="modal fade" id="respondModal" tabindex="-1"
                             aria-labelledby="respondModalLabel" aria-hidden="true">
                            <div class="modal-dialog">
                                <div class="modal-content text-dark">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="respondModalLabel">
                                            <@spring.message "response.modal.title"/>
                                        </h5>
                                        <button type="button" class="btn-close"
                                                data-bs-dismiss="modal" aria-label="Close"></button>
                                    </div>

                                    <form method="post" action="/vacancy/${v.id}/respond">
                                        <div class="modal-body">
                                            <#if _csrf??>
                                                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                            </#if>

                                            <div class="mb-3">
                                                <label class="form-label" for="resumeId">
                                                    <@spring.message "response.modal.select"/>
                                                </label>
                                                <select id="resumeId" name="resumeId" class="form-select" required>
                                                    <#list myResumes as r>
                                                        <option value="${r.id}">
                                                            ${r.name!'-'}
                                                            <#if r.updateTime??>${r.updateTime}</#if>
                                                        </option>
                                                    </#list>
                                                </select>
                                            </div>

                                            <div class="d-flex align-items-center gap-2">
                                    <span class="small text-secondary">
                                        <@spring.message "response.modal.or.create"/>
                                    </span>
                                                <a href="/resume/new" class="btn btn-outline-secondary btn-sm" target="_blank">
                                                    <i class="bi bi-plus-circle"></i>
                                                    <@spring.message "response.create.resume"/>
                                                </a>
                                            </div>
                                        </div>

                                        <div class="modal-footer">
                                            <button type="button" class="btn btn-outline-secondary"
                                                    data-bs-dismiss="modal">
                                                <@spring.message "common.cancel"/>
                                            </button>
                                            <button type="submit" class="btn btn-primary">
                                                <@spring.message "response.modal.submit"/>
                                            </button>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>

                    <#else>
                        <div class="alert alert-warning mt-3 d-flex align-items-center gap-2">
                            <span><@spring.message "response.no.active.resume"/></span>
                            <a class="btn btn-sm btn-outline-light" href="/resume/new" target="_blank">
                                <i class="bi bi-plus-circle"></i> <@spring.message "response.create.resume"/>
                            </a>
                        </div>
                    </#if>
                </#if>

            <#elseif !auth>
                <a href="/auth/login" class="btn btn-warning">
                    <@spring.message "response.login.to.apply"/>
                </a>
            </#if>


            <#if role == "ROLE_EMPLOYER" && responses??>
                <h4 class="mt-4">Отклики:</h4>
                <ul>
                    <#list responses as resp>
                        <li>
                            Соискатель: ${resp.applicantName}
                            <form method="post" action="/chat/start/${resp.id}" style="display:inline">
                                <#if _csrf??>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                                </#if>

                                <button class="btn btn-sm btn-success">Начать чат</button>
                            </form>
                        </li>
                    </#list>
                </ul>
            </#if>
        </#if>
    </div>
</@layout>
