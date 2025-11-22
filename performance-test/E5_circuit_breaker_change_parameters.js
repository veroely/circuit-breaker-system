import http from "k6/http";
import { check, sleep } from "k6";
import { buildHeaders, buildPayload } from "./common-request.js";
import { Trend, Rate } from "k6/metrics";

export let success = new Rate('success_rate');

export let options = {
  stages: [
    { duration: '10s', target: 30 },
    { duration: '120s', target: 150 },
    { duration: '10s', target: 0 }
  ],
  thresholds: {
    'success_rate': ['rate>0.50']
  },
};

export default function () {
  const url = 'http://localhost:9050/api/bills/query';
  const payload = buildPayload(__VU, __ITER);
  const headers = buildHeaders();
  const res = http.post(url, payload, { headers });


    // Registrar métricas
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
//k6 run --out experimental-prometheus-rw=http://localhost:9090/api/v1/write C:\Users\Veronica\Desktop\POSGRADO\SegundoSemestre\TrabajoDeTitulacion\code\circuit-breaker-system\performance-test\E4_errors_5xx_cb.js