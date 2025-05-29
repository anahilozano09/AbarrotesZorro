function generarCuenta() {
    const numCuenta = Math.floor(100000000000 + Math.random() * 900000000000);
    document.getElementById('numCuenta').value = numCuenta;
}
function copiarCuenta() {
    const cuenta = document.getElementById("cuentaGenerada").innerText;
    navigator.clipboard.writeText(cuenta).then(() => {
        alert("Número de cuenta copiado al portapapeles.");
    }).catch(err => {
        console.error("Error al copiar:", err);
    });
}