import { createToolbar } from "../utils/templates";
import { setupSearchEvents, getFilters } from "../utils/event_utils";
import { addLoader, removeLoader } from "../main";

const userId = JSON.parse(localStorage.getItem('userData')).user_id;

export function renderEventsPage() {
    const mainContentDiv = document.querySelector('.main-content-component');

    mainContentDiv.innerHTML = createToolbar();
    handleFilterButton();

    const cardContainer = document.createElement('div');
    cardContainer.className = 'card_container';
    mainContentDiv.appendChild(cardContainer);
    addLoader();
    fetchEventData().then((data) => {
        setTimeout(() => {
            removeLoader();
        }, 200);
        addEvents(data);

        const resetFilterButton = document.querySelector('#resetFilterButton');
        resetFilterButton.addEventListener('click', () => {
            addEvents(data);
        });

        setupSearchEvents(data);
    });
}

async function fetchEventData() {
    try {
        const response = await fetch('http://localhost:3000/api/events');
        if (!response.ok) {
            throw new Error('Network response was not ok!!');
        }
        const data = await response.json();
        return data;
    }
    catch (error) {
        console.log('Error fetching events', error);
        return [];
    }
}

export function addEvents(events) {
    const eventsDiv = document.querySelector(".card_container");
    eventsDiv.innerHTML = '';
    if (!events.length) {
        eventsDiv.innerHTML = 'No Events Found!!';
    }
    else {
        events.forEach(event => {
            const eventElement = createEvent(event);
            if (eventElement) {
                eventsDiv.appendChild(eventElement);
            }
        });
    }
}

const createEvent = (eventData) => {
    if (!eventData) {
        console.error('Invalid event data', eventData);
        return null;
    }
    const eventElement = createEventElement(eventData);
    return eventElement;
}

const createEventElement = (eventData) => {
    const { event_description, image_url, event_name, ticket_categories, venues } = eventData;

    const eventDiv = document.createElement('div');
    eventDiv.classList.add('card');

    const imageElement = document.createElement('img');
    imageElement.src = eventData.image_url;
    imageElement.className = 'event_image';

    const eventCard = document.createElement('div');
    eventCard.className = 'event_card';

    const eventNameElement = document.createElement('p');
    eventNameElement.className = 'subtitle';
    eventNameElement.textContent = eventData.event_name;

    const eventLocation = document.createElement('p');
    eventLocation.className = 'description';
    eventLocation.textContent = eventData.venues.location;

    const eventDescriptionElement = document.createElement('p');
    eventDescriptionElement.className = 'description';
    eventDescriptionElement.textContent = eventData.event_description;

    const chooseCategory = document.createElement('p');
    chooseCategory.className = 'subtitle';
    chooseCategory.textContent = 'Choose Ticket Category:';

    const selectElement = document.createElement('select');
    selectElement.className = 'select';

    for (const category of eventData.ticket_categories) {
        const option = document.createElement('option');
        option.value = category.category_id;
        option.textContent = category.description;
        selectElement.appendChild(option);
    }

    const inputContainer = document.createElement('div');
    inputContainer.className = 'input_container';

    const inputNumber = document.createElement('input');
    inputNumber.type = 'number';
    inputNumber.className = 'input_number';
    inputNumber.min = '0';
    inputNumber.value = '0';

    inputNumber.addEventListener('blur', () => {
        if (!inputNumber.value) {
            inputNumber.value = 0;
        }
    });
    inputNumber.addEventListener('input', () => {
        const currentQuantity = parseInt(inputNumber.value);
        if (currentQuantity > 0) {
            addToCartButton.disabled = false;
        } else {
            addToCartButton.disabled = true;
        }
    });
    inputNumber.addEventListener('input', () => {
        const currentQuantity = parseInt(inputNumber.value);
        if (currentQuantity > 0) {
            addToCartButton.disabled = false;
        } else {
            addToCartButton.disabled = true;
        }
    });

    const buttonPlus = document.createElement('button');
    buttonPlus.className = 'input_button';
    buttonPlus.textContent = '+';

    buttonPlus.addEventListener('click', () => {
        inputNumber.value = parseInt(inputNumber.value) + 1;
        const currentQuantity = parseInt(inputNumber.value);
        if (currentQuantity > 0) {
            addToCartButton.disabled = false;
        } else {
            addToCartButton.disabled = true;
        }
    });

    const buttonMinus = document.createElement('button');
    buttonMinus.className = 'input_button';
    buttonMinus.textContent = '-';

    buttonMinus.addEventListener('click', () => {
        const currentValue = parseInt(inputNumber.value);
        if (currentValue > 0) {
            inputNumber.value = currentValue - 1;
        }
        const currentQuantity = parseInt(inputNumber.value);
        if (currentQuantity > 0) {
            addToCartButton.disabled = false;
        } else {
            addToCartButton.disabled = true;
        }
    });

    inputContainer.appendChild(inputNumber);
    inputContainer.appendChild(buttonPlus);
    inputContainer.appendChild(buttonMinus);

    const addToCartButton = document.createElement('button');
    addToCartButton.className = 'select_button';
    addToCartButton.textContent = 'Add To Cart';
    addToCartButton.disabled = true;

    addToCartButton.addEventListener('click', () => {
        handleAddToCard(eventData.event_name, selectElement, inputNumber, addToCartButton);
    });

    eventCard.appendChild(eventNameElement);
    eventCard.appendChild(eventLocation);
    eventCard.appendChild(eventDescriptionElement);
    eventCard.appendChild(chooseCategory);
    eventCard.appendChild(selectElement);
    eventCard.appendChild(inputContainer);
    eventCard.appendChild(addToCartButton);

    eventDiv.appendChild(imageElement);
    eventDiv.appendChild(eventCard);
    return eventDiv;
}

