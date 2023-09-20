export function createToolbar() {
    return `
    <h1 class="title">Discover the best Events</h1>
    <div id="toolbar">
        <div class="search_bar">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none">
                <path
                    d="M19.6 21L13.3 14.7C12.8 15.1 12.225 15.4167 11.575 15.65C10.925 15.8833 10.2333 16 9.5 16C7.68333 16 6.146 15.3707 4.888 14.112C3.63 12.8533 3.00067 11.316 3 9.5C3 7.68333 3.62933 6.146 4.888 4.888C6.14667 3.63 7.684 3.00067 9.5 3C11.3167 3 12.854 3.62933 14.112 4.888C15.37 6.14667 15.9993 7.684 16 9.5C16 10.2333 15.8833 10.925 15.65 11.575C15.4167 12.225 15.1 12.8 14.7 13.3L21 19.6L19.6 21ZM9.5 14C10.75 14 11.8127 13.5623 12.688 12.687C13.5633 11.8117 14.0007 10.7493 14 9.5C14 8.25 13.5623 7.18733 12.687 6.312C11.8117 5.43667 10.7493 4.99933 9.5 5C8.25 5 7.18733 5.43767 6.312 6.313C5.43667 7.18833 4.99933 8.25067 5 9.5C5 10.75 5.43767 11.8127 6.313 12.688C7.18833 13.5633 8.25067 14.0007 9.5 14Z"
                    fill="#929292" />
            </svg>
            <input type="text" placeholder="Search" id="search">
        </div>
        <button class="filter_button" id="filterButton">
            <svg xmlns="http://www.w3.org/2000/svg" width="17" height="14" viewBox="0 0 17 14" fill="none">
                <rect width="17" height="4" rx="2" fill="#929292" />
                <rect x="2" y="5" width="13" height="4" rx="2" fill="#929292" />
                <rect x="3" y="10" width="10" height="4" rx="2" fill="#929292" />
            </svg>
            <span class="filter_text">Filters</span>
        </button>
        <button class="filter_button" id="resetFilterButton">
            <span class="filter_text">Reset Filters</span>
        </button>
	</div>
	<div id="filterModal" class="modal">
        <div class="modal-content">
            <span class="close">&times;</span>
            <div class="filter_content">
                <div id="locationFilter">
                    <p class="subtitle">Select Location</p>
                    <select class="select" id="locationSelect"></select>
                </div>
                <div id="categoryFilter">
                    <p class="subtitle">Select Category</p>
                    <select class="select" id="categorySelect"></select>
                </div>
            </div>
            <button id="filterConfirm" class="select_button">Confirm</button>
        </div>
    </div>
    `;
}

export function createTable() {
    return `
    <h1 class="title">My Orders </h1>
    <table class="order_table table-sortable">
        <thead>
            <tr>
                <th>Event Name</th>
                <th>Number Of Tickets</th>
                <th>Ticket Category</th>
                <th>Date</th>
                <th>Total Price</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody id="order_table_body"></tbody>
    </table>
    `
}