let currentAction = 'create';

const productMessage = document.getElementById('productMessage');

async function loadProductCodes() {
  try {
    productMessage.textContent = '';

    const response = await fetch('http://localhost:8080/product/get-all');
    const products = await response.json();
    const select = document.getElementById('productCodeSelect');

    select.innerHTML = '<option value="">-- Select Product Code --</option>';
    products.forEach(product => {
      const option = document.createElement('option');
      option.value = product.productCode;
      option.textContent = product.productCode;
      select.appendChild(option);
    });
  } catch (error) {
    productMessage.style.color = 'red';
    productMessage.textContent = 'Failed to load product codes: ' + error.message;
  }
}

function showForm(type) {
  currentAction = type;
  document.getElementById('formTitle').textContent = type.charAt(0).toUpperCase() + type.slice(1) + ' Product';

  // Evidenzia il bottone cliccato
  document.querySelectorAll('.button-group button').forEach(btn => btn.classList.remove('active'));
  document.getElementById(`btn-${type}`).classList.add('active');

  const productCodeInput = document.getElementById('productCodeInput');
  const productCodeSelect = document.getElementById('productCodeSelect');
  const fields = ['name', 'description', 'price', 'supply', 'category'];

  if (type === 'create') {
    productCodeInput.style.display = 'block';
    productCodeInput.disabled = false;
    productCodeInput.required = true;

    productCodeSelect.style.display = 'none';
    productCodeSelect.disabled = true;
    productCodeSelect.required = false;

    fields.forEach(id => {
      const el = document.getElementById(id);
      el.disabled = false;
      el.required = true;
      el.value = '';
    });

  } else {
    // update o delete
    productCodeInput.style.display = 'none';
    productCodeInput.disabled = true;
    productCodeInput.required = false;

    productCodeSelect.style.display = 'block';
    productCodeSelect.disabled = false;
    productCodeSelect.required = true;

    // Campi modificabili solo per update
    const enableFields = (type === 'update');
    fields.forEach(id => {
      const el = document.getElementById(id);
      el.disabled = !enableFields;
      el.required = enableFields;
      el.value = '';
    });

    loadProductCodes();
  }
}

document.getElementById('productCodeSelect').addEventListener('change', async function () {
  productMessage.textContent = '';
  const selectedCode = this.value;

  try {
    const response = await fetch(`http://localhost:8080/product/get-by-code?productCode=${encodeURIComponent(selectedCode)}`);
    const product = await response.json();

    document.getElementById('name').value = product.name || '';
    document.getElementById('description').value = product.description || '';
    document.getElementById('price').value = product.price || '';
    document.getElementById('supply').value = product.supply || '';
    document.getElementById('category').value = product.category || '';
  } catch (error) {
    productMessage.style.color = 'red';
    productMessage.textContent = 'Failed to load product details: ' + error.message;
  }
});

document.getElementById('productForm').addEventListener('submit', async (e) => {
  e.preventDefault();
  productMessage.textContent = '';

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
      url = 'http://localhost:8080/product/add';
      method = 'POST';
      options.body = JSON.stringify(data);
      break;

    case 'update':
      url = 'http://localhost:8080/product/update';
      method = 'PUT';
      options.body = JSON.stringify(data);
      break;

    case 'delete':
      if (!data.productCode) {
        productMessage.style.color = 'red';
        productMessage.textContent = 'Product Code is required for deletion';
        return;
      }
      url = `http://localhost:8080/product/delete?productCode=${encodeURIComponent(data.productCode)}`;
      method = 'DELETE';
      break;

    default:
      productMessage.style.color = 'red';
      productMessage.textContent = 'Unknown action';
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
      alert(`Error: ${errorText}`);
      return;
    }

    productMessage.style.color = 'green';
    productMessage.textContent = 'Operation successful!';
    form.reset();
    showForm(currentAction);
  } catch (error) {
    productMessage.style.color = 'red';
    productMessage.textContent = 'Network error: ' + error.message;
  }
});

showForm('create');
