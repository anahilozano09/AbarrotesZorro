function initPedidos() {
    if (typeof jQuery === 'undefined') {
        showJqueryError();
        return;
    }

    setupEventListeners();

    resetFormState();
}

function showJqueryError() {
    $('#proveedorInfo').html(`
        <tr>
            <td colspan="3" class="text-danger">
                Error: jQuery no está disponible. Recarga la página. 
                Si persiste, contacta al administrador.
            </td>
        </tr>
    `);
}

function setupEventListeners() {
    $(document).ready(function() {
        $('#tipoProductoSelect').on('change', function() {
            handleTipoProductoChange();
        });

        $('#productoSelect').on('change', function() {
            handleProductoChange();
        });

        $('#cantidad').on('change', function() {
            handleCantidadChange();
        });

        $('#formFinalizar').on('submit', function(e) {
            return validateForm(e);
        });
    });
}

function handleTipoProductoChange() {
    const tipoId = $('#tipoProductoSelect').val();

    if (tipoId && tipoId !== "0") {
        loadProductos(tipoId);
    } else {
        resetProductos();
    }
}

function handleProductoChange() {
    const productoId = $('#productoSelect').val();

    if (productoId && productoId !== "0") {
        loadProveedor(productoId);
        $('#inputProducto').val(productoId);
    } else {
        resetProveedorInfo();
    }
}

function handleCantidadChange() {
    const cantidad = $('#cantidad').val();

    if(cantidad > 0) {
        $('#inputCantidad').val(cantidad);
    } else {
        $('#cantidad').val(1);
        $('#inputCantidad').val(1);
    }
}

function validateForm(e) {
    if(!$('#inputProducto').val() || $('#inputProducto').val() === "0") {
        e.preventDefault();
        alert('Por favor seleccione un producto');
        return false;
    }

    if(!$('#inputCantidad').val() || $('#inputCantidad').val() < 1) {
        e.preventDefault();
        alert('La cantidad debe ser al menos 1');
        return false;
    }

    return true;
}

function loadProductos(tipoId) {
    $.ajax({
        url: '/admin/pedido/productos-por-tipo',
        method: 'GET',
        data: { idTipoProducto: tipoId },
        dataType: 'json',
        beforeSend: function() {
            $('#productoSelect').prop('disabled', true);
            $('#productoSelect').html('<option value="0">Cargando productos...</option>');
        },
        success: function(response) {
            updateProductSelect(response);
        },
        error: function() {
            alert("Error al cargar productos. Intenta nuevamente.");
            resetProductos();
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
    resetProveedorInfo();
}

function resetProveedorInfo() {
    $('#proveedorInfo').html('<tr><td colspan="3">Seleccione un producto</td></tr>');
}

function loadProveedor(productoId) {
    $.ajax({
        url: '/admin/pedido/provedoor-por-producto',
        method: 'GET',
        data: { idProducto: productoId },
        beforeSend: function() {
            $('#proveedorInfo').html('<tr><td colspan="3">Cargando proveedor...</td></tr>');
        },
        success: function(proveedor) {
            updateProveedorInfo(proveedor);
        },
        error: function() {
            $('#proveedorInfo').html('<tr><td colspan="3">Error al cargar proveedor</td></tr>');
        }
    });
}

function updateProveedorInfo(proveedor) {
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
}

function resetFormState() {
    $('#productoSelect').prop('disabled', true);
    $('#inputCantidad').val(1);
    resetProveedorInfo();
}

$(document).ready(function() {
    initPedidos();
});