let logInButton = document.querySelector('#logInButton');

logInButton.addEventListener('click', () => {
    handleLogIn();
});

async function handleLogIn() {
    const username = document.querySelector('#usernameInput').value;
    const password = document.querySelector('#passwordInput').value;

    const response = await fetch('http://localhost:3000/login', {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({
            username: username,
            password: password,
        })
    });

    if (response.ok) {
        const data = await response.json();
        console.log(data);
        localStorage.setItem('userData', JSON.stringify(data));
        window.location.href = '/user_page.html';
    } else {
        alert("Username or password wrong!!");
    }
}
