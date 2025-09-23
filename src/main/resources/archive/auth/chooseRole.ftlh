<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Вход</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
    <link rel="stylesheet" href="../../static/styles/chooseRole.css">
</head>
<body>

<button class="back-button" aria-label="Назад" onclick="history.back()">
    <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-arrow-left-short" viewBox="0 0 16 16">
        <path fill-rule="evenodd" d="M12 8a.5.5 0 0 1-.5.5H5.707l2.147 2.146a.5.5 0 0 1-.708.708l-3-3a.5.5 0 0 1 0-.708l3-3a.5.5 0 1 1 .708.708L5.707 7.5H11.5a.5.5 0 0 1 .5.5"/>
    </svg>
</button>

<div class="container-custom bg-dark bg-opacity-75 rounded-3 shadow-lg p-5">
    <div class="search-icon" aria-hidden="true">
        <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-search" viewBox="0 0 16 16">
            <path d="M11.742 10.344a6.5 6.5 0 1 0-1.397 1.398h-.001q.044.06.098.115l3.85 3.85a1 1 0 0 0 1.415-1.414l-3.85-3.85a1 1 0 0 0-.115-.1zM12 6.5a5.5 5.5 0 1 1-11 0 5.5 5.5 0 0 1 11 0"/>
        </svg>
    </div>

    <h2 class="text-center fw-bold mb-4">Регистрация</h2>

    <form method="get" id="roleForm">
        <div class="mb-3">
            <input class="form-check-input" type="radio" name="role" id="applicant" value="applicant" checked />
            <label class="option-card form-check-label" for="applicant">
                <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-person-circle" viewBox="0 0 16 16">
                    <path d="M11 6a3 3 0 1 1-6 0 3 3 0 0 1 6 0"/>
                    <path fill-rule="evenodd" d="M0 8a8 8 0 1 1 16 0A8 8 0 0 1 0 8m8-7a7 7 0 0 0-5.468 11.37C3.242 11.226 4.805 10 8 10s4.757 1.225 5.468 2.37A7 7 0 0 0 8 1"/>
                </svg>
                <div>
                    <strong>Я ищу работу</strong><br />
                    <small class="text-secondary">Профиль соискателя</small>
                </div>
            </label>
        </div>

        <div class="mb-4">
            <input class="form-check-input" type="radio" name="role" id="employer" value="employer" />
            <label class="option-card form-check-label" for="employer">
                <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" class="bi bi-buildings" viewBox="0 0 16 16">
                    <path d="M14.763.075A.5.5 0 0 1 15 .5v15a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5V14h-1v1.5a.5.5 0 0 1-.5.5h-9a.5.5 0 0 1-.5-.5V10a.5.5 0 0 1 .342-.474L6 7.64V4.5a.5.5 0 0 1 .276-.447l8-4a.5.5 0 0 1 .487.022M6 8.694 1 10.36V15h5zM7 15h2v-1.5a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 .5.5V15h2V1.309l-7 3.5z"/>
                    <path d="M2 11h1v1H2zm2 0h1v1H4zm-2 2h1v1H2zm2 0h1v1H4zm4-4h1v1H8zm2 0h1v1h-1zm-2 2h1v1H8zm2 0h1v1h-1zm2-2h1v1h-1zm0 2h1v1h-1zM8 7h1v1H8zm2 0h1v1h-1zm2 0h1v1h-1zM8 5h1v1H8zm2 0h1v1h-1zm2 0h1v1h-1zm0-2h1v1h-1z"/>
                </svg>
                <div>
                    <strong>Я ищу сотрудников</strong><br />
                    <small class="text-secondary">Профиль работодателя</small>
                </div>
            </label>
        </div>

        <div class="d-grid gap-2">
            <button formaction="/auth/register" class="btn btn-outline-primary fw-bold" type="submit">
                Зарегистрироваться
            </button>
        </div>
    </form>
</div>
</body>
</html>
