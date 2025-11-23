import http from "k6/http";
import { check, sleep } from "k6";

import { buildHeaders, buildPayload } from "./common-request.js";

export const options = {
  vus: 100,                 // volumen normal equivalente a 100 TPS aproximados
  duration: '60s',          // prueba estable
};

export default function () {
  const url = 'http://localhost:9050/api/bills/query';
  const payload = buildPayload(__VU, __ITER);
  const headers = buildHeaders();
  const res = http.post(url, payload, { headers });

  //validaciones
  check(res, {
    'status is 200': (r) => r.status === 200,
    'time<500ms':(r)=>r.timings.duration <500
  });


  //simula tiempo entre peticiones
  sleep(1);
}

//comandos para ejecutar script
//k6 run --out experimental-prometheus-rw=http://localhost:9090/api/v1/write C:\Users\Veronica\Desktop\POSGRADO\SegundoSemestre\TrabajoDeTitulacion\code\circuit-breaker-system\performance-test\baseline-load-test.js