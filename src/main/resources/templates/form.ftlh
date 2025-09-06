
<#import "layout.ftlh" as l>
<#import "spring.ftl" as spring>

<@l.layout title="Создать">

    <form action="${formAction}" method="post" class="form">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">

        <@spring.bind "${dtoName}.name"/>
        <div class="mb-3">
            <label class="form-label">Имя</label>
            <input class="form-control" name="${spring.status.expression}" value="${spring.status.value!}">
        </div>

        <@spring.bind "${dtoName}.categoryId"/>
        <div class="mb-3">
            <label class="form-label">Категория</label>
            <select class="form-select" name="${spring.status.expression}">
                <option value="">Выберите категорию</option>
                <#list categories as category>
                    <option value="${category.id}"
                            <#if spring.status.value?? && spring.status.value?string == category.id?string>selected</#if>>
                        ${category.name}
                    </option>
                </#list>
            </select>
        </div>

        <@spring.bind "${dtoName}.salary"/>
        <div class="mb-3">
            <label class="form-label">Зарплата</label>
            <input type="number" class="form-control" name="${spring.status.expression}" value="${spring.status.value!}" min="0">
        </div>

        <@spring.bind "${dtoName}.active"/>
        <div class="form-check mb-4">
            <input type="checkbox" class="form-check-input" id="active" name="${spring.status.expression}" value="true"
                   <#if spring.status.value?? && (spring.status.value)?string == "true">checked</#if>>
            <label class="form-check-label" for="active">Активна?</label>
        </div>

        <#if formType == "resume">
            <h5>Опыт работы</h5>
            <#list dto.workExperiences as we>
                <div class="mb-3">
                    <label>Компания</label>
                    <input type="text" name="dto.workExperiences[${we_index}].companyName" value="${we.companyName!}" class="form-control">
                </div>
                <div class="mb-3">
                    <label>Должность</label>
                    <input type="text" name="dto.workExperiences[${we_index}].position" value="${we.position!}" class="form-control">
                </div>
                <div class="mb-3">
                    <label>Годы</label>
                    <input type="number" name="dto.workExperiences[${we_index}].years" value="${we.years!}" class="form-control" min="0">
                </div>
                <div class="mb-3">
                    <label>Обязанности</label>
                    <textarea name="dto.workExperiences[${we_index}].responsibilities" class="form-control">${we.responsibilities!}</textarea>
                </div>
                <hr>
            </#list>

            <h5>Образование</h5>
            <#list dto.educationInfos as edu>
                <div class="mb-3">
                    <label>Учебное заведение</label>
                    <input type="text" name="dto.educationInfos[${edu_index}].institution" value="${edu.institution!}" class="form-control">
                </div>
                <div class="mb-3">
                    <label>Программа</label>
                    <input type="text" name="dto.educationInfos[${edu_index}].program" value="${edu.program!}" class="form-control">
                </div>
                <div class="mb-3">
                    <label>Степень</label>
                    <input type="text" name="dto.educationInfos[${edu_index}].degree" value="${edu.degree!}" class="form-control">
                </div>
                <div class="mb-3">
                    <label>Дата начала</label>
                    <input type="date" name="dto.educationInfos[${edu_index}].startDate" value="${edu.startDate!}" class="form-control">
                </div>
                <div class="mb-3">
                    <label>Дата окончания</label>
                    <input type="date" name="dto.educationInfos[${edu_index}].endDate" value="${edu.endDate!}" class="form-control">
                </div>
                <hr>
            </#list>

            <h5>Контакты</h5>
            <#list dto.contactInfos as c>
                <div class="mb-3">
                    <label>Тип контакта</label>
                    <input type="text" name="dto.contactInfos[${c_index}].contactValue" value="${c.contactValue!}" class="form-control">
                </div>
                <hr>
            </#list>

        </#if>

        <button class="btn btn-primary w-100" type="submit">
            <i class="bi bi-check-circle"></i> Сохранить
        </button>
    </form>

</@l.layout>
