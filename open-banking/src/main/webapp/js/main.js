var me;
var selectedAccount;
var selectedBank;
var selectedBankName;

function getSelectedAccount() {
	return selectedAccount;
}

function setSelectedAccount(iban) {
	selectedAccount = iban;

}

function resetSelectedAccount() {
	selectedAccount = null;
}

function getSelectedBank() {
	return selectedBank;
}

function setSelectedBank(bank_id) {
	selectedBank = bank_id;
}

function resetSelectedBank() {
	selectedBank = null;
}

function setSelectedBankName(bank_id, callback) {
	$.ajax({
		url: "/bank/" + bank_id,
		method: "GET",
		success: function (data) {
			selectedBankName = data;
			callback();
		}, error: function () {
			alert("Error loading data!");
		}
	})
}

function getSelectedBankName() {
	return selectedBankName;
}

function getAuthorization() {
	$.ajax({
		url: "/authorization",
		method: "GET",
		complete: function (data) {
			if (data.status == 200) {
				me = data.responseJSON;
			} else {
				window.location.href = "index.html";
			}
		},
		fail: function () {
			window.location.href = "index.html";
		}
	});
}

function getAdministration() {
	$.ajax({
		url: "/authorization/admin",
		method: "GET",
		complete: function (data) {
			if (data.status == 200) {
				me = data.responseJSON;
			} else {
				window.location.href = "index.html";
			}
		},
		fail: function () {
			window.location.href = "index.html";
		}

	});
}

function logout() {
	$.ajax({
		url: "/session",
		method: "DELETE",
		complete: function (data) {
			if (data.status == 200) {
				window.location.href = "index.html";
			} else {
				alert("You are not logged in!");
				window.location.href = "index.html";
			}
		}
	});
}

function logoutAdmin() {
	$.ajax({
		url: "/session",
		method: "DELETE",
		complete: function (data) {
			if (data.status == 200) {
				window.location.href = "admin.html";
			} else {
				alert("You are not logged in!");
				window.location.href = "admin.html";
			}
		}
	});
}
