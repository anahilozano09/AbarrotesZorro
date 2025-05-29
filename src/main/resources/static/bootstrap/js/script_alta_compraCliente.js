document.addEventListener("DOMContentLoaded", function () {
    const tipoProductoSelect = document.getElementById("tipoProducto");
    const productoSelect = document.getElementById("producto");
    const carritoBody = document.getElementById("carritoBody");
    const totalCarrito = document.getElementById("totalCarrito");
    const cantidadInput = document.getElementById("cantidad");
    const btnAgregarCarrito = document.getElementById("btnAgregarCarrito");
    const btnFinalizarCompra = document.getElementById("btnFinalizarCompra");
    const inputsCarrito = document.getElementById("inputsCarrito");
    const buscarClienteBtn = document.getElementById("buscarClienteBtn");
    const debugProductos = document.getElementById("debugProductos");
    const RUTA_IMAGENES = /*[[@{/external-images/}]]*/ "/external-images/";

    let total = 0;
    let carrito = [];

    if (document.querySelector("input[name='clienteId']")) {
        tipoProductoSelect.disabled = false;
        btnFinalizarCompra.disabled = false;
    }

    buscarClienteBtn.addEventListener("click", function(e) {
        const valor = document.getElementById("valorBusqueda").value.trim();
        if (!valor) {
            e.preventDefault();
            alert("Por favor, ingresa un dato de cliente");
        }
    });

    tipoProductoSelect.addEventListener("change", function () {
        const tipoProductoId = this.value;
        productoSelect.innerHTML = '<option value="">-- Selecciona un producto --</option>';
        productoSelect.disabled = true;
        cantidadInput.disabled = true;
        btnAgregarCarrito.disabled = true;
        if (!tipoProductoId) return;

        fetch(`/cajero/compra-cliente/productos-por-tipo/${tipoProductoId}`)
            .then(r => r.json())
            .then(data => {
                data.forEach(prod => {
                    const option = document.createElement("option");
                    option.value = prod.id;
                    option.textContent = `${prod.nombre} - $${prod.precio.toFixed(2)}`;
                    option.dataset.nombre = prod.nombre;
                    option.dataset.precio = prod.precio;
                    option.dataset.imagen = `${RUTA_IMAGENES}${prod.imagen}`;
                    productoSelect.appendChild(option);
                });
                productoSelect.disabled = false;
            });
    });

    productoSelect.addEventListener("change", function () {
        const selected = this.selectedOptions[0];
        if (selected && selected.value) {
            cantidadInput.disabled = false;
            btnAgregarCarrito.disabled = false;
        } else {
            cantidadInput.disabled = true;
            btnAgregarCarrito.disabled = true;
        }
    });

    btnAgregarCarrito.addEventListener("click", function () {
        const selected = productoSelect.selectedOptions[0];
        if (!selected || !selected.value) return;

        const productoId = selected.value;
        const nombre = selected.dataset.nombre;
        const precio = parseFloat(selected.dataset.precio);
        const imagen = selected.dataset.imagen;
        const cantidad = parseInt(cantidadInput.value);

        if (cantidad <= 0 || isNaN(cantidad)) {
            alert("Ingresa una cantidad válida.");
            return;
        }

        const subtotal = precio * cantidad;

        carrito.push({ productoId, cantidad });
        total += subtotal;
        totalCarrito.textContent = total.toFixed(2);

        const index = carrito.length - 1;

        const tr = document.createElement("tr");
        tr.dataset.index = index;
        tr.innerHTML = `
                <td><img src="${imagen}" width="50" alt="${nombre}"/></td>
                <td>${nombre}</td>
                <td>${cantidad}</td>
                <td>$${precio.toFixed(2)}</td>
                <td>$${subtotal.toFixed(2)}</td>
                <td><button type="button" class="btn btn-danger btn-sm">Eliminar</button></td>
            `;
        carritoBody.appendChild(tr);

        const input = document.createElement("input");
        input.type = "hidden";
        input.name = "productos[]";
        input.value = `${productoId}-${cantidad}`;
        input.dataset.index = index;
        inputsCarrito.appendChild(input);

        debugProductos.textContent = Array.from(inputsCarrito.querySelectorAll("input")).map(i => i.value).join("\n");

        productoSelect.selectedIndex = 0;
        cantidadInput.value = 1;
        cantidadInput.disabled = true;
        btnAgregarCarrito.disabled = true;
    });

    carritoBody.addEventListener("click", function(e) {
        if (e.target.classList.contains("btn-danger")) {
            const row = e.target.closest("tr");
            const index = row.dataset.index;
            const subtotal = parseFloat(row.cells[4].textContent.replace('$', ''));
            total -= subtotal;
            totalCarrito.textContent = total.toFixed(2);
            row.remove();

            const input = inputsCarrito.querySelector(`input[data-index="${index}"]`);
            if (input) input.remove();

            debugProductos.textContent = Array.from(inputsCarrito.querySelectorAll("input")).map(i => i.value).join("\n");
        }
    });

    document.getElementById("formCompra").addEventListener("submit", function(e) {
        const productos = Array.from(document.querySelectorAll("input[name='productos[]']")).map(i => i.value);
        console.log("Productos a enviar:", productos);
    });
});
