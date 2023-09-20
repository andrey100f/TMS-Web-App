import { createTable } from "../utils/templates";
import { addLoader, removeLoader } from "../main";

const user = JSON.parse(localStorage.getItem('userData'));

export function renderOrdersPage() {
    const mainContentDiv = document.querySelector('.main-content-component');
    mainContentDiv.innerHTML = createTable();

    const headerCells = document.querySelectorAll(".table-sortable th");

    headerCells[0].addEventListener("click", () => {
        const tableElement = headerCells[0].parentElement.parentElement.parentElement;
        const headerIndex = Array.prototype.indexOf.call(headerCells[0].parentElement.children, headerCells[0]);
        const currentIsAscending = headerCells[0].classList.contains("th-sort-asc");

        sortTableByColumnString(tableElement, headerIndex, !currentIsAscending);
    });

    headerCells[headerCells.length - 1].addEventListener("click", () => {
        const tableElement = headerCells[headerCells.length - 1].parentElement.parentElement.parentElement;
        const headerIndex = Array.prototype.indexOf.call(headerCells[headerCells.length - 1].parentElement.children, headerCells[headerCells.length - 1]);
        const currentIsAscending = headerCells[headerCells.length - 1].classList.contains("th-sort-asc");

        sortTableByColumnNumber(tableElement, headerIndex, !currentIsAscending);
    });

    addLoader();
    fetchOrdersData().then((data) => {
        setTimeout(() => {
            removeLoader();
        }, 200);
        const ordersTable = document.querySelector("#order_table_body");
        ordersTable.innerHTML = '';
        addOrders(data);
    });


}

async function fetchOrdersData() {
    try {
        const response = await fetch(`http://localhost:3000/api/orders/${user.user_id}`);
        if (!response.ok) {
            throw new Error('Network response was not ok!!');
        }
        const data = await response.json();
        return data;
    }
    catch (error) {
        console.log('Error fetching orders', error);
        return [];
    }
}

const addOrders = (orders) => {
    const ordersTable = document.querySelector("#order_table_body");
    if (!orders.length) {
        ordersTable.innerHTML = 'No Orders Found!!';
    }
    else {
        let number = 0;
        orders.forEach(order => {
            number += 1;
            const orderElement = createOrder(order, number);
            if (orderElement) {
                ordersTable.appendChild(orderElement);
            }
        });
    }
}

const createOrder = (orderData, number) => {
    if (!orderData) {
        console.log(orderData);
        console.error('Invalid order data');
        return null;
    }
    const orderElement = createOrderElement(orderData, number);
    return orderElement;
}

