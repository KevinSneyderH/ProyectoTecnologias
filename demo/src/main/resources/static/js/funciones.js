function filtrar() {
  // Eliminar el mensaje de "No hay resultados" si existe
  let mensaje = document.getElementById("sin-resultados");
  if (mensaje) {
    mensaje.remove();
  }

  // Mostrar todas las filas antes de filtrar
  document.querySelectorAll("#tbody tr").forEach(fila => fila.style.display = "");

  const fecha = document.getElementById("fecha").value;
  const tipo = document.getElementById("tipo").value;
  const producto = document.getElementById("producto").value;
  const usuario = document.getElementById("usuario").value;

  // Solo filas de datos, sin el mensaje
  const filas = Array.from(document.querySelectorAll("#tbody tr")).filter(fila => fila.id !== "sin-resultados");
  let filtradas = Array.from(filas);

  // Filtrar por tipo
  if (tipo) {
    filtradas = filtradas.filter(fila => fila.cells[1].textContent.trim() === tipo);
  }

  // Filtrar por producto (convertir ambos a string)
  if (producto) {
    filtradas = filtradas.filter(fila => String(fila.cells[4].dataset.producto) === String(producto));
  }

  if (usuario) {
    filtradas = filtradas.filter(fila => String(fila.cells[5].dataset.usuario) === String(usuario));
  }


  // Ordenar por fecha
  if (fecha) {
    filtradas.sort((a, b) => {
      const aFecha = parseFecha(a.cells[0].dataset.fecha);
      const bFecha = parseFecha(b.cells[0].dataset.fecha);
      return fecha === "asc" ? aFecha - bFecha : bFecha - aFecha;
    });


  }

  // Ocultar todas las filas
  filas.forEach(fila => fila.style.display = "none");

  // Mostrar las filtradas
  filtradas.forEach(fila => fila.style.display = "");

  // Mostrar mensaje si no hay resultados
  if (filtradas.length === 0) {
    let mensaje = document.createElement("tr");
    mensaje.id = "sin-resultados";
    mensaje.innerHTML = `<td colspan="7" style="text-align:center;">No hay resultados</td>`;
    document.getElementById("tbody").appendChild(mensaje);
  }
}

// Función para parsear fechas en formato dd/MM/yyyy o yyyy-MM-dd
function parseFecha(fechaStr) {
  if (!fechaStr) return new Date("1900-01-01");
  if (fechaStr.includes("/")) {
    const [d, m, y] = fechaStr.split("/");
    return new Date(`${y}-${m}-${d}`);
  }
  return new Date(fechaStr); // formato ISO (yyyy-MM-dd)
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

