const form = document.getElementById("shorten-form");
const urlInput = document.getElementById("url-input");
const resultDiv = document.getElementById("result");
const shortLink = document.getElementById("short-link");
const token = localStorage.getItem("token");

async function loadLinks(payload) {

   const headers = {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
              };
  
      try {

        const response = await fetch(`http://localhost:8080/ls-link/urls/all/${payload.sub}`, {
          method: "GET",
          headers
        });

        if (!response.ok) {
          throw new Error("Failed to shorten link");
        }

        const data = await response.json();
        data.forEach(link => {
          addToTable(link);
        });

      } catch (error) {
        alert(error.message);
      }
}

async function deleteLink(link) {

  const headers = {
                "Content-Type": "application/json",
                "Authorization": `Bearer ${token}`
              };
  
  try {

    const response = await fetch(`http://localhost:8080/ls-link/urls/${link.accessKey}/delete`, {
      method: "DELETE",
      headers
    });


    const data = await response.json();
    if (data.success) {
      clearTable(); // Clear the table
      loadLinks(parseJwt(token));
    }
  
  } catch (error) {
    
  }
}

function addToTable(link) {
  const tbody = document.getElementById('links-table-body');
  const row = document.createElement('tr');
  row.innerHTML = `
    <td><a href="${link.redirect}" target="_blank">${link.redirect.replace('http://', '').replace('https://','')}</a></td>
    <td><a href="http://localhost:8080/${link.accessKey}" target="_blank">localhost:8080/${link.accessKey}</a></td>
    <td>${new Date(link.expiration).toLocaleDateString()}</td>
    <td>
      <button class="btn btn-sm btn-danger delete-btn" title="Delete">
        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-trash" viewBox="0 0 16 16">
          <path d="M5.5 5.5A.5.5 0 0 1 6 6v6a.5.5 0 0 1-1 0V6a.5.5 0 0 1 .5-.5zm2.5.5a.5.5 0 0 0-1 0v6a.5.5 0 0 0 1 0V6zm3 .5a.5.5 0 0 1 .5-.5.5.5 0 0 1 .5.5v6a.5.5 0 0 1-1 0V6z"/>
          <path fill-rule="evenodd" d="M14.5 3a1 1 0 0 1-1-1V1a1 1 0 0 0-1-1h-7a1 1 0 0 0-1 1v1a1 1 0 0 1-1 1H1v1h14V3h-1.5zM2.5 3V2a.5.5 0 0 1 .5-.5h7A.5.5 0 0 1 10.5 2v1h-8zM1 4v10a2 2 0 0 0 2 2h10a2 2 0 0 0 2-2V4H1zm2 10a1 1 0 0 1-1-1V5h12v8a1 1 0 0 1-1 1H3z"/>
        </svg>
      </button>
    </td>
  `;
  tbody.appendChild(row);
  // Add event listener to the delete button
  row.querySelector('.delete-btn').addEventListener('click', function() {
    deleteLink(link);
  });
}

function clearTable() {
  const tbody = document.getElementById('links-table-body');
  tbody.innerHTML = '';
}

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

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const url = urlInput.value;

  const payload = parseJwt(token);


    const headers = {
    "Content-Type": "application/json",
  };
  if (token) {
    headers["Authorization"] = `Bearer ${token}`;
  }

  try {
    const response = await fetch("http://localhost:8080/ls-link/urls/create", {
      method: "POST",
      headers,
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
    clearTable();
    loadLinks(parseJwt(token));
  } catch (error) {
    alert(error.message);
  }
});

document.addEventListener("DOMContentLoaded", async (e) => {

  const token = localStorage.getItem("token");
  const authButton = document.getElementById("auth-button");
  const usernameDisplay = document.getElementById("username-display");
  if (token) {
  
    // LOGOUT BTN LOGIC
    const payload = parseJwt(token);
  
    if (payload && payload.sub) {

      authButton.style.display = "none"; // Hide login/register button
  
      usernameDisplay.innerHTML = `
        <span id="username-text" style="color: #000; cursor:pointer;">Hello, ${payload.sub} <span style="font-size:0.7em;">&#9662;</span></span>
        <div id="user-dropdown" style="display:none; position:absolute; background:#fff; border:1px solid #ccc; right:0; z-index:100; min-width:120px; box-shadow:0 2px 8px rgba(0,0,0,0.1);">
          <button id="logout-btn" class="dropdown-item btn btn-link text-danger w-100 text-start" style="padding:8px 16px;">Log out</button>
        </div>
      `;
      
      usernameDisplay.style.display = "inline-block";
      usernameDisplay.style.position = "relative";
  
      const usernameText = document.getElementById("username-text");
      const userDropdown = document.getElementById("user-dropdown");
      const logoutBtn = document.getElementById("logout-btn");

      usernameText.addEventListener("click", (e) => {
        e.stopPropagation();
        userDropdown.style.display = userDropdown.style.display === "block" ? "none" : "block";
      });
      document.addEventListener("click", () => {
        userDropdown.style.display = "none";
      });
      // Prevent dropdown from closing when clicking inside
      userDropdown.addEventListener("click", (e) => {
        e.stopPropagation();
      });

      logoutBtn.addEventListener("click", () => {
        localStorage.removeItem("token");
        window.location.reload();
      });

      loadLinks(payload);
       
    }
  } 
});