import http from "k6/http";
import { check, sleep } from "k6";
import { buildHeaders, buildPayload } from "./common-request.js";
import { Trend, Rate } from "k6/metrics";

// ----------------------
// MÉTRICAS PERSONALIZADAS
// ----------------------
export let latency = new Trend("latency_ms");
export let success = new Rate("success_rate");

// ----------------------
// CONFIGURACIÓN DEL ESCENARIO
// ----------------------
// E3: Latencia 3000 ms + Circuit Breaker habilitado (config por defecto)
// Perfil de carga estándar: warm-up → sustain → cool-down
export let options = {
  stages: [
    { duration: "10s", target: 50 },   // warm-up
    { duration: "120s", target: 150 }, // carga sostenida (ideal para ver CB abrir)
    { duration: "10s", target: 0 },    // cool-down
  ],

  thresholds: {
    "success_rate": ["rate>0.80"], // con latencia alta, CB abrirá → respuestas fallback
    "http_req_duration": ["p(95)<8000"], // p95 en open state debe mejorar
  },
};

export default function () {
  const url = 'http://localhost:9050/api/bills/query';
  const payload = buildPayload(__VU, __ITER);
  const headers = buildHeaders();
  const res = http.post(url, payload, { headers });


    // Registrar métricas
  latency.add(res.timings.duration);
  success.add(res.status >= 200 && res.status < 400);


  // Validaciones del CB activo
  check(res, {
    "CB activo responde correctamente": (r) =>
      r.status >= 200 && r.status < 400,
  });

  //simula tiempo entre peticiones
  sleep(0.01);
}

//comandos para ejecutar script
//k6 run --out experimental-prometheus-rw=http://localhost:9090/api/v1/write C:\Users\Veronica\Desktop\POSGRADO\SegundoSemestre\TrabajoDeTitulacion\code\circuit-breaker-system\performance-test\E3_latency_cb.js