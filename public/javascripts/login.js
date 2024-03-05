// Handle login form

// Handle when user enter password

const handlePassword = function() {
    const password = document.getElementById('password-ip');
    const showPwEye = document.querySelector('.fa-eye');
    const hidePwEye = document.querySelector('.fa-eye-slash');

    document.getElementById('password-ip').oninput = function(event) {
        showPwEye.style.display = 'inline-block';
        if (event.target.value === '') {
            showPwEye.style.display = 'none';
            hidePwEye.style.display = 'none';
            password.type = 'password';
        }
    };

    document.querySelector('.fa-eye').addEventListener('click', function() {
        if (password.type === 'password') {
            password.type = 'text';
            showPwEye.style.display = 'none';
            hidePwEye.style.display = 'inline-block';
        }
    });

    document.querySelector('.fa-eye-slash').addEventListener('click', function() {
        if (password.type === 'text') {
            password.type = 'password';
            showPwEye.style.display = 'inline-block';
            hidePwEye.style.display = 'none';
        }
    });
};

handlePassword();

// Handle the login button
document.getElementById('btn-login').addEventListener('click', function(event) {
    event.preventDefault();
    let username, password;
    username = document.getElementById('username-ip').value;
    password = document.getElementById('password-ip').value;
    if (username !== '' && password !== '') {
        if (username === 'liliana' && password === '123') {
            alert('Login successfully');
        } else {
            alert('Wrong account or password');
        }
    } else {
        alert('Please enter complete information!');
    }
});