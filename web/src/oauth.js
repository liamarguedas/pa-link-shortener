// Function to register a new user
async function registerUser({ name, username, email, password }) {
  // Hash the password using bcryptjs
  const saltRounds = 12;
  const salt = bcrypt.genSaltSync(saltRounds);
  const hashedPassword = bcrypt.hashSync(password, salt);

  const payload = {
    "name": name,
    "username": username,
    "email": email,
    "password": hashedPassword,
  };

  const response = await fetch('http://localhost:8080/ls-user/users/register', {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(payload),
  });

  if (!response.ok) {
    throw new Error('Registration failed');
  }
  return response.json();
}

// Usage example (replace with actual form handling logic):
// registerUser({ name: 'liam', username: 'lianotest', email: 'liano@mail.com', password: 'plaintextpassword' })
//   .then(data => console.log('Registered:', data))
//   .catch(err => console.error(err));
