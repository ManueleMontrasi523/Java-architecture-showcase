let originalData = [];


const searchInput = document.getElementById('searchInput');
const paymentContainer = document.getElementById('paymentContainer');
const submitPaymentBtn = document.getElementById('submitPaymentBtn');
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
            <td>${order.orderCode}</td>
            <td><b>${order.status}</b></td>
            <td>${order.debit}</td>
            <td>${order.orderDate}</td>
            <td>
                <button id="payBtn-${order.orderCode}"
                        onclick="openPayPopup('${order.orderCode}')"
                        ${order.status !== 'PENDING_PAYMENT' ? 'disabled style="opacity: 0.5; cursor: not-allowed;"' : ''}>
                    ${order.status === 'PENDING_PAYMENT' ? 'PAY' : order.status}
                </button>
                <div id="popup-overlay-${order.orderCode}" class="popup-overlay" style="display:none;">
                    <div class="popup">
                        <h3>Payment order ${order.orderCode}</h3>
                        <span id="warning-${order.orderCode}" style="color: red; font-size: 12px; display: none;">
                            The amount is higher than the amount to be paid.
                        </span>
                        <br/>
                        <label style="margin-top: 10px; display: inline-block;">
                            <input type="checkbox" /> Pay All
                        </label>
                        <br/><br/>
                        <button onclick="confirmPayment('${order.orderCode}')">Confirm</button>
                        <button onclick="closePopup('${order.orderCode}')">Close</button>
                    </div>
                </div>
            </td>
        `;
		tableBody.appendChild(tr);
	});
}

function sortByColumn(column) {
	const sorted = [...originalData].sort((a, b) => {
		if (typeof a[column] === 'number') return a[column] - b[column];
		return String(a[column]).localeCompare(String(b[column]));
	});
	buildTable(sorted);
}

function openPayPopup(orderId) {
	document.getElementById(`popup-overlay-${orderId}`).style.display = 'flex';
}

function closePopup(orderId) {
	document.getElementById(`popup-overlay-${orderId}`).style.display = 'none';
}

function confirmPayment(orderCode) {
    closePopup(orderCode);
	fetch(`http://localhost:8080/payment-order/pay?orderCode=${encodeURIComponent(orderCode)}`, {
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
        	paymentMessage.style.color = 'green';
        	paymentMessage.textContent = 'Paid order ' + id + ' with code: ' + code;
        	paymentContainer.innerHTML = '';
        	loadData()
		})
		.catch(err => {
			paymentMessage.style.color = 'red';
			paymentMessage.textContent = `Failed to pay order: ${err.message}`;
		});
}