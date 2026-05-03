// Shared script for login, register, and todos pages
const SERVER_URL = "http://localhost:8081";
function getToken() {
    return localStorage.getItem("token");
}

// Login page logic
function login() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch(`${SERVER_URL}/auth/login`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    })
    .then(async res => {
        const data = await res.json();
        if (!res.ok) throw new Error(data.message || "Login failed");
        return data;
    })
    .then(data => {
        localStorage.setItem("token", data.token);
        window.location.href = "todos.html";
    })
    .catch(err => alert(err.message));
}

// Register page logic
function register() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch(`${SERVER_URL}/auth/register`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({ email, password })
    })
    .then(res => {
        if (!res.ok) throw new Error("Registration failed");
        alert("Registered successfully!");
        window.location.href = "login.html";
    })
    .catch(err => alert(err.message));
}



// Todos page logic
function createTodoCard(todo) {
    const card = document.createElement("div");
    card.className = "todo-card";
    card.setAttribute("draggable", true);

    // checkbox
    const checkbox = document.createElement("input");
    checkbox.type = "checkbox";
    checkbox.checked = todo.isCompleted;
    checkbox.className = "todo-checkbox";

    checkbox.addEventListener("change", () => {
        updateTodoStatus({ ...todo, isCompleted: checkbox.checked });
    });

    // text
    const span = document.createElement("span");
    span.textContent = todo.title;

    // ✏️ edit on double click
    span.addEventListener("dblclick", () => {
        const input = document.createElement("input");
        input.type = "text";
        input.value = todo.title;

        card.replaceChild(input, span);

        input.focus();

        input.addEventListener("blur", () => {
            todo.title = input.value;
            updateTodoStatus(todo);
            span.textContent = input.value;
            card.replaceChild(span, input);
        });
    });

    // delete button
    const deleteBtn = document.createElement("button");
    deleteBtn.textContent = "X";

    deleteBtn.onclick = () => {
        card.classList.add("fade-out");
        setTimeout(() => deleteTodo(todo.id), 300);
    };

    card.appendChild(checkbox);
    card.appendChild(span);
    card.appendChild(deleteBtn);

    return card;
}

function loadTodos() {
    const token = getToken(); 
    if (!token) {
        alert("Please login first");
        window.location.href = "login.html";
        return;
    }

    fetch(`${SERVER_URL}/api/v1/todo`, {
        headers: {
           Authorization: `Bearer ${token}` 
        }
    })
    .then(res => {
        if (!res.ok) throw new Error("Failed to load todos");
        return res.json();
    })
    .then(todos => {
        const list = document.getElementById("todo-list");
        list.innerHTML = "";

        if (!todos || todos.length === 0) {
            list.innerHTML = "<p>No Todos yet</p>";
        } else {
            todos.forEach(todo => {
                list.appendChild(createTodoCard(todo));
            });
        }
    })
    .catch(() => {
        document.getElementById("todo-list").innerHTML =
            "<p style='color:red'>Error loading todos</p>";
    });
}
function addTodo() {
    const input = document.getElementById("new-todo");
    const title = input.value.trim();

    if (!title) {
        alert("Todo cannot be empty");
        return;
    }

    fetch(`${SERVER_URL}/api/v1/todo`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${getToken()}`
        },
        body: JSON.stringify({
            title: title,
            isCompleted: false   // description optional now
        })
    })
    .then(res => {
        if (!res.ok) throw new Error("Failed to add todo");
    })
    .then(() => {
        input.value = "";
        loadTodos();
    })
    .catch(err => alert(err.message));
}
function deleteTodo(id) {
    fetch(`${SERVER_URL}/api/v1/todo/${id}`, {
        method: "DELETE",
        headers: {
             Authorization: `Bearer ${getToken()}` 
        }
    })
    .then(() => loadTodos())
    .catch(err => alert(err.message));
}
function updateTodoStatus(todo) {
    fetch(`${SERVER_URL}/api/v1/todo`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${getToken()}`
        },
        body: JSON.stringify(todo)
    })
    .then(() => loadTodos())
    .catch(err => alert(err.message));
}

// Page-specific initializations
document.addEventListener("DOMContentLoaded", function () {
    if (document.getElementById("todo-list")) {
        loadTodos();
    }
});
let dragged;

document.addEventListener("dragstart", (e) => {
    dragged = e.target;
});

document.addEventListener("dragover", (e) => {
    e.preventDefault();
});

document.addEventListener("drop", (e) => {
    e.preventDefault();
    if (e.target.classList.contains("todo-card")) {
        e.target.parentNode.insertBefore(dragged, e.target);
    }
});
document.getElementById("new-todo").addEventListener("keypress", function(e) {
    if (e.key === "Enter") {
        addTodo();
    }
});