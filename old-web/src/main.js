const form = document.getElementById("shorten-form");
const urlInput = document.getElementById("url-input");
const resultDiv = document.getElementById("result");
const shortLink = document.getElementById("short-link");

form.addEventListener("submit", async (e) => {
  e.preventDefault();

  const url = urlInput.value;

  try {
    const response = await fetch("http://localhost:8080/ls-link/urls/create", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
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
