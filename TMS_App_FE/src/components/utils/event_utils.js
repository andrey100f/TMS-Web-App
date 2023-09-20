import { addEvents } from "../event/event_scripts";

export async function liveSearch(events) {
    const searchInput = document.querySelector("#search");
    if (searchInput) {
        const searchValue = searchInput.value;
        if (searchValue != undefined) {
            // const filteredEvents = events.filter((e) =>
            //     e.event_name.toLowerCase().includes(searchValue.toLowerCase())
            const filter = {
                name: searchValue
            };
            const queryParams = new URLSearchParams(filter).toString();
            const events = await fetch(`http://localhost:3000/api/events/search?${queryParams}`, {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
            });
            const filteredEvents = await events.json();
            addEvents(filteredEvents);
        }
    }
}

export function setupSearchEvents(events) {
    const searchInput = document.querySelector('#search');
    if (searchInput) {
        const searchInterval = 500;
        searchInput.addEventListener('keyup', () => {
            setTimeout(liveSearch(events), searchInterval);
        });
    }
}

export function getFilters() {
    const categorySelect = document.querySelector('#categorySelect');
    const locationSelect = document.querySelector('#locationSelect');

    const categorySelected = categorySelect.options[categorySelect.selectedIndex].text;
    const locationSelected = locationSelect.options[locationSelect.selectedIndex].text;

    return {
        location: locationSelected,
        category: categorySelected
    };
}