let originalData = [];
let currentEntity = 'user';

const entityEndpoints = {
    user: '/user/get-all?status=ACTIVE',
    product: '/product/get-all',
    order: '/order/get-all'
};

const entitySelect = document.getElementById('entitySelect');
const searchInput = document.getElementById('searchInput');
const tableHeader = document.getElementById('tableHeader');
const tableBody = document.getElementById('tableBody');
const noDataDiv = document.getElementById('noData');

const userSelect = document.getElementById('userSelect');
const productsContainer = document.getElementById('productsContainer');
const addProductBtn = document.getElementById('addProductBtn');
const submitOrderBtn = document.getElementById('submitOrderBtn');
const orderCodeInput = document.getElementById('orderCode');
const orderMessage = document.getElementById('orderMessage');
const orderMessage2 = document.getElementById('orderMessage2');

loadData(currentEntity);

entitySelect.addEventListener('change', () => {
    currentEntity = entitySelect.value;
    searchInput.value = '';
    loadData(currentEntity);
});

searchInput.addEventListener('input', () => {
    const value = searchInput.value.toLowerCase();
    const filtered = originalData.filter(row =>
        Object.values(row).some(val =>
            String(val).toLowerCase().includes(value)
        )
    );
    buildTable(filtered);
});

document.getElementById('updateBtn').addEventListener('click', () => {
  const selectedEntity = document.getElementById('entitySelect').value;
  loadData(selectedEntity);
});

function loadData(entity) {
    loadProducts();
    fetch(entityEndpoints[entity])
        .then(res => res.json())
        .then(data => {
            if (!Array.isArray(data)) data = [];
            originalData = data;
            buildTable(data, entity);
            if (entity === 'user') {
                populateUserSelect(data);
            }
        })
        .catch(err => {
            console.error('Error loading data:', err);
            originalData = [];
            buildTable([]);
        });
}

function buildTable(data, entity, orderColumn) {
    tableHeader.innerHTML = '';
    tableBody.innerHTML = '';

    if (data.length === 0) {
        noDataDiv.style.display = 'block';
        return;
    } else {
        noDataDiv.style.display = 'none';
    }

    let keys;
    if (entity === 'order' || orderColumn === true) {
        keys = ['id', 'orderCode', 'Email', 'Status', 'Order Date', 'Gesture'];
    } else {
        keys = Object.keys(data[0]);
    }

    keys.forEach(key => {
        const th = document.createElement('th');
        th.textContent = key;
        th.style.cursor = 'pointer';
        th.addEventListener('click', () => sortByColumn(key));
        tableHeader.appendChild(th);
    });

    data.forEach(item => {
        const row = document.createElement('tr');
        keys.forEach(key => {
            const td = document.createElement('td');
            let value = '';

            if (entity === 'order' || orderColumn === true) {
                switch (key) {
                    case 'orderCode':
                        const link = document.createElement('a');
                        link.href = '#';
                        link.textContent = item.orderCode || '';
                        link.addEventListener('click', (e) => {
                            e.preventDefault();
                            toggleProduceOrders(row, item.productResource);
                        });
                        td.appendChild(link);
                        break;
                    case 'Email':
                        value = item.userResource?.email || '';
                        break;
                    case 'Status':
                        value = item.status || '';
                        break;
                    case 'Order Date':
                        value = item.orderDate || '';
                        break;
                    case 'Gesture':
                        const link2 = document.createElement('a');
                        link2.href = '#';
                        link2.textContent = 'DELETE';
                        link2.style.cursor = 'pointer';
                        link2.style.color = '#d11a2a';
                        link2.style.textDecoration = 'underline';
                        link2.title = 'Delete Order';
                        link2.disabled = item.status == 'CANCELLED' || item.status == 'PAID'

                        link2.addEventListener('click', (e) => {
                            e.preventDefault();
                            toggleDeleteOrder(row, item.orderCode);
                        });

                        td.appendChild(link2);
                        break;
                    default:
                        value = item[key] !== undefined ? item[key] : '';
                        if (value && key !== 'orderCode') {
                            td.textContent = value;
                        }
                }
            } else {
                value = item[key] !== undefined ? item[key] : '';
                td.textContent = value;
            }

            if (key !== 'orderCode' && key !== 'Gesture') {
                td.textContent = value;
            }

            row.appendChild(td);
        });

        tableBody.appendChild(row);
    });
}

