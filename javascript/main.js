//Counts
var uncheckedCnt = 0;  //Counts the number of unchecked checkboxes in list
var checkedCnt = 0;    //Counts the number of checked checkboxes in list
var totalCnt = 0;      //Counts the total number of tasks or items

//For shopping checklist only
var totalPrice = 0.00;  //Total price of all items (including quantities) combined 

//Regex, used for shopping checklist
var pwReg = new RegExp("^[1-9]+[0-9]*$");  //Regex for positive whole numbers
var curReg = new RegExp("^(([$])?((([0-9]{1,3},)+[0-9]{3})|[0-9]+)(\.[0-9]{2})?)$")  //Regex for USD currency

//Format price so it has .00 at the end
function formatPrice(price) {
  price = Math.abs(price);

  var formatter = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD', });
  var formattedPrice = formatter.format(price);

  return formattedPrice; //Don't do equations with this!!!
}

//Display header
function displayHeader() {
  document.getElementById("header-place").innerHTML = '<header> <div class="header-content"> <div class="header-home"> <a href="./index.html"><img src="./images/check-list-64px.png" alt="checklists" /></a> <a href="./index.html" class="header-text title-font">Checklists</a> </div> <nav class="nav-buttons"> <div class="dropdown"> <span class="button-for-nav">Make a List &nbsp<img src="./images/down-arrow.png" alt="dropdown-icon" /></span> <div class="dropdown-content"> <a href="./todo.html">To-Do List</a> <a href="./shopping.html">Shopping List</a> </div> </div> <a href="./about.html" class="button-for-nav">About</a> <a href="./credits.html" class="button-for-nav">Attribution</a> <a href="./login.html" class="button-for-nav">Sign In</a> </nav> </div> </header>';
}

//Display footer
function displayFooter() {
  document.getElementById("footer-place").innerHTML = "<footer>@2022</footer>";
}

//Display count numbers on webpage
function displayCounts(tableNameStr) {
  switch (tableNameStr) {
    case "todo-table":
      displayToDoCounts();
      break;
    case "shopping-table":
      displayShoppingCounts();
      break;
    default:
      console.error("Error: Table not found. displayCounts can't be executed");
  }
}

function displayToDoCounts() {
  document.getElementById("num-of-tasks").innerHTML = totalCnt;
  document.getElementById("num-of-c-tasks").innerHTML = checkedCnt;
  document.getElementById("num-of-u-tasks").innerHTML = uncheckedCnt;
}

function displayShoppingCounts() {
  document.getElementById("num-of-items").innerHTML = totalCnt;
  document.getElementById("num-of-p-items").innerHTML = checkedCnt;
  document.getElementById("num-of-u-items").innerHTML = uncheckedCnt;
  document.getElementById("price-of-items").innerHTML = formatPrice(totalPrice);
}

//Instead of submitting, pressing enter executes addRowToDo or addRowShopping()
function enterForRows(event, tableName) {
  //Detect enter key
  if (event.key === "Enter") {
    //Stop from submitting
    event.preventDefault();
  }
  else {
    return;
  }

  //Enter key is pressed do one of the following
  if (tableName == "todo-table") {
    console.log("to-do")
    addRowToDo();
  }
  else if (tableName == "shopping-table") {
    console.log("shopping")
    addRowShopping();
  }
}

//Add an item to the To-Do list
function addRowToDo() {
  var taskDesc = document.getElementById("task").value;   //Get description of the task to be added (get input from user)

  //If the user doesn't input anything
  if (taskDesc == "") {
    alert("Please type in a task with at least one character");
    return;
  }

  //Create checkbox
  var checkbox = document.createElement("INPUT");
  checkboxAttrToDo(checkbox, "todo-table");

  //Count update
  uncheckedCnt++;
  totalCnt++;
  displayCounts("todo-table");

  // Get table (the todo checklist visual)
  var table = document.getElementById("todo-table");

  // Insert Row
  var row = table.insertRow();

  // Insert cells and info
  //First cell of row (Checkbox)
  var cell = row.insertCell();
  cell.appendChild(checkbox);
  //Second cell of row (Task)
  cell = row.insertCell();
  cell.innerHTML = taskDesc;
  //Third cell ("remove" button column)
  cell = row.insertCell();
  createRemoveBtn(checkbox, cell, row, "todo-table");

  //Remove input in text box after adding
  document.getElementById("task").value = ''
}

