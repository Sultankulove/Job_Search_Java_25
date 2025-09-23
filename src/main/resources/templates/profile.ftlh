<#import "layout.ftlh" as l>
<#import "/spring.ftl" as spring>

<@l.layout title="<@spring.message 'profile.title'/>">
    <div class="container py-3">

        <div class="card bg-dark text-light border-0 rounded-4 shadow p-4 mb-4">
            <div class="row g-4 align-items-center">
                <div class="col-md-3 text-center">
                    <#if user?? && user.avatar?has_content>
                        <img src="/api/profile/avatar" class="rounded-circle border"
                             style="width:160px;height:160px;object-fit:cover" alt="<@spring.message 'profile.avatar.alt'/>">
                    <#else>
                        <img src="/static/images/default-avatar.png" class="rounded-circle border"
                             style="width:160px;height:160px;object-fit:cover" alt="<@spring.message 'profile.avatar.alt'/>">
                    </#if>
                </div>

                <div class="col-md-9">
                    <h2 class="mb-1">${(user.name)!'—'} ${(user.surname)!''}</h2>
                    <div class="text-secondary small mb-2">
                        <i class="bi bi-envelope"></i> ${(user.email)!'—'} &nbsp;|&nbsp;
                        <i class="bi bi-telephone"></i> ${(user.phoneNumber)!'—'} &nbsp;|&nbsp;
                        <i class="bi bi-cake2"></i> ${(user.age)!'—'}
                    </div>

                    <button id="editProfileBtn" class="btn btn-outline-light" data-bs-toggle="modal" data-bs-target="#editProfileModal">
                        <i class="bi bi-pencil"></i> <@spring.message 'profile.btn.edit'/>
                    </button>

                    <#if isEmployer?? && isEmployer>
                        <a class="btn btn-primary ms-2" href="/vacancy/new">
                            <i class="bi bi-plus-circle"></i> <@spring.message 'profile.btn.createVacancy'/>
                        </a>
                        <a class="btn btn-secondary ms-2" href="/profile/vacancies">
                            <i class="bi bi-folder2-open"></i> <@spring.message 'profile.btn.myVacancies'/>
                        </a>
                    </#if>

                    <#if isApplicant?? && isApplicant>
                        <a class="btn btn-primary ms-2" href="/resume/new">
                            <i class="bi bi-plus-circle"></i> <@spring.message 'profile.btn.createResume'/>
                        </a>
                        <a class="btn btn-secondary ms-2" href="/profile/resumes">
                            <i class="bi bi-folder2-open"></i> <@spring.message 'profile.btn.myResumes'/>
                        </a>
                    </#if>
                </div>
            </div>
        </div>

        <#if isEmployer?? && isEmployer && myVacancies?? && myVacancies?size gt 0>
            <div class="card bg-dark text-light border-0 rounded-4 shadow p-3 mb-4">
                <h4 class="mb-3"><@spring.message 'profile.section.myVacancies'/></h4>
                <div class="table-responsive">
                    <table class="table table-dark table-hover align-middle">
                        <thead>
                        <tr>
                            <th><@spring.message 'table.col.name'/></th>
                            <th><@spring.message 'table.col.category'/></th>
                            <th><@spring.message 'table.col.salary'/></th>
                            <th><@spring.message 'table.col.updated'/></th>
                            <th class="text-end"><@spring.message 'table.col.actions'/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list myVacancies as v>
                            <tr>
                                <td>${v.name!'-'}</td>
                                <td>${v.categoryName!'-'}</td>
                                <td><#if v.salary??>${v.salary?string["#,##0"]} $<#else>—</#if></td>
                                <td>${(v.updateTime!'—')?string}</td>
                                <td class="text-end">
                                    <button class="btn btn-sm btn-outline-info me-1 js-touch-vacancy" data-id="${v.id}">
                                        <i class="bi bi-arrow-clockwise"></i> <@spring.message 'table.action.refresh'/>
                                    </button>
                                    <a class="btn btn-sm btn-outline-light me-1" href="/vacancy/${v.id}/edit">
                                        <i class="bi bi-pencil-square"></i> <@spring.message 'table.action.edit'/>
                                    </a>
                                    <div class="form-check form-switch d-inline-block">
                                        <input class="form-check-input js-publish-vacancy" type="checkbox"
                                               data-id="${v.id}" <#if v.active?? && v.active>checked</#if>>
                                        <label class="form-check-label small"><@spring.message 'table.action.published'/></label>
                                    </div>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </#if>

        <#if isApplicant?? && isApplicant && myResumes?? && myResumes?size gt 0>
            <div class="card bg-dark text-light border-0 rounded-4 shadow p-3 mb-4">
                <h4 class="mb-3"><@spring.message 'profile.section.myResumes'/></h4>
                <div class="table-responsive">
                    <table class="table table-dark table-hover align-middle">
                        <thead>
                        <tr>
                            <th><@spring.message 'table.col.name'/></th>
                            <th><@spring.message 'table.col.category'/></th>
                            <th><@spring.message 'table.col.salary'/></th>
                            <th><@spring.message 'table.col.updated'/></th>
                            <th class="text-end"><@spring.message 'table.col.actions'/></th>
                        </tr>
                        </thead>
                        <tbody>
                        <#list myResumes as r>
                            <tr>
                                <td>${r.name!'-'}</td>
                                <td>${r.categoryName!'-'}</td>
                                <td><#if r.salary??>${r.salary?string["#,##0"]} $<#else>—</#if></td>
                                <td>${(r.updateTime!'—')?string}</td>
                                <td class="text-end">
                                    <button class="btn btn-sm btn-outline-info me-1 js-touch-resume" data-id="${r.id}">
                                        <i class="bi bi-arrow-clockwise"></i> <@spring.message 'table.action.refresh'/>
                                    </button>
                                    <a class="btn btn-sm btn-outline-light me-1" href="/resume/${r.id}/edit">
                                        <i class="bi bi-pencil-square"></i> <@spring.message 'table.action.edit'/>
                                    </a>
                                    <div class="form-check form-switch d-inline-block">
                                        <input class="form-check-input js-publish-resume" type="checkbox"
                                               data-id="${r.id}" <#if r.active?? && r.active>checked</#if>>
                                        <label class="form-check-label small"><@spring.message 'table.action.published'/></label>
                                    </div>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </#if>

    </div>

    <div class="modal fade" id="editProfileModal" tabindex="-1" aria-hidden="true">
        <div class="modal-dialog modal-lg modal-dialog-centered">
            <div class="modal-content bg-dark text-light">
                <div class="modal-header">
                    <h5 class="modal-title"><i class="bi bi-person-lines-fill me-2"></i><@spring.message 'profile.modal.title'/></h5>
                    <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <form id="editProfileForm">
                        <div class="row g-3">
                            <div class="col-md-6">
                                <label class="form-label"><@spring.message 'profile.form.name'/></label>
                                <input class="form-control" name="name" id="f-name" maxlength="50" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label"><@spring.message 'profile.form.surname'/></label>
                                <input class="form-control" name="surname" id="f-surname" maxlength="50">
                            </div>
                            <div class="col-md-6">
                                <label class="form-label"><@spring.message 'profile.form.email'/></label>
                                <input type="email" class="form-control" name="email" id="f-email" maxlength="64" required>
                            </div>
                            <div class="col-md-6">
                                <label class="form-label"><@spring.message 'profile.form.phone'/></label>
                                <input class="form-control" name="phoneNumber" id="f-phone" maxlength="20"
                                       placeholder="<@spring.message 'profile.form.phone.placeholder'/>">
                            </div>
                            <div class="col-md-3">
                                <label class="form-label"><@spring.message 'profile.form.age'/></label>
                                <input type="number" class="form-control" name="age" id="f-age" min="18" max="100">
                            </div>
                            <div class="col-md-9">
                                <label class="form-label"><@spring.message 'profile.form.avatar'/></label>
                                <input type="file" class="form-control" id="f-avatar" name="avatar" accept="image/*">
                                <div class="form-text"><@spring.message 'profile.form.avatar.hint'/></div>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-outline-secondary" data-bs-dismiss="modal"><@spring.message 'common.cancel'/></button>
                    <button class="btn btn-primary" id="saveProfileBtn">
                        <span class="spinner-border spinner-border-sm me-2 d-none" id="saveSpinner"></span>
                        <@spring.message 'common.save'/>
                    </button>
                </div>
            </div>
        </div>
    </div>

    <script>
        window.addEventListener('load', () => {
            'use strict';

            const I18N = {
                saveError: "<@spring.message 'profile.error.save'/>",
                avatarError: "<@spring.message 'profile.error.avatar'/>"
            };

            const user = {
                name: "${(user.name)!''?js_string}",
                surname: "${(user.surname)!''?js_string}",
                email: "${(user.email)!''?js_string}",
                phone: "${(user.phoneNumber)!''?js_string}",
                age: "${(user.age)!''?js_string}"
            };

            const modalEl  = document.getElementById('editProfileModal');
            const fName    = document.getElementById('f-name');
            const fSurname = document.getElementById('f-surname');
            const fEmail   = document.getElementById('f-email');
            const fPhone   = document.getElementById('f-phone');
            const fAge     = document.getElementById('f-age');
            const fAvatar  = document.getElementById('f-avatar');

            modalEl.addEventListener('show.bs.modal', () => {
                fName.value    = user.name;
                fSurname.value = user.surname;
                fEmail.value   = user.email;
                fPhone.value   = user.phone;
                fAge.value     = user.age;
                fAvatar.value  = "";
            });

            const saveBtn = document.getElementById('saveProfileBtn');
            const spinner = document.getElementById('saveSpinner');

            const csrfHeader = document.querySelector('meta[name="_csrf_header"]')?.content;
            const csrfToken  = document.querySelector('meta[name="_csrf"]')?.content;
            const withCsrf = (init = {}) => {
                init.headers = init.headers || {};
                if (csrfHeader && csrfToken) init.headers[csrfHeader] = csrfToken;
                return init;
            };

            saveBtn.addEventListener('click', async () => {
                saveBtn.disabled = true; spinner.classList.remove('d-none');
                try {
                    const form = new URLSearchParams();
                    form.append('name', fName.value || '');
                    form.append('surname', fSurname.value || '');
                    form.append('email', fEmail.value || '');
                    form.append('phoneNumber', fPhone.value || '');
                    if (fAge.value) form.append('age', fAge.value);

                    let resp = await fetch('/profile/edit', withCsrf({
                        method: 'POST',
                        headers: { 'Content-Type': 'application/x-www-form-urlencoded;charset=UTF-8' },
                        body: form
                    }));
                    if (!resp.ok) throw new Error(I18N.saveError);

                    if (fAvatar.files.length > 0) {
                        const fd = new FormData();
                        fd.append('avatar', fAvatar.files[0]);
                        resp = await fetch('/profile/avatar', withCsrf({
                            method: 'POST',
                            body: fd
                        }));
                        if (!resp.ok) throw new Error(I18N.avatarError);
                    }

                    const modal = window.bootstrap?.Modal.getOrCreateInstance(modalEl);
                    if (modal) {
                        if (modalEl.contains(document.activeElement)) document.activeElement.blur();

                        modalEl.addEventListener('hidden.bs.modal', () => {
                            document.getElementById('editProfileBtn')?.focus();
                            location.reload();
                        }, { once: true });

                        modal.hide();
                    } else {
                        setTimeout(() => location.reload(), 0);
                    }
                } catch (e) {
                    console.error(e);
                    alert(e.message || I18N.saveError);
                } finally {
                    saveBtn.disabled = false; spinner.classList.add('d-none');
                }
            });

            document.querySelectorAll('.js-touch-resume').forEach(btn => {
                btn.addEventListener('click', async () => {
                    const r = await fetch('/api/resumes/' + btn.dataset.id + `/touch`, withCsrf({ method: 'PATCH' }));
                    if (r.ok) location.reload();
                });
            });
            document.querySelectorAll('.js-touch-vacancy').forEach(btn => {
                btn.addEventListener('click', async () => {
                    const r = await fetch('/api/vacancies/' + btn.dataset.id + `/touch`, withCsrf({ method: 'PATCH' }));
                    if (r.ok) location.reload();
                });
            });

            document.querySelectorAll('.js-publish-resume').forEach(sw => {
                sw.addEventListener('change', async () => {
                    const body = JSON.stringify({ active: sw.checked });
                    const r = await fetch('/api/resumes/' + sw.dataset.id + '/status', withCsrf({
                        method: 'PATCH',
                        headers: { 'Content-Type': 'application/json' },
                        body
                    }));
                    if (!r.ok) sw.checked = !sw.checked;
                });
            });
            document.querySelectorAll('.js-publish-vacancy').forEach(sw => {
                sw.addEventListener('change', async () => {
                    const body = JSON.stringify({ active: sw.checked });
                    const r = await fetch('/api/vacancies/' + sw.dataset.id + '/status', withCsrf({
                        method: 'PATCH',
                        headers: { 'Content-Type': 'application/json' },
                        body
                    }));
                    if (!r.ok) sw.checked = !sw.checked;
                });
            });
        });
    </script>

</@l.layout>
