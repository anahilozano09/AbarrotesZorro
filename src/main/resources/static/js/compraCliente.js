document.addEventListener("DOMContentLoaded", function () {
    const tipoProductoSelect = document.getElementById("tipoProducto");
    const productoSelect = document.getElementById("producto");
    const carritoBody = document.getElementById("carritoBody");
    const totalCarrito = document.getElementById("totalCarrito");
    const cantidadInput = document.getElementById("cantidad");
    const btnAgregarCarrito = document.getElementById("btnAgregarCarrito");
    const btnFinalizarCompra = document.getElementById("btnFinalizarCompra");
    const inputsCarrito = document.getElementById("inputsCarrito");

    let total = 0;
    let carrito = [];

    document.getElementById("buscarClienteBtn").addEventListener("click", function () {
        const valor = document.getElementById("valorBusqueda").value.trim();
        if (!valor) return alert("Por favor, ingresa un dato de cliente");

        fetch(`/alta-compraCliente/api/clientes/buscar?valor=${encodeURIComponent(valor)}`)
            .then(r => r.ok ? r.json() : Promise.reject("No encontrado"))
            .then(data => {
                document.getElementById("nombreCliente").textContent = data.nombre;
                document.getElementById("nombreClienteContainer").style.display = "block";
                tipoProductoSelect.disabled = false;
                productoSelect.disabled = true;
                cantidadInput.disabled = true;
                btnAgregarCarrito.disabled = true;
                btnFinalizarCompra.disabled = false;
                productoSelect.innerHTML = '<option value="">-- Selecciona un producto --</option>';
            })
            .catch(() => alert("Cliente no encontrado."));
    });

    tipoProductoSelect.addEventListener("change", function () {
        const tipoProductoId = this.value;
        productoSelect.innerHTML = '<option value="">-- Selecciona un producto --</option>';
        if (!tipoProductoId) return;

        fetch(`/api/productos?tipoProductoId=${tipoProductoId}`)
            .then(r => r.json())
            .then(data => {
                data.forEach(prod => {
                    const option = document.createElement("option");
                    option.value = prod.id;
                    option.textContent = `${prod.nombre} - $${prod.precio.toFixed(2)}`;
                    option.dataset.nombre = prod.nombre;
                    option.dataset.precio = prod.precio;
                    option.dataset.imagen = prod.imagen;
                    productoSelect.appendChild(option);
                });
                productoSelect.disabled = false;
            });
    });

    productoSelect.addEventListener("change", function () {
        const selected = this.selectedOptions[0];
        cantidadInput.disabled = !selected;
        btnAgregarCarrito.disabled = !selected;
    });

    btnAgregarCarrito.addEventListener("click", function () {
        const selected = productoSelect.selectedOptions[0];
        const productoId = selected.value;
        const nombre = selected.dataset.nombre;
        const precio = parseFloat(selected.dataset.precio);
        const imagen = selected.dataset.imagen;
        const cantidad = parseInt(cantidadInput.value);
        const subtotal = precio * cantidad;

        carrito.push({ productoId, cantidad });
        total += subtotal;
        totalCarrito.textContent = total.toFixed(2);

        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td><img src="/images/${imagen}" width="50"/></td>
            <td>${nombre}</td>
            <td>${cantidad}</td>
            <td>$${precio.toFixed(2)}</td>
            <td>$${subtotal.toFixed(2)}</td>
            <td><button type="button" class="btn btn-danger btn-sm">Eliminar</button></td>
        `;
        carritoBody.appendChild(tr);

        const input = document.createElement("input");
        input.type = "hidden";
        input.name = "productos";
        input.value = `${productoId}-${cantidad}`;
        inputsCarrito.appendChild(input);
    });
});

