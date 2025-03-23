<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Manage Addresses</title>
        <style>
            .hidden {
                display: none;
            }
        </style>
        <script>
            function enableEdit(addressID) {
                let form = document.querySelector(`form input[name='addressID'][value='${addressID}']`).closest("form");
                let inputField = form.querySelector("input[name='addressContent']");
                let editButton = form.querySelector("button:nth-of-type(1)");
                let deleteButton = form.querySelector("button:nth-of-type(2)");
                let saveButton = form.querySelector("button:nth-of-type(3)");

                inputField.readOnly = false;
                editButton.style.display = "none";
                deleteButton.style.display = "none";
                saveButton.classList.remove("hidden");
            }

            function validateAndSubmit(form) {
                let inputField = form.querySelector("input[name='addressContent']");
                let value = inputField.value.trim();

                if (!value) {
                    alert("Address cannot be empty.");
                    return;
                }
                if (value.length > 120) {
                    alert("Address cannot exceed 120 characters.");
                    return;
                }

                form.submit();
            }

            function addNewAddress() {
                let container = document.getElementById("address-list");
                let form = document.createElement("form");
                let addButton = document.getElementById("add-button");
                form.setAttribute("action", "AddressManager");
                form.setAttribute("method", "post");

                form.innerHTML = `
        <input type="text" name="addressContent" placeholder="Enter new address">
        <button type="button" onclick="validateAndSubmit(this.closest('form'))">Save</button>
    `;

                container.appendChild(form);
            }

        </script>
    </head>
    <body>
        <h2>Manage Addresses</h2>
        <div id="address-list">
            <c:forEach var="address" items="${addressList}">
                <form id="form" action="AddressManager" method="post">
                    <input type="text"  name="addressID" value="${address.addressID}" readonly style="visibility: hidden">
                    <input type="text"  name="addressContent" value="${address.addressLine}" readonly>
                    <button type="button"  
                            onclick="enableEdit()">Edit</button>
                    <button type="button" >Delete</button>
                    <button type="button"  class="hidden" 
                            onclick="validateAndSubmit()">Save</button>
                </form>
            </c:forEach>
        </div>
        <button id="add-button" onclick="addNewAddress()">Add Address</button>
    </body>
</html>
