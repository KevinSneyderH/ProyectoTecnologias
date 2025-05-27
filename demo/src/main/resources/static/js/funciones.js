function filtrar() {
  // Eliminar el mensaje de "No hay resultados" si existe
  let mensaje = document.getElementById("sin-resultados");
  if (mensaje) mensaje.remove();

  // Mostrar todas las filas antes de filtrar
  document.querySelectorAll("#tbody tr").forEach(fila => fila.style.display = "");

  const fechaOrden = document.getElementById("fecha").value;
  const tipo = document.getElementById("tipo").value;
  const producto = document.getElementById("producto").value;
  const usuario = document.getElementById("usuario").value;

  // Solo filas de datos, sin el mensaje
  let filas = Array.from(document.querySelectorAll("#tbody tr")).filter(fila => fila.id !== "sin-resultados");
  let filtradas = Array.from(filas);

  // Filtrar por tipo
  if (tipo) {
    filtradas = filtradas.filter(fila => fila.cells[1].textContent.trim() === tipo);
  }

  // Filtrar por producto
  if (producto) {
    filtradas = filtradas.filter(fila => String(fila.cells[4].dataset.producto) === String(producto));
  }

  // Filtrar por usuario
  if (usuario) {
    filtradas = filtradas.filter(fila => String(fila.cells[5].dataset.usuario) === String(usuario));
  }

  // ORDENAR por fecha si se selecciona
  if (fechaOrden === "desc" || fechaOrden === "asc") {
    filtradas.sort((a, b) => {
      // Convierte "2025-05-26 20:55:05.0" a "2025-05-26T20:55:05"
      function parseFecha(fechaStr) {
        if (!fechaStr) return new Date("1900-01-01");
        // Quita el .0 final si existe
        fechaStr = fechaStr.replace(".0", "");
        // Reemplaza espacio por T
        fechaStr = fechaStr.replace(" ", "T");
        return new Date(fechaStr);
      }
      const fechaA = parseFecha(a.cells[0].dataset.fecha);
      const fechaB = parseFecha(b.cells[0].dataset.fecha);
      return fechaOrden === "desc" ? fechaB - fechaA : fechaA - fechaB;
    });
  }

  // Ocultar todas y mostrar solo las filtradas
  filas.forEach(fila => fila.style.display = "none");
  filtradas.forEach(fila => fila.style.display = "");

  // Si no hay resultados, mostrar mensaje
  if (filtradas.length === 0) {
    const tr = document.createElement("tr");
    tr.id = "sin-resultados";
    const td = document.createElement("td");
    td.colSpan = 6;
    td.textContent = "No hay resultados";
    tr.appendChild(td);
    document.getElementById("tbody").appendChild(tr);
  }
}

function limpiarFiltros() {
  document.getElementById("fecha").value = "";
  document.getElementById("tipo").value = "";
  document.getElementById("producto").value = "";
  document.getElementById("usuario").value = "";

  // Mostrar todas las filas
  document.querySelectorAll("#tbody tr").forEach(fila => fila.style.display = "");

  // Eliminar el mensaje de "No hay resultados" si existe
  let mensaje = document.getElementById("sin-resultados");
  if (mensaje) {
    mensaje.remove();
  }
}

document.querySelector('.toggle-sidebar-btn').onclick = function () {
  document.getElementById('sidebar').classList.toggle('hidden');
};

// Ocultar sidebar al hacer clic fuera de él
document.addEventListener('click', function (event) {
  const sidebar = document.getElementById('sidebar');
  const toggleBtn = document.querySelector('.toggle-sidebar-btn');
  // Si el sidebar está visible y el clic fue fuera del aside y del botón
  if (sidebar && !sidebar.classList.contains('hidden')) {
    if (!sidebar.contains(event.target) && !toggleBtn.contains(event.target)) {
      sidebar.classList.add('hidden');
    }
  }
});

function showCustomAlert(msg, type = "success") {
  document.getElementById('customAlertMsg').textContent = msg;
  const img = document.getElementById('customAlertImg');
  if (type === "success") {
    img.src = "/img/correcto.webp";
  } else {
    img.src = "/img/incorrecto.webp";
  }
  document.getElementById('customAlert').classList.add('show');
}
function closeCustomAlert() {
  document.getElementById('customAlert').classList.remove('show');
}