function toggleDeleteOrder(orderRow, order) {
    fetch(`http://localhost:8080/order/cancel?orderCode=${encodeURIComponent(order)}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json'
        },
    })
    .then(async res => {
        const data = await res.json();
        if (!res.ok && data.status !== 200) {
            throw new Error(data.message || `Error ${res.status}`);
        }
    })
    .then(data => {
        orderMessage2.style.color = 'green';
        orderMessage2.textContent = 'Order Deleted successfully!';
        productsContainer.innerHTML = '';
        loadData('order')
    })
    .catch(err => {
        orderMessage2.style.color = 'red';
        orderMessage2.textContent = `Failed to create order: ${err.message}`;
    });
}

function toggleProduceOrders(orderRow, produceOrders) {
    const nextRow = orderRow.nextElementSibling;
    if (nextRow && nextRow.classList.contains('produce-orders-row')) {
        nextRow.remove();
        return;
    }

    const detailRow = document.createElement('tr');
    detailRow.classList.add('produce-orders-row');

    const detailCell = document.createElement('td');
    detailCell.colSpan = orderRow.children.length;

    const innerTable = document.createElement('table');
    innerTable.style.width = '100%';
    innerTable.style.borderCollapse = 'collapse';
    innerTable.style.marginTop = '10px';

    if (!produceOrders || produceOrders.length === 0) {
        detailCell.textContent = 'No produce available';
    } else {
        const innerThead = document.createElement('thead');
        const innerHeaderRow = document.createElement('tr');

        const keys = Object.keys(produceOrders[0]);
        keys.forEach(key => {
            const th = document.createElement('th');
            th.textContent = key;
            th.style.border = '1px solid #ddd';
            th.style.padding = '4px';
            innerHeaderRow.appendChild(th);
        });
        innerThead.appendChild(innerHeaderRow);
        innerTable.appendChild(innerThead);

        const innerTbody = document.createElement('tbody');
        produceOrders.forEach(po => {
            const poRow = document.createElement('tr');
            keys.forEach(k => {
                const td = document.createElement('td');
                td.textContent = po[k] !== undefined ? po[k] : '';
                td.style.border = '1px solid #ddd';
                td.style.padding = '4px';
                poRow.appendChild(td);
            });
            innerTbody.appendChild(poRow);
        });
        innerTable.appendChild(innerTbody);
        detailCell.appendChild(innerTable);
    }

    detailRow.appendChild(detailCell);
    orderRow.parentNode.insertBefore(detailRow, orderRow.nextSibling);
}

function sortByColumn(column) {
    const sorted = [...originalData].sort((a, b) => {
        if (typeof a[column] === 'number') return a[column] - b[column];
        return String(a[column]).localeCompare(String(b[column]));
    });
    buildTable(sorted, null, true);
}

function populateUserSelect(users) {
    userSelect.innerHTML = '';
    if (users.length === 0) {
        userSelect.disabled = true;
        const option = document.createElement('option');
        option.text = 'No users available';
        userSelect.add(option);
        return;
    }
    userSelect.disabled = false;
    users.forEach(user => {
        const option = document.createElement('option');
        option.value = JSON.stringify(user);
        option.text = user.name;
        userSelect.add(option);
    });
}

let productsList = [];

function loadProducts() {
    fetch('/product/get-all')
        .then(res => {
            if (!res.ok) throw new Error('Failed to load products');
            return res.json();
        })
        .then(data => {
            productsList = data;
        })
        .catch(err => {
            console.error('Error loading products:', err);
        });
}

function createProductEntry() {
    loadProducts();

    const productDiv = document.createElement('div');
    productDiv.classList.add('product-entry');

    const nameLabel = document.createElement('label');
    nameLabel.textContent = 'Product Name';
    const nameSelect = document.createElement('select');

    const opt = document.createElement('option');
    opt.value = '';
    opt.text = 'Select Product';
    nameSelect.add(opt);

    productsList.forEach(cat => {
        const opt = document.createElement('option');
        opt.value = cat.id;
        opt.text = cat.name;
        nameSelect.add(opt);
    });

    const priceLabel = document.createElement('label');
    priceLabel.textContent = 'Price';
    const priceInput = document.createElement('input');
    priceInput.type = 'number';
    priceInput.placeholder = 'Price';
    priceInput.min = 0;
    priceInput.step = '0.01';
    priceInput.disabled = true;
    priceInput.required = true;
    priceInput.readOnly = true;

    const quantityLabel = document.createElement('label');
    quantityLabel.textContent = 'Quantity';
    const quantityInput = document.createElement('input');
    quantityInput.type = 'number';
    quantityInput.placeholder = 'Quantity';
    quantityInput.min = 1;
    quantityInput.step = '1';
    quantityInput.required = true;
    quantityInput.value = 1;


    const categoryLabel = document.createElement('label');
    categoryLabel.textContent = 'Category';
    const categoryInput = document.createElement('input');
    categoryInput.type = 'text';
    categoryInput.placeholder = 'Category';
    categoryInput.required = true;
    categoryInput.disabled = true;
    categoryInput.readOnly = true;

    nameSelect.addEventListener('change', (event) => {
            const selectedId = event.target.value;

            const selectedProduct = productsList.find(prod => prod.id.toString() === selectedId);

            if (selectedProduct) {
                priceInput.value = selectedProduct.price.toFixed(2);
                categoryInput.value = selectedProduct.category;
            } else {
                priceInput.value = '';
                categoryInput.value = '';
            }
    });

    const removeBtn = document.createElement('button');
    removeBtn.id = 'removeBtn';
    removeBtn.type = 'button';
    removeBtn.textContent = 'Remove Product';
    removeBtn.addEventListener('click', () => {
        productsContainer.removeChild(productDiv);
    });

    productDiv.appendChild(nameLabel);
    productDiv.appendChild(nameSelect);
    productDiv.appendChild(priceLabel);
    productDiv.appendChild(priceInput);
    productDiv.appendChild(quantityLabel);
    productDiv.appendChild(quantityInput);
    productDiv.appendChild(categoryLabel);
    productDiv.appendChild(categoryInput);
    productDiv.appendChild(removeBtn);

    productsContainer.appendChild(productDiv);
}

addProductBtn.addEventListener('click', () => {
    createProductEntry();
});

submitOrderBtn.addEventListener('click', () => {
    submitOrder();
});

function submitOrder() {
    orderMessage.textContent = '';

    const selectedUserJson = userSelect.value;
    if (!selectedUserJson) {
        orderMessage.style.color = 'red';
        orderMessage.textContent = 'Select a user';
        return;
    }
    const userResource = JSON.parse(selectedUserJson);

    const productEntries = productsContainer.querySelectorAll('.product-entry');
    if (productEntries.length === 0) {
        orderMessage.style.color = 'red';
        orderMessage.textContent = 'Add at least one product';
        return;
    }

    const productResource = [];
    for (const entry of productEntries) {
        const inputsText = entry.querySelectorAll('input[type="text"]');
        const inputsNumber = entry.querySelectorAll('input[type="number"]');

        const nameSelect = entry.querySelector('select');
        const id = nameSelect.options[nameSelect.selectedIndex].value;
        const name = nameSelect.options[nameSelect.selectedIndex].text.trim();

        const price = parseFloat(inputsNumber[0].value);
        const quantity = parseInt(inputsNumber[1].value, 10);

        const productCode = productsList.find(prod => prod.id.toString() === id).productCode;

        if (!name || isNaN(price) || price < 0 || isNaN(quantity) || quantity < 1) {
            orderMessage.style.color = 'red';
            orderMessage.textContent = 'Please fill all product fields correctly.';
            return;
        }

        productResource.push({
            name,
            price,
            quantity,
            productCode,
            unitPrice: price,
            total: (price * quantity).toFixed(2)
        });
    }

    const payload = {
        userResource,
        productResource,
        status: "CREATED"
    };

    fetch('/order/add', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
    .then(async res => {
        const data = await res.json();
        if (!res.ok && data.status !== 200) {
            throw new Error(data.message || `Error ${res.status}`);
        }
    })
    .then(data => {
        orderMessage.style.color = 'green';
        orderMessage.textContent = 'Order created successfully!';
        productsContainer.innerHTML = '';
    })
    .catch(err => {
        orderMessage.style.color = 'red';
        orderMessage.textContent = `Failed to create order: ${err.message}`;
    });
}