const createOrderElement = (orderData, number) => {
    const { ticket_categories, ordered_at, number_of_tickets, total_price } = orderData;

    const orderTableRow = document.createElement('tr');

    const eventNameElement = document.createElement('td');
    eventNameElement.textContent = orderData.ticket_categories.events.event_name;

    const numberOfTicketsElement = document.createElement('td');
    const inputNumber = document.createElement('input');
    inputNumber.setAttribute('id', `numberOfTickets-${number}`);
    inputNumber.type = 'number';
    inputNumber.className = 'input_number';
    inputNumber.classList.add('readonly-number-input');
    inputNumber.value = orderData.number_of_tickets.toString();
    numberOfTicketsElement.appendChild(inputNumber);

    const ticketCategoryElement = document.createElement('td');
    const selectElement = document.createElement('select');
    selectElement.setAttribute('id', `ticketCategory-${number}`);
    selectElement.className = 'select';
    selectElement.classList.add('readonly-select');
    let index = 0;
    for (const category of orderData.ticket_categories.events.ticket_categories) {
        const option = document.createElement('option');
        option.value = category.category_id;
        option.textContent = category.description;
        selectElement.appendChild(option);
        if (category.description == orderData.ticket_categories.description) {
            selectElement.selectedIndex = index;
        }
        index++;
    }
    ticketCategoryElement.appendChild(selectElement);

    const orderedAtElement = document.createElement('td');
    orderedAtElement.textContent = orderData.ordered_at;


    const totalPriceElement = document.createElement('td');
    totalPriceElement.textContent = orderData.total_price;

    const actionsElement = document.createElement('td');

    const confirmButton = document.createElement('i');
    confirmButton.classList.add('fa-solid', 'fa-check', 'icon');
    confirmButton.setAttribute('id', `confirm-${number}`);
    confirmButton.style.display = 'none';
    confirmButton.addEventListener('click', () => {
        confirmHandler();
    })

    const cancelButton = document.createElement('i');
    cancelButton.classList.add('fa-solid', 'fa-x', 'icon');
    cancelButton.setAttribute('id', `cancel-${number}`);
    cancelButton.style.display = 'none';
    cancelButton.addEventListener('click', () => {
        cancelHandler();
    })

    const updateButton = document.createElement('i');
    updateButton.classList.add('fa-solid', 'fa-pen-to-square', 'icon');
    updateButton.setAttribute('id', `update-${number}`);
    updateButton.addEventListener('click', () => {
        updateHandler();
    })

    const deleteButton = document.createElement('i');
    deleteButton.classList.add('fa-solid', 'fa-trash', 'icon');
    deleteButton.setAttribute('id', `deleteButton-${number}`);
    deleteButton.addEventListener('click', () => {
        deleteOrder(orderData.order_id);
    })

    actionsElement.appendChild(confirmButton);
    actionsElement.appendChild(cancelButton);
    actionsElement.appendChild(updateButton);
    actionsElement.appendChild(deleteButton);

    orderTableRow.appendChild(eventNameElement);
    orderTableRow.appendChild(numberOfTicketsElement);
    orderTableRow.appendChild(ticketCategoryElement);
    orderTableRow.appendChild(orderedAtElement);
    orderTableRow.appendChild(totalPriceElement);
    orderTableRow.appendChild(actionsElement);

    function updateHandler() {
        if (confirmButton.style.display === 'none' && cancelButton.style.display === 'none') {
            confirmButton.style.display = 'inline';
            cancelButton.style.display = 'inline';
            updateButton.style.display = 'none';
            inputNumber.classList.remove('readonly-number-input');
            selectElement.classList.remove('readonly-select');
        }
    }

    function cancelHandler() {
        confirmButton.style.display = 'none';
        cancelButton.style.display = 'none';
        updateButton.style.display = 'inline';
        selectElement.classList.add('readonly-select');
        inputNumber.classList.add('readonly-number-input');

        inputNumber.value = orderData.number_of_tickets;
        let index = 0;
        for (const category of orderData.ticket_categories.events.ticket_categories) {
            if (category.description == orderData.ticket_categories.events.ticket_categories.description) {
                selectElement.selectedIndex = index;
            }
            index++;
        }
    }

    function confirmHandler() {
        const newTicketCategory = selectElement.options[selectElement.selectedIndex].text;
        const newNumberOfTickets = parseInt(inputNumber.value);
        if (newTicketCategory != orderData.ticket_categories.description || newNumberOfTickets != orderData.number_of_tickets) {
            addLoader();
            updateOrder(orderData.order_id, orderData.ticket_categories.events.event_name, newTicketCategory, newNumberOfTickets)
                .then((response) => {
                    if (response.ok) {
                        response.json().then((data) => {
                            orderData = data;
                            totalPriceElement.textContent = orderData.total_price;
                        })
                    }
                }).catch((error) => {
                    console.error(error);
                }).finally(() => {
                    setTimeout(() => {
                        removeLoader();
                    }, 200);
                });
        }

        confirmButton.style.display = 'none';
        cancelButton.style.display = 'none';
        updateButton.style.display = 'inline';
        selectElement.classList.add('readonly-select');
        inputNumber.classList.add('readonly-number-input');
    }

    return orderTableRow;
}

async function updateOrder(order_id, event_name, ticket_category, number_of_tickets) {
    return await fetch(`http://localhost:3000/api/orders/${user.user_id}/${order_id}`, {
        method: 'PATCH',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify({
            eventName: event_name,
            ticketCategory: ticket_category,
            numberOfTickets: number_of_tickets
        })
    }).then((response) => {
        if (response.ok) {
            toastr.success("Order updated successfully!!");
            renderOrdersPage();
        }
        else {
            toastr.error("Failed to update order...");
        }
        return response;
    }).catch((error) => {
        throw new Error(error);
    });
}

async function deleteOrder(orderId) {
    addLoader();
    await fetch(`http://localhost:3000/api/orders/${user.user_id}/${orderId}`, {
        method: 'DELETE',
        headers: {
            'Content-Type': 'application/json'
        },
    }).then((response) => {
        response.json();
    }).then(() => {
        renderOrdersPage();
        toastr.success("Order deleted successfully!!");
    }).catch((error) => {
        toastr.error("Error on deleting order...");
        console.error(error);
    }).finally(() => {
        setTimeout(() => {
            removeLoader();
        }, 200);
    });
}