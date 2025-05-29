// pedidos.js - Gestión de pedidos a proveedores

/**
 * Inicializa la funcionalidad de pedidos
 */
function initPedidos() {
    // Verificar si jQuery está disponible
    if (typeof jQuery === 'undefined') {
        showJqueryError();
        return;
    }

    // Configurar eventos
    setupEventListeners();

    // Estado inicial
    resetFormState();
}

/**
 * Muestra error cuando jQuery no está disponible
 */
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

/**
 * Configura todos los event listeners
 */
function setupEventListeners() {
    $(document).ready(function() {
        // Cambio de tipo de producto
        $('#tipoProductoSelect').on('change', function() {
            handleTipoProductoChange();
        });

        // Cambio de producto
        $('#productoSelect').on('change', function() {
            handleProductoChange();
        });

        // Cambio de cantidad
        $('#cantidad').on('change', function() {
            handleCantidadChange();
        });

        // Envío de formulario
        $('#formFinalizar').on('submit', function(e) {
            return validateForm(e);
        });
    });
}

/**
 * Maneja el cambio en el selector de tipo de producto
 */
function handleTipoProductoChange() {
    const tipoId = $('#tipoProductoSelect').val();

    if (tipoId && tipoId !== "0") {
        loadProductos(tipoId);
    } else {
        resetProductos();
    }
}

/**
 * Maneja el cambio en el selector de productos
 */
function handleProductoChange() {
    const productoId = $('#productoSelect').val();

    if (productoId && productoId !== "0") {
        loadProveedor(productoId);
        $('#inputProducto').val(productoId);
    } else {
        resetProveedorInfo();
    }
}

/**
 * Maneja el cambio en la cantidad
 */
function handleCantidadChange() {
    const cantidad = $('#cantidad').val();

    if(cantidad > 0) {
        $('#inputCantidad').val(cantidad);
    } else {
        $('#cantidad').val(1);
        $('#inputCantidad').val(1);
    }
}

/**
 * Valida el formulario antes de enviarlo
 */
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

/**
 * Carga productos según el tipo seleccionado
 */
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

/**
 * Actualiza el selector de productos
 */
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

/**
 * Resetea el selector de productos
 */
function resetProductos() {
    $('#productoSelect').empty()
        .append('<option value="0">Seleccione un producto</option>')
        .prop('disabled', true);
    resetProveedorInfo();
}

/**
 * Resetea la información del proveedor
 */
function resetProveedorInfo() {
    $('#proveedorInfo').html('<tr><td colspan="3">Seleccione un producto</td></tr>');
}

/**
 * Carga la información del proveedor
 */
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

/**
 * Actualiza la información del proveedor
 */
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

/**
 * Establece el estado inicial del formulario
 */
function resetFormState() {
    $('#productoSelect').prop('disabled', true);
    $('#inputCantidad').val(1);
    resetProveedorInfo();
}

// Iniciar la aplicación cuando el documento esté listo
$(document).ready(function() {
    initPedidos();
});