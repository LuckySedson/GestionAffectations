(function () {
    const theme = localStorage.getItem("theme") || "clair";
    if (theme === "sombre") {
        document.documentElement.setAttribute("data-theme", "dark");
    }
})();

function basculerTheme() {
    const actuel = document.documentElement.getAttribute("data-theme");
    if (actuel === "dark") {
        document.documentElement.removeAttribute("data-theme");
        localStorage.setItem("theme", "clair");
    } else {
        document.documentElement.setAttribute("data-theme", "dark");
        localStorage.setItem("theme", "sombre");
    }
}