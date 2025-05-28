document.addEventListener("DOMContentLoaded", function () {
    const tipoProductoSelect = document.getElementById("tipoProducto");
    const productoSelect = document.getElementById("producto");
    const carritoBody = document.getElementById("carritoBody");
    const totalCarrito = document.getElementById("totalCarrito");
    const cantidadInput = document.getElementById("cantidad");
    const btnAgregarCarrito = document.getElementById("btnAgregarCarrito");
    const btnFinalizarCompra = document.getElementById("btnFinalizarCompra");
    const inputsCarrito = document.getElementById("inputsCarrito");

    let carrito = [];
    let total = 0;

    document.getElementById("buscarClienteBtn").addEventListener("click", () => {
        const valor = document.getElementById("valorBusqueda").value.trim();
        if (!valor) return alert("Ingresa un número de cuenta, email o teléfono");

        fetch(`/alta-compraCliente/api/clientes/buscar?valor=${encodeURIComponent(valor)}`)
            .then(resp => {
                if (!resp.ok) throw new Error();
                return resp.json();
            })
            .then(data => {
                document.getElementById("nombreCliente").textContent = data.nombre;
                document.getElementById("clienteId").value = data.id;
                document.getElementById("nombreClienteContainer").style.display = "block";

                tipoProductoSelect.disabled = false;
                productoSelect.innerHTML = '<option value="">-- Selecciona un producto --</option>';
            })
            .catch(() => {
                alert("Cliente no encontrado.");
                document.getElementById("clienteId").value = "";
                document.getElementById("nombreClienteContainer").style.display = "none";
                tipoProductoSelect.disabled = true;
                productoSelect.disabled = true;
                cantidadInput.disabled = true;
                btnAgregarCarrito.disabled = true;
                btnFinalizarCompra.disabled = true;
                productoSelect.innerHTML = '<option value="">-- Selecciona un producto --</option>';
            });
    });

    tipoProductoSelect.addEventListener("change", function () {
        const tipoProductoId = this.value;
        productoSelect.innerHTML = '<option value="">-- Selecciona un producto --</option>';
        if (tipoProductoId) {
            fetch(`/api/productos?tipoProductoId=${tipoProductoId}`)
                .then(resp => resp.json())
                .then(data => {
                    data.forEach(prod => {
                        const option = document.createElement("option");
                        option.value = prod.id;
                        option.textContent = `${prod.nombre} - $${prod.precio.toFixed(2)}`;
                        option.dataset.precio = prod.precio;
                        option.dataset.nombre = prod.nombre;
                        option.dataset.imagen = prod.imagen;
                        productoSelect.appendChild(option);
                    });
                    productoSelect.disabled = false;
                });
        } else {
            productoSelect.disabled = true;
        }
    });

    productoSelect.addEventListener("change", () => {
        if (productoSelect.value) {
            cantidadInput.disabled = false;
            btnAgregarCarrito.disabled = false;
        } else {
            cantidadInput.disabled = true;
            btnAgregarCarrito.disabled = true;
        }
    });

    btnAgregarCarrito.addEventListener("click", () => {
        const selectedOption = productoSelect.options[productoSelect.selectedIndex];
        const id = productoSelect.value;
        const nombre = selectedOption.dataset.nombre;
        const precio = parseFloat(selectedOption.dataset.precio);
        const imagen = selectedOption.dataset.imagen;
        const cantidad = parseInt(cantidadInput.value);

        if (carrito.find(p => p.id === id)) {
            alert("Este producto ya fue agregado al carrito.");
            return;
        }

        const subtotal = cantidad * precio;
        carrito.push({ id, nombre, cantidad, precio, imagen, subtotal });
        total += subtotal;
        totalCarrito.textContent = total.toFixed(2);

        const tr = document.createElement("tr");
        tr.innerHTML = `
            <td><img src="/img/${imagen}" style="width: 60px"></td>
            <td>${nombre}</td>
            <td>${cantidad}</td>
            <td>$${precio.toFixed(2)}</td>
            <td>$${subtotal.toFixed(2)}</td>
            <td><button type="button" class="btn btn-danger btn-sm btnEliminar">Eliminar</button></td>
        `;
        carritoBody.appendChild(tr);

        const inputProd = document.createElement("input");
        inputProd.type = "hidden";
        inputProd.name = "productos";
        inputProd.value = `${id}-${cantidad}`;
        inputsCarrito.appendChild(inputProd);

        tr.querySelector(".btnEliminar").addEventListener("click", () => {
            carrito = carrito.filter(p => p.id !== id);
            total -= subtotal;
            totalCarrito.textContent = total.toFixed(2);
            tr.remove();
            inputsCarrito.removeChild(inputProd);
        });

        productoSelect.selectedIndex = 0;
        cantidadInput.value = 1;
        cantidadInput.disabled = true;
        btnAgregarCarrito.disabled = true;
        productoSelect.disabled = true;
    });
});