//Add an item to the Shopping List
function addRowShopping() {
  //Get user input for item name, quantity, and price
  var itemName = document.getElementById("item").value;
  var quantity = document.getElementById("quantity").value;
  var price = document.getElementById("price").value

  //If the user doesn't input anything
  if (itemName == "") {
    alert("Please type in an item with at least one character");
    return;
  }
  //Quantity and price is not required by the user. Both have default values.
  if (quantity == "" || quantity == "0") {
    quantity = 1;
  }
  //If quantity is not a positive whole number
  else if (pwReg.test(quantity) == false) {
    alert("Please type in a positive whole number for quantity");
    return;
  }
  if (price == "") {
    price = "0.00";
  }
  //If price is not in the correct format
  else if (curReg.test(price) == false) {
    alert("Please type in price in USD price format with or without commas. Examples of valid input: 9, 9.00, 99.99, 99999.99, and 1,999,999.00");
    return;
  }

  //Calculate price of an item and total price of all items
  var itemPrice = price * quantity;
  totalPrice = itemPrice + totalPrice;

  //Create checkbox
  var checkbox = document.createElement("INPUT");
  checkboxAttrShopping(checkbox, "shopping-table", quantity);

  //Count update
  uncheckedCnt = uncheckedCnt + parseInt(quantity);
  totalCnt = totalCnt + parseInt(quantity);
  displayShoppingCounts();

  // Get table (the todo checklist visual)
  var table = document.getElementById("shopping-table");

  // Insert Row
  var row = table.insertRow();

  // Insert cells and info
  //First cell of row (Checkbox)
  var cell = row.insertCell();
  cell.appendChild(checkbox);
  //Second cell of row (Item)
  cell = row.insertCell();
  cell.innerHTML = itemName;
  //Third cell of row (Quantity)
  cell = row.insertCell();
  cell.innerHTML = quantity;
  //Fourth cell of row (Price)
  cell = row.insertCell();
  cell.innerHTML = formatPrice(itemPrice);
  //Last cell ("remove" button column)
  cell = row.insertCell();
  createRemoveBtn(checkbox, cell, row, "shopping-table");

  //Remove input in text box after adding
  document.getElementById("item").value = '';
  document.getElementById("quantity").value = '';
  document.getElementById("price").value = '';
}

//Add attributes to checkbox
function checkboxAttrToDo(checkbox, tableNameStr) {   //For todo table
  checkbox.setAttribute("type", "checkbox");
  checkboxStyle(checkbox);  //Style checkbox

  //Change count for unchecked and checked when checkbox is clicked on
  checkbox.onclick = function () {
    //If unchecked
    if (checkbox.checked == false) {
      uncheckedCnt++; checkedCnt--;
    }
    //If checked
    else {
      uncheckedCnt--; checkedCnt++;
    }
    displayCounts(tableNameStr);
  }
};

//Overload checkboxAttr
function checkboxAttrShopping(checkbox, tableNameStr, quantity) {   //For shopping table
  checkbox.setAttribute("type", "checkbox");
  checkboxStyle(checkbox);  //Style checkbox

  //Change count for unchecked and checked when checkbox is clicked on
  checkbox.onclick = function () {
    //If unchecked
    if (checkbox.checked == false) {
      uncheckedCnt = uncheckedCnt + parseInt(quantity);
      checkedCnt = checkedCnt - parseInt(quantity);
    }
    //If checked
    else {
      uncheckedCnt = uncheckedCnt - parseInt(quantity);
      checkedCnt = checkedCnt + parseInt(quantity);
    }
    displayCounts(tableNameStr);
  }
};

function checkboxStyle(checkbox) {
  checkbox.style.width = "25px";
  checkbox.style.height = "25px";
}

//Delete specified row from table
function removeRow(checkbox, rowToDelete, tableName) {
  switch (tableName) {
    case "todo-table":
      removeRowTodo(checkbox, rowToDelete);
      break;
    case "shopping-table":
      removeRowShopping(checkbox, rowToDelete,)
      break;
    default:
      console.error("Error: Table not found. removeRow can't be executed");
  }
  document.getElementById(tableName).deleteRow(rowToDelete.rowIndex);
  displayCounts(tableName);
}

