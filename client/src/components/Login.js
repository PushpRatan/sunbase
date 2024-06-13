import React, { useState } from "react";
import "../index.css";

function Login({ onLogin }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    const AUTH_API = "http://localhost:8080/auth/login";
    const response = await fetch(AUTH_API, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ email: username, password }),
    });
    if (response.ok) {
      const data = await response.json();
      onLogin(data.jwtToken);
    } else {
      console.error(`HTTP error, status: ${response.status}`);
    }
  };

  return (
    <section id="login">
      <form id="login-form" onSubmit={handleSubmit}>
        <h1>Login</h1>
        <input
          type="text"
          id="username"
          placeholder="Enter username"
          required
          value={username}
          onChange={(e) => setUsername(e.target.value)}
        />
        <input
          type="password"
          id="password"
          placeholder="Enter password"
          required
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button type="submit">Submit</button>
      </form>
    </section>
  );
}

export default Login;
