let currentAction = 'create';

const userMessage = document.getElementById('userMessage');

async function loadUserMail() {
  try {
    userMessage.textContent = '';
    const response = await fetch('http://localhost:8080/user/get-all');
    const emails = await response.json();
    const select = document.getElementById('emailSelect');

    select.innerHTML = '<option value="">-- Select E-Mail --</option>';
    emails.forEach(user => {
      const option = document.createElement('option');
      option.value = user.email;
      option.textContent = user.email;
      select.appendChild(option);
    });
  } catch (error) {
    userMessage.style.color = 'red';
    userMessage.textContent = 'Failed to load user email: ' + error.message;
  }
}

function showForm(type) {
  currentAction = type;
  document.getElementById('formTitle').textContent = type.charAt(0).toUpperCase() + type.slice(1) + ' Email';

  document.querySelectorAll('.button-group button').forEach(btn => btn.classList.remove('active'));
  document.getElementById(`btn-${type}`).classList.add('active');

  const emailInput = document.getElementById('emailInput');
  const emailSelect = document.getElementById('emailSelect');
  const status = document.getElementById('status');
  const fields = ['name', 'lastname', 'residenceAddress', 'residenceCity'];

  if (type === 'create') {
    emailInput.style.display = 'block';
    emailInput.disabled = false;
    emailInput.required = true;

    emailSelect.style.display = 'none';
    emailSelect.disabled = true;
    emailSelect.required = false;

    status.style.display = 'none';
    status.disabled = true;
    status.required = false;

    fields.forEach(id => {
      const el = document.getElementById(id);
      el.disabled = false;
      el.required = true;
      el.value = '';
    });

  } else if (type === 'status') {
    emailInput.style.display = 'none';
    emailInput.disabled = true;
    emailInput.required = false;

    emailSelect.style.display = 'block';
    emailSelect.disabled = false;
    emailSelect.required = true;

    status.style.display = 'block';
    status.disabled = false;
    status.required = true;

    fields.forEach(id => {
      const el = document.getElementById(id);
      el.disabled = true;
      el.required = false;
      el.value = '';
    });

  } else {
    // update o delete
    emailInput.style.display = 'none';
    emailInput.disabled = true;
    emailInput.required = false;

    emailSelect.style.display = 'block';
    emailSelect.disabled = false;
    emailSelect.required = true;

    status.style.display = 'none';
    status.disabled = true;
    status.required = false;

    const enableFields = (type === 'update');
    fields.forEach(id => {
      const el = document.getElementById(id);
      el.disabled = !enableFields;
      el.required = enableFields;
      el.value = '';
    });

    loadUserMail();
  }
}

document.getElementById('emailSelect').addEventListener('change', async function () {
  const selectedMail = this.value;
  userMessage.textContent = '';
  try {
    const response = await fetch(`http://localhost:8080/user/get-by-email?email=${encodeURIComponent(selectedMail)}`);
    const user = await response.json();

    document.getElementById('name').value = user.name || '';
    document.getElementById('lastname').value = user.lastname || '';
    document.getElementById('residenceAddress').value = user.residenceAddress || '';
    document.getElementById('residenceCity').value = user.residenceCity || '';
    document.getElementById('status').value = user.status || '';
  } catch (error) {
    userMessage.style.color = 'red';
    userMessage.textContent = 'Failed to load user details: ' + error.message;
  }
});

document.getElementById('userForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  userMessage.textContent = '';

  const form = e.target;
  const data = {};
  [...form.elements].forEach(el => {
    if (el.name && !el.disabled) {
      data[el.name] = el.value;
    }
  });

  let url = '';
  let method = '';
  const options = {
    headers: {
      'Content-Type': 'application/json'
    }
  };

  switch (currentAction) {
    case 'create':
      url = 'http://localhost:8080/user/add';
      method = 'POST';
      options.body = JSON.stringify(data);
      break;

    case 'update':
      url = 'http://localhost:8080/user/update';
      method = 'PUT';
      options.body = JSON.stringify(data);
      break;

    case 'status':
      url = `http://localhost:8080/user/status?email=${encodeURIComponent(data.email)}&status=${encodeURIComponent(data.status)}`;
      method = 'PUT';
      break;

    case 'delete':
      if (!data.email) {
        alert('E-Mail is required for deletion!');
        return;
      }
      url = `http://localhost:8080/user/delete?email=${encodeURIComponent(data.email)}`;
      method = 'DELETE';
      break;

    default:
      userMessage.style.color = 'red';
      userMessage.textContent = 'Unknown action: ';
      return;
  }

  try {
    const response = await fetch(url, {
      method,
      headers: options.headers,
      body: options.body
    });

    if (!response.ok) {
      const errorText = await response.text();
      userMessage.style.color = 'red';
      userMessage.textContent = `Error: ${errorText}`;
      return;
    }

    userMessage.style.color = 'green';
    userMessage.textContent = 'Operation successful!';
    form.reset();
    showForm(currentAction);
  } catch (error) {
    userMessage.style.color = 'red';
    userMessage.textContent = 'Network error: ' + error.message;
  }
});

showForm('create');