//Remove row from todo list
function removeRowTodo(checkbox) {
  if (checkbox.checked == true) {
    checkedCnt--;
  }
  else {
    uncheckedCnt--;
  }
  totalCnt--;
}

//Remove row from shopping list
function removeRowShopping(checkbox, rowToDelete) {
  //Reduce total price in shopping list
  var itemPriceDisplay = rowToDelete.cells[3].innerHTML;  //Get price
  var itemPrice = Number(itemPriceDisplay.replace(/[^0-9\.-]+/g, ""));   //Remove formatting so it is a float
  totalPrice = totalPrice - itemPrice;

  //Get quantity
  var quantity = rowToDelete.cells[2].innerHTML;

  //Change counts
  if (checkbox.checked == true) {
    checkedCnt = checkedCnt - parseInt(quantity);
  }
  else {
    uncheckedCnt = uncheckedCnt - parseInt(quantity);
  }
  totalCnt = totalCnt - quantity;
}

//Create a remove button in the row
function createRemoveBtn(checkbox, cell, row, tableName) {
  var removeBtn = document.createElement("BUTTON");
  var removeText = document.createTextNode("Remove");
  removeBtn.onclick = function () { removeRow(checkbox, row, tableName) };
  removeBtn.appendChild(removeText);
  cell.appendChild(removeBtn);
}

//Array of attribution links
const attrArray = [];

function attrArrayFunct() {
  // let useWhile = false;

  attrArray[0] = '<p><img src="./images/check-list.png" alt="icon"/>&nbsp;<a class = "a-color-link" href="https://www.flaticon.com/free-icons/task" target="-blank" rel="noopener noreferrer" title="task icons">Task icons created by Freepik - Flaticon</a></p>'
  attrArray[1] = '<br><p><img src="./images/down-arrow.png" alt="icon"/>&nbsp;<a class = "a-color-link" href="https://www.flaticon.com/free-icons/triangle" target="-blank" rel="noopener noreferrer"title="triangle icons">Triangle icons created by Iconpro86 - Flaticon. Edited by Me.</a></p>'
  attrArray[2] = '<br><p><a class = "a-color-link" href="https://www.pexels.com/photo/mug-watch-and-planner-book-on-brown-wooden-surface-2736499/" target="-blank" rel="noopener noreferrer" title="Mug, Watch, and Planner Book on Brown Wooden Surface">Mug, Watch, and Planner Book on Brown Wooden Surface by Content Pixie - Pexels. Edited by Me.</a></p>'

  for (let i = 0; i < attrArray.length; i++) {
    document.getElementById("attr-section").innerHTML += attrArray[i];
  }

  //Another way to do this
  // while(useWhile == true)
  // {
  //   let i = 0;
  //   while(i < attrArray.length)
  //   {
  //     document.getElementById("attr-section").innerHTML += attrArray[i];
  //     i++;
  //   }
  // }
}

//Commented out HTML below is created by attrArrayFunct()
/* <p><img src="images\check-list.png" alt="icon"/>&nbsp;<a class = "a-color-link" href="https://www.flaticon.com/free-icons/task" target="-blank" rel="noopener noreferrer" title="task icons">Task icons created by Freepik - Flaticon</a></p>
<br>
<p><img src="images\down-arrow.png" alt="icon"/>&nbsp;<a class = "a-color-link" href="https://www.flaticon.com/free-icons/triangle" target="-blank" rel="noopener noreferrer"title="triangle icons">Triangle icons created by Iconpro86 - Flaticon. Edited by Me.</a></p>
 <br>
<p><a class = "a-color-link" href="https://www.flaticon.com/free-icons/triangle" target="-blank" rel="noopener noreferrer" title="Mug, Watch, and Planner Book on Brown Wooden Surface">Mug, Watch, and Planner Book on Brown Wooden Surface by Content Pixie - Pexels. Edited by Me.</a></p>
</br> */

//Validate confirmed password for registration
function confirmPassValidate() {
  var password = document.getElementById("password")
  var confirmPassword = document.getElementById("confirm-password");

  if (password.value != confirmPassword.value) {
    confirmPassword.setCustomValidity("Passwords Don't Match");
  } else {
    confirmPassword.setCustomValidity('');
  }

  password.onchange = confirmPassValidate;
  confirmPassword.onkeyup = confirmPassValidate;
}

