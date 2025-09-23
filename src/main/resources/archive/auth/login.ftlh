<#import "../layout.ftlh" as main>
<@main.layout>
    <section class="text-center text-lg-start">
        <div class="card m-3 shadow-lg border-0" style="background-color: #1e1e1e;">
            <div class="row g-0 d-flex align-items-center">


                <div class="col-lg-4 d-none d-lg-block">

                    <img src="../../static/images/default-avatar.png"
                         alt="Login visual"
                         class="rounded-circle border border-secondary shadow"
                         style="width: 100%; height: 100%; object-fit: cover; margin-left: 5%">
                </div>

                <div class="col-lg-8">
                    <div class="card-body py-5 px-md-5 text-start">

                        <h2 class="text-light mb-4">Добро пожаловать 👋</h2>
                        <p class="text-secondary mb-4">Введите свои данные, чтобы войти в систему.</p>

                        <form id="login-form" action="/login" method="post">
                            <#if _csrf??>
                                <input
                                        type="hidden"
                                        name="${(_csrf.parameterName)!'csrf-param-name'}"
                                        value="${(_csrf.token)!'csrf-token'}"/>

                            </#if>

                            <div class="mb-4">
                                <label for="form2Example1" class="form-label text-light">Email</label>
                                <input type="email" id="form2Example1" name="username"
                                       class="form-control bg-dark text-light border-secondary"
                                       placeholder="you@example.com" required/>
                            </div>

                            <div class="mb-4">
                                <label for="form2Example2" class="form-label text-light">Пароль</label>
                                <input type="password" id="form2Example2" name="password"
                                       class="form-control bg-dark text-light border-secondary"
                                       placeholder="Ваш пароль" required/>
                            </div>

                            <div class="d-grid mb-4">
                                <button type="submit" class="btn btn-primary btn-lg">
                                    <i class="bi bi-box-arrow-in-right me-1"></i> Войти
                                </button>
                            </div>

                            <div class="text-center">
                                <a href="/auth/register" class="text-decoration-none text-info">Нет аккаунта?
                                    Зарегистрируйтесь</a>
                            </div>

                            <a href="forgot-password">Забыли пароль?</a>

                        </form>
                    </div>
                </div>
            </div>
        </div>
    </section>
</@main.layout>
