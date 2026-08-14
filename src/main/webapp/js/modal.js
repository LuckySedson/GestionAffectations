let callbackConfirmation = null;

function ouvrirConfirmation(message, callback, texteBouton) {
    document.getElementById("modal-message").textContent = message;
    document.getElementById("modal-btn-confirmer").textContent = texteBouton || "Supprimer";
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

function annulerDerniereSuppression() {
    const chemin = window.location.pathname;
    let base = "";
    if (chemin.includes("/employe")) base = "employe";
    else if (chemin.includes("/lieu")) base = "lieu";
    else if (chemin.includes("/affecter")) base = "affecter";

    if (base) {
        window.location.href = base + "?action=annulerSuppr";
    }
}

function confirmerSiDatePassee(event) {
    const champDate = document.querySelector('input[name="date"]');
    const dateChoisie = new Date(champDate.value);
    const aujourdHui = new Date();
    aujourdHui.setHours(0, 0, 0, 0);

    if (dateChoisie < aujourdHui) {
        event.preventDefault();
        ouvrirConfirmation(
            "La date sélectionnée (" + champDate.value + ") est dans le passé. Confirmer malgré tout ?",
            function() {
                event.target.submit();
            },
            "Confirmer"
        );
        return false;
    }
    return true;
}