<#import "layout.ftlh" as l>

<@l.layout title="<@spring.message 'form.title'! 'Создание/Редактирование резюме'/>">

<head>
    <title>${(formAction?contains("/edit"))?then("Редактирование резюме","Создание резюме")}</title>
    <style>
        .err{color:#b00020;margin:4px 0}
        .edu-item{border:1px solid #ccc;padding:8px;margin:8px 0}
        .row{display:flex;gap:8px;flex-wrap:wrap}
        .col{display:flex;flex-direction:column;min-width:220px}
        /*.rm{align-self:flex-end;margin-top:20px}*/

        body {
            background-color: #1e1e1e;
            color: #e0e0e0;
            font-family: 'Segoe UI', sans-serif;
        }

        label, select, input, h2, h3, button {
            color: #e0e0e0;
        }

        select, input[type="text"], input[type="number"], input[type="date"], input[type="email"] {
            background-color: #2c2c2c;
            color: #e0e0e0;
            border: 1px solid #444;
            border-radius: 4px;
            padding: 6px;
        }

        input[type="checkbox"] {
            transform: scale(1.2);
            margin-right: 6px;
        }

        select:focus, input:focus {
            outline: 2px solid #00acc1;
            background-color: #2f2f2f;
        }

        button {
            background-color: #00acc1;
            color: #fff;
            border: none;
            padding: 8px 16px;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.2s ease-in-out;
        }

        button:hover {
            background-color: #0097a7;
        }

        button.rm {
            background-color: #c62828;
            color: #fff;
        }

        button.rm:hover {
            background-color: #b71c1c;
        }

        .edu-item, .contact-item {
            background-color: #2a2a2a;
            border: 1px solid #444;
            padding: 12px;
            border-radius: 6px;
            margin-bottom: 12px;
        }

        .err {
            color: #ff5252;
        }

        h2, h3 {
            color: #ffb300;
            margin-top: 16px;
        }

        .row {
            display: flex;
            gap: 12px;
            flex-wrap: wrap;
        }

        .col {
            display: flex;
            flex-direction: column;
            min-width: 220px;
            flex-grow: 1;
        }

        ::placeholder {
            color: #999;
        }

        select option {
            background-color: #2c2c2c;
            color: #e0e0e0;
        }

        i, svg, img {
            filter: invert(90%);
        }

        /*button.rm {*/
        /*    font-size: 12px;*/
        /*    padding: 4px 8px;*/
        /*    background-color: #c62828;*/
        /*    color: #fff;*/
        /*    border-radius: 4px;*/
        /*    border: none;*/
        /*    cursor: pointer;*/
        /*}*/

        /*button.rm:hover {*/
        /*    background-color: #b71c1c;*/
        /*}*/

        .rm {
            padding: 0.25rem 0.5rem;
            /*margin: 20px;*/
            font-size: 0.8rem;
            background-color: #dc3545;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            white-space: nowrap;
        }

        .rm:hover {
            background-color: #bb2d3b;
        }




    </style>
</head>

<h2>${(formAction?contains("/edit"))?then("Редактирование резюме","Создание резюме")}</h2>

<#if (br?? && br.hasErrors()) || (errors?? && errors?size > 0)>
    <div class="err">
        <ul>
            <#if br??>
                <#list br.globalErrors as e>
                    <li>${e.defaultMessage!e.code}</li>
                </#list>
            </#if>
        </ul>
    </div>
</#if>

<form id="resumeForm" action="${formAction!'/resume/new'}" method="post">
    <#if _csrf??>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
    </#if>

    <div class="col">
        <label>Название резюме</label>
        <input type="text" name="name" value="${(dto.name)!}">
        <#if errors??>
            <#list errors?filter(e -> e.field == "name") as err>
                <div class="err">${err.defaultMessage!}</div>
            </#list>
        </#if>
    </div>

    <div class="col">
        <label>Ожидаемый уровень оклада</label>
        <input type="number"
               name="salary"
               min="0" step="0.01"
               value="${(dto.salary?c)!}">
        <#if errors??>
            <#list errors?filter(e -> e.field == "salary") as err>
                <div class="err">${err.defaultMessage!}</div>
            </#list>
        </#if>
    </div>

    <div class="col">
        <label>Категория</label>
        <select name="categoryId" required>
            <option value="">— выберите —</option>
            <#list categories![] as c>
                <#assign selected = (dto.categoryId?? && (dto.categoryId?long == c.id))>
                <option value="${c.id}" <#if selected>selected</#if>>${c.name}</option>
            </#list>
        </select>
        <#if errors??>
            <#list errors?filter(e -> e.field == "categoryId") as err>
                <div class="err">${err.defaultMessage!}</div>
            </#list>
        </#if>
    </div>

    <div class="col">
        <label>
            <input type="checkbox" name="active" <#if dto.active>checked</#if> > Активное резюме

        </label>
        <#if errors??>
            <#list errors?filter(e -> e.field == "active") as err>
                <div class="err">${err.defaultMessage!}</div>
            </#list>
        </#if>
    </div>

    <h3>Образование</h3>

    <div id="eduContainer">
        <#assign eduList = dto.educationInfos![]>
        <#if eduList?size == 0>
            <#assign eduList = [ {} ]>
        </#if>

        <#list eduList as ed>
            <#assign i = ed?index>
            <div class="edu-item" data-index="${i}">
                <div class="row">
                    <div class="col">
                        <label>Учебное заведение</label>
                        <input type="text" name="educationInfos[${i}].institution" value="${(ed.institution)!}">
                        <#if errors??>
                            <#list errors?filter(e -> e.field == "educationInfos[" + i + "].institution") as err>
                                <div class="err">${err.defaultMessage!}</div>
                            </#list>
                        </#if>
                    </div>

                    <div class="col">
                        <label>Программа</label>
                        <input type="text" name="educationInfos[${i}].program" value="${(ed.program)!}">
                        <#if errors??>
                            <#list errors?filter(e -> e.field == "educationInfos[" + i + "].program") as err>
                                <div class="err">${err.defaultMessage!}</div>
                            </#list>
                        </#if>
                    </div>

                    <div class="col">
                        <label>Степень</label>
                        <input type="text" name="educationInfos[${i}].degree" value="${(ed.degree)!}">
                        <#if errors??>
                            <#list errors?filter(e -> e.field == "educationInfos[" + i + "].degree") as err>
                                <div class="err">${err.defaultMessage!}</div>
                            </#list>
                        </#if>
                    </div>

                    <div class="col">
                        <label>Дата начала</label>
                        <input type="date" name="educationInfos[${i}].startDate" value="${(ed.startDate)!}">
                        <#if errors??>
                            <#list errors?filter(e -> e.field == "educationInfos[" + i + "].startDate") as err>
                                <div class="err">${err.defaultMessage!}</div>
                            </#list>
                        </#if>
                    </div>

                    <div class="col">
                        <label>Дата окончания</label>
                        <input type="date" name="educationInfos[${i}].endDate" value="${(ed.endDate)!}">
                        <#if errors??>
                            <#list errors?filter(e -> e.field == "educationInfos[" + i + "].endDate") as err>
                                <div class="err">${err.defaultMessage!}</div>
                            </#list>
                        </#if>
                    </div>

                    <button type="button" class="rm" onclick="removeEdu(this)">Удалить</button>
                </div>
            </div>
        </#list>
    </div>

    <button type="button" onclick="addEdu()">+ Добавить образование</button>


    <h3>Опыт работы</h3>

    <div id="workContainer">
        <#assign workList = dto.workExperiences![]>
        <#if workList?size == 0>
            <#assign workList = [ {} ]>
        </#if>

        <#list workList as w>
            <#assign i = w?index>
            <div class="edu-item" data-windex="${i}">
                <div class="row">
                    <div class="col">
                        <label>Компания</label>
                        <input type="text" name="workExperiences[${i}].companyName" value="${(w.companyName)!}">
                        <#if errors??>
                            <#list errors?filter(e -> e.field == "workExperiences[" + i + "].companyName") as err>
                                <div class="err">${err.defaultMessage!}</div>
                            </#list>
                        </#if>
                    </div>

                    <div class="col">
                        <label>Должность</label>
                        <input type="text" name="workExperiences[${i}].position" value="${(w.position)!}">
                        <#if errors??>
                            <#list errors?filter(e -> e.field == "workExperiences[" + i + "].position") as err>
                                <div class="err">${err.defaultMessage!}</div>
                            </#list>
                        </#if>
                    </div>

                    <div class="col" style="min-width:320px">
                        <label>Обязанности</label>
                        <input type="text" name="workExperiences[${i}].responsibilities" value="${(w.responsibilities)!}">
                        <#if errors??>
                            <#list errors?filter(e -> e.field == "workExperiences[" + i + "].responsibilities") as err>
                                <div class="err">${err.defaultMessage!}</div>
                            </#list>
                        </#if>
                    </div>

                    <div class="col" style="min-width:160px">
                        <label>Сколько лет</label>
                        <input type="text" name="workExperiences[${i}].years" value="${(w.years)!}">
                        <#if errors??>
                            <#list errors?filter(e -> e.field == "workExperiences[" + i + "].years") as err>
                                <div class="err">${err.defaultMessage!}</div>
                            </#list>
                        </#if>
                    </div>

                    <button type="button" class="rm" onclick="removeWork(this)">Удалить</button>
                </div>
            </div>
        </#list>
    </div>

    <button type="button" onclick="addWork()">+ Добавить опыт</button>
    <div style="margin-top:12px;">
        <button type="submit">${(formAction?contains("/edit"))?then("Сохранить","Создать")}</button>
    </div>


    <h3>Контакты</h3>

    <div id="contactContainer">
        <#assign contactList = dto.contactInfos![]>
        <#if contactList?size == 0>
            <#assign contactList = [ {} ]>
        </#if>

        <#list contactList as c>
            <#assign i = c?index>
            <div class="contact-item" data-index="${i}">
                <div class="row">
                    <div class="col">
                        <label>Тип контакта</label>
                        <select name="contactInfos[${i}].typeId">
                            <option value="">— выберите —</option>
                            <#list contactTypes![] as t>
                                <#assign selected = (c.typeId?? && (c.typeId?long == t.id))>
                                <option value="${t.id}" <#if selected>selected</#if>>${t.name}</option>
                            </#list>
                        </select>
                    </div>

                    <div class="col">
                        <label>Значение</label>
                        <input type="text" name="contactInfos[${i}].contactValue" value="${(c.contactValue)!}">
                    </div>

                    <button type="button" class="rm" onclick="removeContact(this)">Удалить</button>
                </div>
            </div>
        </#list>
    </div>

    <button type="button" onclick="addContact()">+ Добавить контакт</button>

</form>

<script>
    const eduContainer = document.getElementById('eduContainer');
    const workContainer = document.getElementById('workContainer');
    const contactContainer = document.getElementById('contactContainer');

    function addEdu() {
        const idx = eduContainer.children.length;
        const node = document.createElement('div');
        node.className = 'edu-item';
        node.dataset.index = String(idx);
        node.innerHTML = '<div class="row"> ' +
            '<div class="col"> ' +
            '<label>Учебное заведение</label> ' +
            '<input name="educationInfos[' + idx + '].institution"> ' +
            '</div> ' +
            '<div class="col"> ' +
            '<label>Программа</label> ' +
            '<input name="educationInfos[' + idx + '].program"> ' +
            '</div> ' +
            '<div class="col"> ' +
            '<label>Степень</label> ' +
            '<input name="educationInfos[' + idx + '].degree"> ' +
            '</div> ' +
            '<div class="col"> ' +
            '<label>Дата начала</label> ' +
            '<input type="date" name="educationInfos[' + idx + '].startDate"> ' +
            '</div> ' +
            '<div class="col">' +
            '<label>Дата окончания</label> ' +
            '<input type="date" name="educationInfos[' + idx + '].endDate"> ' +
            '</div> ' +
            '<button type="button" class="rm" onclick="removeEdu(this)">Удалить</button> ' +
            '</div>';
        eduContainer.appendChild(node);
    }

    function removeEdu(btn) {
        const card = btn.closest('.edu-item');
        if (!card) return;
        eduContainer.removeChild(card);
        reindexEdu();
    }

    function reindexEdu() {
        const items = eduContainer.querySelectorAll('.edu-item');
        items.forEach((el, i) => {
            el.dataset.index = String(i);
            el.querySelectorAll('input[name]').forEach(inp => {
                inp.name = inp.name
                    .replace(/educationInfos\[\d+]\.institution/, 'educationInfos[' + i + '].institution')
                    .replace(/educationInfos\[\d+]\.program/,     'educationInfos[' + i + '].program')
                    .replace(/educationInfos\[\d+]\.degree/,      'educationInfos[' + i + '].degree')
                    .replace(/educationInfos\[\d+]\.startDate/,   'educationInfos[' + i + '].startDate')
                    .replace(/educationInfos\[\d+]\.endDate/,     'educationInfos[' + i + '].endDate');
            });
        });
    }

    document.getElementById('resumeForm')
        .addEventListener('submit', () => reindexEdu());


    ///////


    function workTemplate(idx) {
        return '' +
            '<div class="edu-item" data-windex="' + idx + '">' +
            '<div class="row">' +
            '<div class="col">' +
            '<label>Компания</label>' +
            '<input name="workExperiences[' + idx + '].companyName">' +
            '</div>' +
            '<div class="col">' +
            '<label>Должность</label>' +
            '<input name="workExperiences[' + idx + '].position">' +
            '</div>' +
            '<div class="col" style="min-width:320px">' +
            '<label>Обязанности</label>' +
            '<input name="workExperiences[' + idx + '].responsibilities">' +
            '</div>' +
            '<div class="col" style="min-width:160px">' +
            '<label>Годы (например: 2021–2024)</label>' +
            '<input name="workExperiences[' + idx + '].years">' +
            '</div>' +
            '<button type="button" class="rm" onclick="removeWork(this)">Удалить</button>' +
            '</div>' +
            '</div>';
    }

    function addWork() {
        const idx = workContainer.children.length;
        const wrap = document.createElement('div');
        wrap.innerHTML = workTemplate(idx).trim();
        workContainer.appendChild(wrap.firstElementChild);
    }

    function removeWork(btn) {
        const card = btn.closest('.edu-item');
        if (!card) return;
        workContainer.removeChild(card);
        reindexWork();
    }

    function reindexWork() {
        const items = workContainer.querySelectorAll('.edu-item');
        items.forEach((el, i) => {
            el.dataset.windex = String(i);
            el.querySelectorAll('input[name]').forEach(inp => {
                inp.name = inp.name
                    .replace(/workExperiences\[\d+]\.companyName/,    'workExperiences[' + i + '].companyName')
                    .replace(/workExperiences\[\d+]\.position/,        'workExperiences[' + i + '].position')
                    .replace(/workExperiences\[\d+]\.responsibilities/,'workExperiences[' + i + '].responsibilities')
                    .replace(/workExperiences\[\d+]\.years/,           'workExperiences[' + i + '].years');
            });
        });
    }

    document.getElementById('resumeForm')
        .addEventListener('submit', () => {
            reindexEdu();
            reindexWork();
        });


    function addContact() {
        const idx = contactContainer.children.length;
        const node = document.createElement('div');
        node.className = 'contact-item';
        node.dataset.index = String(idx);
        node.innerHTML =
            '<div class="row">' +
            '<div class="col">' +
            '<label>Тип контакта</label>' +
            '<select name="contactInfos[' + idx + '].typeId">' +
            '<option value="">— выберите —</option>' +
            '<#list contactTypes![] as t>' +
            '<option value="${t.id}">${t.name}</option>' +
            '</#list>' +
            '</select>' +
            '</div>' +
            '<div class="col">' +
            '<label>Значение</label>' +
            '<input type="text" name="contactInfos[' + idx + '].contactValue">' +
            '</div>' +
            '<button type="button" class="rm" onclick="removeContact(this)">Удалить</button>' +
            '</div>';
        contactContainer.appendChild(node);
    }

    function removeContact(btn) {
        const card = btn.closest('.contact-item');
        if (!card) return;
        contactContainer.removeChild(card);
        reindexContacts();
    }

    function reindexContacts() {
        const items = contactContainer.querySelectorAll('.contact-item');
        items.forEach((el, i) => {
            el.dataset.index = String(i);
            el.querySelectorAll('input,select').forEach(inp => {
                inp.name = inp.name
                    .replace(/contactInfos\[\d+]\.typeId/, 'contactInfos[' + i + '].typeId')
                    .replace(/contactInfos\[\d+]\.value/,   'contactInfos[' + i + '].value');
            });
        });
    }

    document.getElementById('resumeForm')
        .addEventListener('submit', () => {
            reindexContacts();
            reindexEdu();
            reindexWorkExp();
        });
</script>

</@l.layout>
