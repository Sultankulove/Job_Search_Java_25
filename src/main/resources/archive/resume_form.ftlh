<#import "layout.ftlh" as main>

<@main.layout title="Создание резюме">
    <section class="container" style="max-width:1100px;margin:24px auto;">
        <h1 style="color:#d9534f;margin:0 0 18px 0;">Создание резюме:</h1>

        <form action="${formAction!'/resume/new'}" method="post" id="resumeForm" autocomplete="on">

            <#if (errors?? && errors?size > 0) || (bindingResult?? && bindingResult.hasErrors())>
                <div class="alert alert-danger">
                    <strong>Исправьте ошибки в форме:</strong>
                    <ul style="margin:8px 0 0 20px;">

                        <#if bindingResult??>
                            <#list bindingResult.fieldErrors![] as fe>
                                <li>${fe.field}: ${fe.defaultMessage}</li>
                            </#list>
                            <#list bindingResult.globalErrors![] as ge>
                                <li>${ge.defaultMessage}</li>
                            </#list>
                        <#else>
                            <#list errors![] as e>
                                <li>${e}</li>
                            </#list>
                        </#if>
                    </ul>
                </div>
            </#if>


            <#if _csrf??>
                <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
            </#if>

            <div class="mb-3">
                <label class="form-label">Название резюме:</label>
                <input class="form-control" type="text" name="name" required
                       placeholder="Введите название"
                       value="${(dto.name)!}">
            </div>

            <div class="mb-3">
                <label class="form-label">Категория:</label>
                <select class="form-select" name="categoryId" required>
                    <option value="">Выберите категорию</option>
                    <#list categories![] as c>
                        <option value="${c.id}"
                                <#if dto.categoryId?has_content && (dto.categoryId?long == c.id)>selected</#if>>
                            ${c.name}
                        </option>
                    </#list>
                </select>
            </div>

            <div class="mb-3">
                <label class="form-label">Ожидаемый уровень оклада:</label>
                <input class="form-control" type="number" min="0" step="1"
                       name="salary" placeholder="Введите оклад"
                       value="${(dto.salary)!}">
            </div>

            <hr class="my-4"/>

            <h3 class="h5">О себе:</h3>


            <div class="row g-3 align-items-start">
                <div class="col-12 col-md-9">
                    <div id="workExpContainer" data-next-index="${(dto.workExperiences![])?size}">
                        <#list dto.workExperiences![] as we>
                            <#assign i = we?index>
                            <div class="card we-item mb-3" data-index="${i}">
                                <div class="card-body">
                                    <div class="row g-2">
                                        <div class="col-12 col-md-3">
                                            <label class="form-label small">Год(ы)</label>
                                            <input type="number" inputmode="numeric" pattern="\\d*"
                                                   min="0" step="1"
                                                   name="workExperiences[${i}].years"
                                                   value="${(we.years)!}"/>
                                        </div>
                                        <div class="col-12 col-md-4">
                                            <label class="form-label small">Название компании</label>
                                            <input class="form-control" required
                                                   name="workExperiences[${i}].companyName"
                                                   placeholder="Компания" value="${(we.companyName)!}">
                                        </div>
                                        <div class="col-12 col-md-5">
                                            <label class="form-label small">Должность</label>
                                            <input class="form-control" name="workExperiences[${i}].position"
                                                   placeholder="Должность" value="${(we.position)!}">
                                        </div>
                                        <div class="col-12">
                                            <label class="form-label small">Обязанности</label>
                                            <textarea class="form-control" rows="3"
                                                      name="workExperiences[${i}].responsibilities"
                                                      placeholder="Ключевые задачи и достижения">${(we.responsibilities)!}</textarea>
                                        </div>
                                    </div>
                                </div>
                                <button type="button" class="btn btn-light btn-remove-we"
                                        title="Удалить опыт"
                                        style="position:absolute;right:8px;top:8px;">✕
                                </button>
                            </div>
                        </#list>
                    </div>
                </div>

                <div class="col-12 col-md-3">
                    <button type="button" id="addWorkExp" class="btn btn-outline-primary w-100"
                            style="height:74px;font-size:22px;">+
                    </button>
                </div>
            </div>


            <div class="row g-3 align-items-start mt-2">
                <div class="col-12 col-md-9">
                    <div id="eduContainer" data-next-index="${(dto.educationInfos![])?size}">
                        <#list dto.educationInfos![] as ed>
                            <#assign j = ed?index>
                            <div class="card edu-item mb-3" data-index="${j}">
                                <div class="card-body">
                                    <div class="row g-2">
                                        <div class="col-12 col-md-6">
                                            <label class="form-label small">Где учился</label>
                                            <input class="form-control" required
                                                   name="educationInfos[${j}].institution"
                                                   placeholder="ВУЗ / Колледж" value="${(ed.institution)!}">
                                        </div>
                                        <div class="col-12 col-md-3">
                                            <label class="form-label small">Образование</label>
                                            <input class="form-control" name="educationInfos[${j}].degree"
                                                   placeholder="Специальность / степень" value="${(ed.degree)!}">
                                        </div>
                                        <div class="col-12 col-md-3">
                                            <label class="form-label small">Срок обучения</label>
                                            <input type="date"
                                                   name="dto.educationInfos[${i}].startDate"
                                                   value="<#if ed.startDate??>
                                                    <#if ed.startDate?is_date>
                                                        ${ed.startDate?string('yyyy-MM-dd')}
                                                    <#else>
                                                        ${ed.startDate}
                                                    </#if>
                                                    </#if>">


                                            <<input type="date"
                                                    name="dto.educationInfos[${i}].endDate"
                                                    value="<#if ed.endDate??>
                                                    <#if ed.endDate?is_date>
                                                        ${ed.endDate?string('yyyy-MM-dd')}
                                                    <#else>
                                                        ${ed.endDate}
                                                    </#if>
                                                    </#if>">
                                        </div>
                                    </div>
                                </div>
                                <button type="button" class="btn btn-light btn-remove-edu"
                                        title="Удалить образование"
                                        style="position:absolute;right:8px;top:8px;">✕
                                </button>
                            </div>
                        </#list>
                    </div>
                </div>

                <div class="col-12 col-md-3">
                    <button type="button" id="addEducation" class="btn btn-outline-primary w-100"
                            style="height:74px;font-size:22px;">+
                    </button>
                </div>
            </div>

            <hr class="my-4"/>


            <h3 class="h5 mb-2">Ссылки на соц. сети / контакты:</h3>


            <#assign existingContacts = {} />
            <#list dto.contactInfos![] as ci0>
                <#if ci0.typeId?has_content>
                    <#assign existingContacts = existingContacts + { (ci0.typeId?long)?string : (ci0.contactValue!"") } >
                </#if>
            </#list>

            <#assign contactIndex = 0>
            <div class="row g-3">
                <#list contactTypes![] as t>
                    <#assign key = (t.id?long)?string>
                    <#assign prefill = (existingContacts[key])!"" >
                    <div class="col-12 col-md-6">
                        <div class="input-group">
            <span class="input-group-text" style="min-width:46px;">
              <#switch t.name?lower_case>
                  <#case "telegram">✈️<#break>
                  <#case "facebook">f<#break>
                  <#case "linkedin">in<#break>
                  <#case "email">✉️<#break>
                  <#case "phone">📞<#break>
                  <#default>🔗
              </#switch>
            </span>
                            <input type="hidden" name="dto.contactInfos[${contactIndex}].typeId" value="${t.id}">
                            <input class="form-control" name="contactInfos[${contactIndex}].contactValue"
                                   placeholder="${t.name}" value="${prefill}">
                        </div>
                    </div>
                    <#assign contactIndex = contactIndex + 1>
                </#list>
            </div>

            <div class="form-check mt-4">
                <input type="hidden" name="dto.active" value="false">
                <input class="form-check-input" type="checkbox" id="activeCheck"
                       name="active" value="true"
                       <#if (dto.active)!false>checked</#if>>
                <label class="form-check-label" for="activeCheck">Активно</label>

            </div>

            <div class="mt-4">
                <button type="submit" class="btn btn-success px-4 py-2">Опубликовать</button>
            </div>
        </form>
    </section>


    <script type="text/template" id="tpl-work-exp">
        <div class="card we-item mb-3" data-index="__INDEX__">
            <div class="card-body">
                <div class="row g-2">
                    <div class="col-12 col-md-3">
                        <label class="form-label small">Год(ы)</label>
                        <input class="form-control" name="workExperiences[__INDEX__].years"
                               placeholder="Напр., 2022">
                    </div>
                    <div class="col-12 col-md-4">
                        <label class="form-label small">Название компании</label>
                        <input class="form-control" required
                               name="workExperiences[__INDEX__].companyName"
                               placeholder="Компания">
                    </div>
                    <div class="col-12 col-md-5">
                        <label class="form-label small">Должность</label>
                        <input class="form-control" name="workExperiences[__INDEX__].position"
                               placeholder="Должность">
                    </div>
                    <div class="col-12">
                        <label class="form-label small">Обязанности</label>
                        <textarea class="form-control" rows="3"
                                  name="workExperiences[__INDEX__].responsibilities"
                                  placeholder="Ключевые задачи и достижения"></textarea>
                    </div>
                </div>
            </div>
            <button type="button" class="btn btn-light btn-remove-we"
                    title="Удалить опыт"
                    style="position:absolute;right:8px;top:8px;">✕
            </button>
        </div>
    </script>

    <script type="text/template" id="tpl-education">
        <div class="card edu-item mb-3" data-index="__INDEX__">
            <div class="card-body">
                <div class="row g-2">
                    <div class="col-12 col-md-6">
                        <label class="form-label small">Где учился</label>
                        <input class="form-control" required
                               name="educationInfos[__INDEX__].institution"
                               placeholder="ВУЗ / Колледж">
                    </div>
                    <div class="col-12 col-md-3">
                        <label class="form-label small">Образование</label>
                        <input class="form-control" name="educationInfos[__INDEX__].degree"
                               placeholder="Специальность / степень">
                    </div>
                    <div class="col-12 col-md-3">
                        <label class="form-label small">Срок обучения</label>
                        <input class="form-control" name="educationInfos[__INDEX__].startDate"
                               placeholder="Напр., 2019–2023">
                        <input class="form-control" name="educationInfos[__INDEX__].endDate"
                               placeholder="Напр., 2019–2023">
                    </div>
                </div>
            </div>
            <button type="button" class="btn btn-light btn-remove-edu"
                    title="Удалить образование"
                    style="position:absolute;right:8px;top:8px;">✕
            </button>
        </div>
    </script>

    <script>
        (function () {
            const weContainer = document.getElementById('workExpContainer');
            const eduContainer = document.getElementById('eduContainer');

            const weTpl = document.getElementById('tpl-work-exp').textContent.trim();
            const eduTpl = document.getElementById('tpl-education').textContent.trim();

            function nextIndex(el) {
                const n = parseInt(el.getAttribute('data-next-index') || '0', 10);
                el.setAttribute('data-next-index', String(n + 1));
                return n;
            }

            function addBlock(container, tpl) {
                const idx = nextIndex(container);
                const html = tpl.replaceAll('__INDEX__', idx);
                const node = document.createElement('div');
                node.innerHTML = html;
                container.appendChild(node.firstElementChild);
                renumber(container);

            }

            function renumber(container) {
                const items = Array.from(container.children);
                items.forEach((card, newIdx) => {
                    card.setAttribute('data-index', newIdx);

                    card.querySelectorAll('input[name], textarea[name]').forEach(el => {
                        el.name = el.name.replace(/\[(\d+)\]/g, '[' + newIdx + ']');
                    });
                });
                container.setAttribute('data-next-index', String(items.length));
            }

            document.getElementById('addWorkExp').addEventListener('click', () => addBlock(weContainer, weTpl));
            document.getElementById('addEducation').addEventListener('click', () => addBlock(eduContainer, eduTpl));

            weContainer.addEventListener('click', function (e) {
                if (e.target.classList.contains('btn-remove-we')) {
                    e.target.closest('.we-item')?.remove();
                    renumber(weContainer);
                }
            });
            eduContainer.addEventListener('click', function (e) {
                if (e.target.classList.contains('btn-remove-edu')) {
                    e.target.closest('.edu-item')?.remove();
                    renumber(eduContainer);
                }
            });
        })();
    </script>

    <style>
        .form-label {
            font-weight: 600;
        }

        .card {
            position: relative;
            border: 1px solid rgba(0, 0, 0, .08);
            box-shadow: 0 2px 8px rgba(0, 0, 0, .06);
            border-radius: 12px;
        }

        .btn {
            border-radius: 10px;
        }
    </style>
</@main.layout>

