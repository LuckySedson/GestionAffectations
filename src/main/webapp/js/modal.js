let callbackConfirmation = null;

function ouvrirConfirmation(message, callback) {
    document.getElementById("modal-message").textContent = message;
    callbackConfirmation = callback;
    document.getElementById("modal-overlay").classList.add("actif");
}

function fermerConfirmation(confirme) {
    document.getElementById("modal-overlay").classList.remove("actif");
    if (confirme && callbackConfirmation) {
        callbackConfirmation();
    }
    callbackConfirmation = null;
}