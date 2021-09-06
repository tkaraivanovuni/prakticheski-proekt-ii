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

async function setSelectedBank(bank_id, setBankName) {
	selectedBank = bank_id;
	await setBankName(bank_id);
}

function resetSelectedBank() {
	selectedBank = null;
}

function setSelectedBankName(bank_id) {
	$.ajax({
		url: "/bank/" + bank_id,
		method: "GET",
		success: function (data) {
			selectedBankName = data;
		}, error: function () {
			alert("No bank found with this id!");
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
