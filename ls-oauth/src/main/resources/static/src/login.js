
const formTitle = document.getElementById('form-title');
const submitBtn = document.getElementById('submit-btn');
const toggleText = document.getElementById('toggle-text');
const toggleLink = document.getElementById('toggle-link');
const nameInput = document.getElementById('name');
const usernameInput = document.getElementById('username');
const emailInput = document.getElementById('email');
const passwordInput = document.getElementById('password');

let isLogin = true;

function updateForm() {
    if (isLogin) {
        formTitle.textContent = 'Login';
        submitBtn.textContent = 'Login';
        toggleText.textContent = "Don't have an account?";
        toggleLink.textContent = 'Register';
        nameInput.style.display = 'none';
        emailInput.style.display = 'none';
        usernameInput.required = true;
        emailInput.required = false;
        nameInput.required = false;
    } else {
        formTitle.textContent = 'Register';
        submitBtn.textContent = 'Register';
        toggleText.textContent = "Already have an account?";
        toggleLink.textContent = 'Login';
        nameInput.style.display = '';
        emailInput.style.display = '';
        usernameInput.required = true;
        emailInput.required = true;
        nameInput.required = true;
    }
}

async function register(passedName, passedUsername, passedEmail, passedPassword) {
    try {
        const response = await fetch("http://localhost:8080/ls-user/users/register", {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                name: passedName,
                username: passedUsername,
                email: passedEmail,
                password: passedPassword
            }),
        });

        if (!response.ok) {
            throw new Error("Failed to register");
        }
        // On successful registration, redirect to index.html
        window.location.href = "index.html";
    } catch (error) {
        alert(error.message);
    }
}

async function login(passedUsername, passedPassword) {

    const redirectUri = window.location.origin + "/index.html";

    const authUrl = "http://localhost:9000/auth/login";

    try {
        const response = await fetch(authUrl, {
            method: "POST",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify({
                username: passedUsername,
                password: passedPassword,
            }),
        });

        if (!response.ok) {
            throw new Error("Failed to log in");
        } else {
            TOKEN_KEY = await response.text();
            localStorage.setItem('token', TOKEN_KEY);
        }
        // On successful registration, redirect to index.html
        window.location.href = "index.html";
    } catch (error) {
        alert(error.message);
    }


}

toggleLink.addEventListener('click', (e) => {
    e.preventDefault();
    isLogin = !isLogin;
    updateForm();
});

document.getElementById('auth-form').addEventListener('submit', function(e) {
    e.preventDefault();

    if(isLogin){
        login(
            usernameInput.value,
            passwordInput.value
        );
    } else {
        register(
            nameInput.value,
            usernameInput.value,
            emailInput.value,
            passwordInput.value
        );
    }
});

updateForm();

