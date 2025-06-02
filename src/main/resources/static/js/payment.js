let originalData = [];

const searchInput = document.getElementById('searchInput');
const paymentContainer = document.getElementById('paymentContainer');
const paymentMessage = document.getElementById('paymentMessage');

loadData();

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
	loadData();
});

function loadData() {
	fetch('/payment-order/get-all')
		.then(res => res.json())
		.then(data => {
			if (!Array.isArray(data)) data = [];
			originalData = data;
			buildTable(data);
		})
		.catch(err => {
			console.error('Error loading data:', err);
			originalData = [];
			buildTable([]);
		});
}

function buildTable(data) {
	const tableBody = document.querySelector('#entityTable tbody');
	tableBody.innerHTML = '';

	data.forEach(order => {
		const tr = document.createElement('tr');
		tr.id = `row-${order.orderCode}`;

		tr.innerHTML = `
      <td>${order.id}</td>
      <td>
        <a href="#" onclick="toggleProduceOrders(this.parentElement.parentElement, '${order.orderCode}')">
          ${order.orderCode}
        </a>
      </td>
      <td><b>${order.status}</b></td>
      <td>${order.debit}</td>
      <td>${order.orderDate}</td>
      <td>
        <button id="payBtn-${order.orderCode}"
                onclick="openPayPopup('${order.orderCode}', '${order.status}')"
                ${order.status !== 'PENDING_PAYMENT' && order.status !== 'RATEIZED' ? 'disabled style="opacity: 0.5; cursor: not-allowed;"' : ''}>
          ${order.status === 'PENDING_PAYMENT' ? 'PAY' : order.status === 'RATEIZED' ? 'Pay Installments' : order.status}
        </button>
        <div id="popup-overlay-${order.orderCode}" class="popup-overlay" style="display:none;">
          <div class="popup">
            <h3 style='text-align: center;'>Payment order ${order.orderCode}</h3>
            <span id="warning-${order.orderCode}" style="color: red; font-size: 12px; display: none;">
              The amount is higher than the amount to be paid.
            </span>
            <br/>
            <div id="payment-options-${order.orderCode}" style="margin-top: 10px; text-align: left;">
              <!-- Payment options will be inserted here -->
            </div>
            <br/><br/>
            <button onclick="confirmPayment('${order.orderCode}', '${order.status}')">Confirm</button>
            <button onclick="closePopup('${order.orderCode}')">Close</button>
          </div>
        </div>
      </td>
    `;
		tableBody.appendChild(tr);
	});
}

async function toggleProduceOrders(orderRow, orderCode) {
	const nextRow = orderRow.nextElementSibling;
	if (nextRow && nextRow.classList.contains('produce-orders-row')) {
		nextRow.remove();
		return;
	}

	let produceOrders = [];
	try {
		const res = await fetch(`http://localhost:8080/payment-order/get-by-code?orderCode=${encodeURIComponent(orderCode)}`, {
			method: 'GET',
			headers: {
				'Content-Type': 'application/json'
			},
		});
		if (!res.ok) {
			throw new Error(`HTTP error! status: ${res.status}`);
		}
		produceOrders = await res.json();
	} catch (err) {
		alert(`Errore nel caricamento delle payment installments: ${err.message}`);
		return;
	}

	const detailRow = document.createElement('tr');
	detailRow.classList.add('produce-orders-row');

	const detailCell = document.createElement('td');
	detailCell.colSpan = orderRow.children.length;

	if (!produceOrders || produceOrders.length === 0) {
		detailCell.textContent = 'No produce available';
		detailRow.appendChild(detailCell);
		orderRow.parentNode.insertBefore(detailRow, orderRow.nextSibling);
		return;
	}

	const innerTable = document.createElement('table');
	innerTable.style.width = '100%';
	innerTable.style.borderCollapse = 'collapse';
	innerTable.style.marginTop = '10px';

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

	detailRow.appendChild(detailCell);
	orderRow.parentNode.insertBefore(detailRow, orderRow.nextSibling);
}

function openPayPopup(orderCode, status) {
	const overlay = document.getElementById(`popup-overlay-${orderCode}`);
	const paymentOptions = document.getElementById(`payment-options-${orderCode}`);
	paymentOptions.innerHTML = ''; // Clear previous options

	if (status === 'PENDING_PAYMENT') {
		paymentOptions.innerHTML = `
      <label style="display: flex; justify-content: flex-start; align-items: center; margin-bottom: 5px;">
        <input type="radio" name="payment-${orderCode}" value="one" style="margin-right: 8px;" checked/>
        Pay in One
      </label>
      <label style="display: flex; justify-content: flex-start; align-items: center;">
        <input type="radio" name="payment-${orderCode}" value="installments" style="margin-right: 8px;" />
        Do you want to pay in 12 installments?
      </label>
    `;
	} else if (status === 'RATEIZED') {
		paymentOptions.innerHTML = `
      <label style="display: flex; justify-content: flex-start; align-items: center;" for="installmentCount-${orderCode}" >Number of installments to pay:</label>
      <input type="number" id="installmentCount-${orderCode}" name="installmentCount-${orderCode}" min="1" style="margin-left: 10px;" />
    `;
	}

	overlay.style.display = 'flex';
}

function closePopup(orderCode) {
	document.getElementById(`popup-overlay-${orderCode}`).style.display = 'none';
}

function confirmPayment(orderCode, status) {
	closePopup(orderCode);

	if (status === 'PENDING_PAYMENT') {
		const selectedPayment = document.querySelector(`#popup-overlay-${orderCode} input[name="payment-${orderCode}"]:checked`);
		const isInstallments = selectedPayment.value === 'installments';

		fetch(`http://localhost:8080/payment-order/pay?orderCode=${encodeURIComponent(orderCode)}&isInstallments=${encodeURIComponent(isInstallments)}`, {
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
				paymentMessage.style.color = 'green';
				paymentMessage.textContent = `Paid order with code: ${orderCode}`;
			})
			.catch(err => {
				paymentMessage.style.color = 'red';
				paymentMessage.textContent = `Failed to pay order: ${err.message}`;
			});
	} else if (status === 'RATEIZED') {
		const installmentInput = document.getElementById(`installmentCount-${orderCode}`);
		const numberOfInstallments = parseInt(installmentInput.value, 10);

		if (isNaN(numberOfInstallments) || numberOfInstallments <= 0) {
			paymentMessage.style.color = 'red';
			paymentMessage.textContent = 'Please enter a valid number of installments.';
			return;
		}

		fetch(`http://localhost:8080/payment-order/pay-rate?orderCode=${encodeURIComponent(orderCode)}&number=${encodeURIComponent(numberOfInstallments)}`, {
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
				paymentMessage.style.color = 'green';
				paymentMessage.textContent = `Paid ${numberOfInstallments} installments for order with code: ${orderCode}`;
			})
			.catch(err => {
				paymentMessage.style.color = 'red';
				paymentMessage.textContent = `Failed to pay installments: ${err.message}`;
			});
	}
}