$(document).ready(function () {
    const mensajeExito = $('.alert-success').text().trim();
    if (mensajeExito !== "") {
        $('#cantidad').val(1);
        $('#inputCantidad').val(1);
        $('#tipoProductoSelect').val("0");
        $('#productoSelect').val("0").prop('disabled', true);
        $('#inputProducto').val('');
        $('#proveedorInfo').html('<tr><td colspan="3">Seleccione un producto</td></tr>');
    }

    initPedidos();
});

function initPedidos() {
    if (window.jQuery) {
        setupPedidos();
    } else {
        document.getElementById('proveedorInfo').innerHTML = `
                <tr>
                    <td colspan="3" class="text-danger">
                        Error: Recarga la página. Si persiste, contacta al administrador.
                    </td>
                </tr>`;
    }
}

function setupPedidos() {
    $('#productoSelect').prop('disabled', true);
    $('#inputCantidad').val(1);

    $('#tipoProductoSelect').change(function() {
        const tipoId = $(this).val();
        if (tipoId && tipoId !== "0") {
            loadProductos(tipoId);
        } else {
            resetProductos();
        }
    });

    $('#productoSelect').change(function() {
        const productoId = $(this).val();
        if (productoId && productoId !== "0") {
            loadProveedor(productoId);
            $('#inputProducto').val(productoId);
        }
    });

    $('#cantidad').on('input change', function() {
        const cantidad = $(this).val();
        $('#inputCantidad').val(cantidad);
    });

    $('#formFinalizar').submit(function(e) {
        if (!$('#inputProducto').val() || $('#inputProducto').val() === "0") {
            e.preventDefault();
            alert('Por favor seleccione un producto');
            return false;
        }

        if (!$('#inputCantidad').val() || $('#inputCantidad').val() < 1) {
            e.preventDefault();
            alert('La cantidad debe ser al menos 1');
            return false;
        }

        return true;
    });
}

function loadProductos(tipoId) {
    $.ajax({
        url: '/admin/pedido/productos-por-tipo',
        method: 'GET',
        data: { idTipoProducto: tipoId },
        dataType: 'json',
        success: function(response) {
            updateProductSelect(response);
        },
        error: function() {
            alert("Error al cargar productos. Intenta nuevamente.");
        }
    });
}

function updateProductSelect(productos) {
    const $select = $('#productoSelect');
    $select.empty().append('<option value="0">Seleccione un producto</option>');

    if (productos && productos.length > 0) {
        productos.forEach(function(producto) {
            $select.append(`<option value="${producto.id}">${producto.nombre}</option>`);
        });
        $select.prop('disabled', false);
    } else {
        $select.append('<option value="0">No hay productos disponibles</option>');
    }
}

function resetProductos() {
    $('#productoSelect').empty()
        .append('<option value="0">Seleccione un producto</option>')
        .prop('disabled', true);
    $('#proveedorInfo').html('<tr><td colspan="3">Seleccione un producto</td></tr>');
}

function loadProveedor(productoId) {
    $.get('/admin/pedido/provedoor-por-producto', { idProducto: productoId }, function(proveedor) {
        if (proveedor) {
            $('#proveedorInfo').html(`
                    <tr>
                        <td>${proveedor.nombre}</td>
                        <td>${proveedor.email}</td>
                        <td>${proveedor.telefono}</td>
                    </tr>
                `);
        } else {
            $('#proveedorInfo').html('<tr><td colspan="3">Proveedor no disponible</td></tr>');
        }
    }).fail(function() {
        $('#proveedorInfo').html('<tr><td colspan="3">Error al cargar proveedor</td></tr>');
    });
}