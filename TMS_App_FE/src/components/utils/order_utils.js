export function sortTableByColumnString(table, column, asc = true) {
    let dirModifier;
    if (asc) {
        dirModifier = 1;
    } else {
        dirModifier = -1;
    }
    const tBody = table.tBodies[0];
    const rows = Array.from(tBody.querySelectorAll('tr'));

    // Sort each row
    const sortedRows = rows.sort((a, b) => {
        const aColText = a.querySelector(`td:nth-child(${column + 1})`).textContent.trim();
        const bColText = b.querySelector(`td:nth-child(${column + 1})`).textContent.trim();

        if (aColText > bColText) {
            return 1 * dirModifier;
        }

        if (aColText < bColText) {
            return -1 * dirModifier;
        }
        return 0;
    });

    // Remove all existing tr from the table
    while (tBody.firstChild) {
        tBody.removeChild(tBody.firstChild);
    }

    // Readd the sorted rows
    tBody.append(...sortedRows);

    // Remember how the column is currently sorted
    table.querySelectorAll('th').forEach(th => th.classList.remove('th-sort-asc', 'th-sort-desc'));
    table.querySelector(`th:nth-child(${column + 1})`).classList.toggle('th-sort-asc', asc);
    table.querySelector(`th:nth-child(${column + 1})`).classList.toggle('th-sort-desc', !asc);
}

export function sortTableByColumnNumber(table, column, asc = true) {
    let dirModifier;
    if (asc) {
        dirModifier = 1;
    } else {
        dirModifier = -1;
    }
    const tBody = table.tBodies[0];
    const rows = Array.from(tBody.querySelectorAll('tr'));

    // Sort each row
    const sortedRows = rows.sort((a, b) => {
        const aColText = a.querySelector(`td:nth-child(${column + 1})`).textContent.trim();
        const bColText = b.querySelector(`td:nth-child(${column + 1})`).textContent.trim();

        if (aColText - bColText > 0) {
            return 1 * dirModifier;
        }

        if (aColText - bColText < 0) {
            return -1 * dirModifier;
        }
        return 0;
    });

    // Remove all existing tr from the table
    while (tBody.firstChild) {
        tBody.removeChild(tBody.firstChild);
    }

    // Readd the sorted rows
    tBody.append(...sortedRows);

    // Remember how the column is currently sorted
    table.querySelectorAll('th').forEach(th => th.classList.remove('th-sort-asc', 'th-sort-desc'));
    table.querySelector(`th:nth-child(${column + 1})`).classList.toggle('th-sort-asc', asc);
    table.querySelector(`th:nth-child(${column + 1})`).classList.toggle('th-sort-desc', !asc);
}