function handleAddToCard(eventName, selectElement, inputNumber, addToCartButton) {
    let ticketCategory = selectElement.options[selectElement.selectedIndex].text;
    let numberOfTickets = inputNumber.value;
    if (parseInt(numberOfTickets)) {
        addLoader();
        fetch(`http://localhost:3000/api/orders/${userId}`, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                eventName: eventName,
                ticketCategory: ticketCategory,
                numberOfTickets: +numberOfTickets
            })
        }).then((response) => {
            return response.json().then((data) => {
                if (!response.ok) {
                    console.log("Something went wrong!!");
                }
                return data;
            })
        }).then((data) => {
            console.log('Done!!');
            inputNumber.value = '0';
            addToCartButton.disabled = true;
            toastr.success("Order added successfully!!");
            return data;
        }).catch(error => {
            toastr.error("Something went wrong!!");
        }).finally(() => {
            setTimeout(() => {
                removeLoader();
            }, 200);
        })
    }
}

async function handleFilterButton() {
    const openModalButton = document.getElementById("filterButton");
    const modal = document.getElementById("filterModal");
    const closeSpan = document.getElementsByClassName("close")[0];

    // Deschide modalul la apăsarea butonului
    openModalButton.addEventListener("click", function () {
        modal.style.display = "block";
    });

    // Închide modalul la apăsarea butonului "x"
    closeSpan.addEventListener("click", function () {
        modal.style.display = "none";
    });

    // Închide modalul dacă se dă clic în afara conținutului
    window.addEventListener("click", function (event) {
        if (event.target === modal) {
            modal.style.display = "none";
        }
    });

    const locationSelect = document.querySelector('#locationSelect');
    const categorySelect = document.querySelector('#categorySelect');

    const eventData = await fetchEventData();
    const categories = new Set(eventData.map((e) => e.event_categories.category_name));
    const locations = new Set(eventData.map((e) => e.venues.location));

    for (const category of categories) {
        const option = document.createElement('option');
        option.value = category;
        option.textContent = category;
        categorySelect.appendChild(option);
    }

    for (const location of locations) {
        const option = document.createElement('option');
        option.value = location;
        option.textContent = location;
        locationSelect.appendChild(option);
    }

    const confirmButton = document.querySelector('#filterConfirm');

    confirmButton.addEventListener('click', () => {
        handleFilterEvents();
        modal.style.display = 'none';
    });
}

async function getFilteredEvents(filters) {
    const queryParams = new URLSearchParams(filters).toString();
    const events = await fetch(`http://localhost:3000/api/events/filters?${queryParams}`, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json'
        },
    });
    const result = await events.json();
    return result;
}

async function handleFilterEvents() {
    const filters = getFilters();
    try {
        const filteredEvents = await getFilteredEvents(filters);
        addEvents(filteredEvents);
    }
    catch (error) {
        console.error("Error fetching events", error);
    }
}