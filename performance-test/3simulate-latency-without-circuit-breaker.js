import http from "k6/http";
import { check, sleep } from "k6";
import { buildHeaders, buildPayload } from "./common-request.js";

export const options = {
    stages: [
    { duration: '1m', target: 100 },//sube a n usuarios   
    { duration: '5m', target: 100 },//carga estable 
    { duration: '1m', target: 0 }//reducción gradual
  ],
  thresholds:{
    http_req_duration: ['p(95)<3000'],//el 95% de las respuestas <500ms
    http_req_failed: ['rate<0.05']//menos del 5% de errores
  }
};

export default function () {
  const url = 'http://localhost:9050/api/bills/query';
  const payload = buildPayload(__VU, __ITER);
  const headers = buildHeaders();
  const res = http.post(url, payload, { headers });

  //validaciones
  check(res, {
    'status is 200': (r) => r.status === 200,
    'time<3000ms':(r)=>r.timings.duration <3000
  });

  //simula tiempo entre peticiones
  sleep(1);
}

//comandos para ejecutar script
//k6 run --out experimental-prometheus-rw=http://localhost:9090/api/v1/write C:\Users\Veronica\Desktop\POSGRADO\SegundoSemestre\TrabajoDeTitulacion\code\circuit-breaker-system\performance-test\3simulate-latency-without-circuit-breaker.js