const form = document.getElementById("shorten-form");
const urlInput = document.getElementById("url-input");
const resultDiv = document.getElementById("result");
const shortLink = document.getElementById("short-link");
const token = localStorage.getItem("token");

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const url = urlInput.value;

  try {
    const response = await fetch("http://localhost:8080/ls-link/urls/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
        "Authorization": `Bearer ${token}`, // Include token for authentication
      },
      body: JSON.stringify({ link: url }),
    });

    if (!response.ok) {
      throw new Error("Failed to shorten link");
    }

    const data = await response.json();
    const accessKey = data.accessKey;

    const shortenedUrl = `http://localhost:8080/${accessKey}`;
    shortLink.href = shortenedUrl;
    shortLink.textContent = shortenedUrl;
    resultDiv.style.display = "block";

    urlInput.value = ""; // Optional: clear the input
  } catch (error) {
    alert(error.message);
  }
});

function parseJwt(token) {
  try {
    const base64Url = token.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split('')
        .map(c => '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2))
        .join('')
    );
    return JSON.parse(jsonPayload);
  } catch (e) {
    return null;
  }
}

document.addEventListener("DOMContentLoaded", () => {
  const token = localStorage.getItem("token");
  const authButton = document.getElementById("auth-button");
  const usernameDisplay = document.getElementById("username-display");

  if (token) {
    const payload = parseJwt(token);
    if (payload && payload.sub) {
      authButton.style.display = "none"; // Hide login/register button
      usernameDisplay.textContent = `Hello, ${payload.sub}`;
      usernameDisplay.style.display = "inline-block"; // Show username
    }
  }
});
