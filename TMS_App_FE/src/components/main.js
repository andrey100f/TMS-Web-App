import { renderEventsPage } from './event/event_scripts'
import { renderOrdersPage } from './order/order_scripts';

const user = JSON.parse(localStorage.getItem('userData'));
function setUsername() {
  const userFields = document.querySelectorAll('.user_name');
  userFields.forEach((user_field) => {
    user_field.innerHTML = user.customer_name;
  })
}
setUsername();

// Funcție pentru a naviga la o anumită secțiune pe bază de clic pe link
function navigateToSection(sectionId) {
  if (sectionId === '/events') {
    renderEvents();
  } else if (sectionId === '/orders') {
    renderOrders();
  }
  else if (sectionId === '/') {
    logOut();
  }
}

function logOut() {
  localStorage.removeItem('userData');
  window.location.href = '/';
}

function renderEvents() {
  if (document.querySelector('.card_container') != null) {
    resetPage();
  }
  renderEventsPage();
}

function renderOrders() {
  if (document.querySelector('.card_container') != null) {
    resetPage();
  }
  renderOrdersPage();
}

// Configurarea evenimentelor de navigare pe elementele navbar-ului
function setupNavigationEvents() {
  const navLinks = document.querySelectorAll('a');
  navLinks.forEach((link) => {
    link.addEventListener('click', (event) => {
      event.preventDefault();
      const sectionId = link.getAttribute('href');
      navigateToSection(sectionId);
    });
  });
}

// Inițializarea paginii cu conținutul paginii inițiale
function setupInitialPage() {
  const initialSectionId = window.location.hash || '/events';
  navigateToSection(initialSectionId);
}

function resetPage() {
  const mainContentDiv = document.querySelector('.main-content-component');
  if (document.querySelector('#toolbar')) {
    mainContentDiv.removeChild(document.querySelector('#toolbar'));
  }
  mainContentDiv.removeChild(document.querySelector('.card_container'));
  mainContentDiv.removeChild(document.querySelector('.title'));
}

// Adaugă loader-ul în containerul specificat
export function addLoader() {
  const loader = document.querySelector(".loader_div");
  loader.style.visibility = 'visible';
}

// Elimină loader-ul din containerul specificat
export function removeLoader() {
  const loader = document.querySelector('.loader_div');
  loader.style.visibility = 'hidden';
}

setupNavigationEvents();
setupInitialPage();
renderEvents();