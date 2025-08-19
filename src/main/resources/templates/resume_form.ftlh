<#import "layout.ftlh" as main>

<@main.layout ; spring>

<#assign br = .data_model["org.springframework.validation.BindingResult.resume"]! />

<link rel="stylesheet"
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"/>

<div class="container py-4">
    <h2 class="text-center text-danger mb-4">Создание резюме:</h2>

    <form class="needs-validation" novalidate method="post" action="/resume/new">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

        <div class="mb-3">
            <label class="form-label" for="name">Название резюме:</label>
            <input id="name" name="name" type="text" class="form-control"
                   placeholder="Введите название" value="${(resume.name)!}"/>
            <#if br?? && br.hasFieldErrors("name")>
                <div class="text-danger small mt-1">
                    <#list br.getFieldErrors("name") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list>
                </div>
            </#if>
        </div>

        <div class="mb-3">
            <label class="form-label" for="categoryId">Категория:</label>
            <select id="categoryId" name="categoryId" class="form-select">
                <option value="">Выберите категорию</option>
                <#if categories??>
                    <#list categories as c>
                        <option value="${c.id}"
                                <#if resume?has_content && resume.categoryId?? && resume.categoryId == c.id>selected</#if>>
                            ${c.name}
                        </option>
                    </#list>
                <#else>
                    <option disabled>Категории не загружены</option>
                </#if>
            </select>

            <#if br?? && br.hasFieldErrors("categoryId")>
                <div class="text-danger small mt-1">
                    <#list br.getFieldErrors("categoryId") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list>
                </div>
            </#if>
        </div>

        <div class="mb-3">
            <label class="form-label" for="salary">Ожидаемый уровень оклада:</label>
            <input id="salary" name="salary" type="number" step="0.01" min="0"
                   class="form-control" placeholder="Введите оклад"
                   value="${(resume.salary)!}"/>
            <#if br?? && br.hasFieldErrors("salary")>
                <div class="text-danger small mt-1">
                    <#list br.getFieldErrors("salary") as e>${e.defaultMessage}<#if e_has_next><br/></#if></#list>
                </div>
            </#if>
        </div>

        <div class="mb-4">
            <label class="form-label" for="description">О себе:</label>
            <textarea id="description" class="form-control" rows="4"
                      placeholder="Кратко о себе, ключевые навыки (пока не сохраняется — для будущей итерации)."></textarea>
        </div>

        <div class="form-check mb-4">
            <input type="hidden" name="isActive" value="false">
            <input class="form-check-input" type="checkbox" id="isActive" name="isActive" value="true"
                   <#if resume?has_content && resume.isActive?? && resume.isActive>checked</#if>>
            <label class="form-check-label" for="isActive">Активно</label>
        </div>



        <#if errors?? && (errors?size > 0)>
            <div class="alert alert-danger">
                <ul class="mb-0">
                    <#list errors as e>
                        <li>${e.defaultMessage!e}</li>
                    </#list>
                </ul>
            </div>
        </#if>

        <div class="d-grid">
            <button type="submit" class="btn btn-success btn-lg">Опубликовать</button>
        </div>
    </form>
</div>


</@main.layout>