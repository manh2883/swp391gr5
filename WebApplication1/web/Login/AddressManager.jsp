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
        .hidden { display: none; }
    </style>
    <script>
        function enableEdit(addressId) {
            document.getElementById('input-' + addressId).removeAttribute('readonly');
            document.getElementById('edit-' + addressId).classList.add('hidden');
            document.getElementById('delete-' + addressId).classList.add('hidden');
            document.getElementById('save-' + addressId).classList.remove('hidden');
        }

        function validateAndSubmit(formId) {
            const input = document.getElementById(formId).querySelector("input[name='addressLine']");
            if (input.value.trim() === '' || input.value.length > 100) {
                alert("Address cannot be empty and must be less than 100 characters.");
                return false;
            }
            document.getElementById(formId).submit();
        }

        function addNewAddress() {
            const container = document.getElementById("address-list");
            const newId = "new-address";
            container.innerHTML += `
                <form id="form" action="AddressManager" method="post">
                    <input type="text" id="input-${newId}" name="addressLine" maxlength="100">
                    <button type="button" onclick="validateAndSubmit('form-${newId}')">Save</button>
                </form>
            `;
            document.getElementById("add-button").classList.add("hidden");
        }
    </script>
</head>
<body>
    <h2>Manage Addresses</h2>
    <div id="address-list">
        <c:forEach var="address" items="${addressList}">
            <form id="form" action="AddressManager" method="post">
                <input type="text"  name="addressLine" value="${address.addressLine}" readonly>
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
