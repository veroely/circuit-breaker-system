import http from "k6/http";
import { check, sleep } from "k6";
import {Trend} from "k6/metrics";
import { buildHeaders, buildPayload } from "./common-request.js";

//const responseTime = new Trend('response_time',true);

export const options = {
    stages: [
    { duration: '1m', target: 100 },//sube a n usuarios   
    { duration: '5m', target: 100 },//carga estable 
    { duration: '1m', target: 0 }//reducción gradual
  ],
  thresholds:{
    http_req_duration: ['p(95)<500'],//el 95% de las respuestas <500ms
    http_req_failed: ['rate<0.02']//menos del 2% de errores
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
    'time<500ms':(r)=>r.timings.duration <500
  });

  //guardar metricas personalizadas
  //responseTime.add(res.timings.duration);

  //simula tiempo entre peticiones
  sleep(1);
}

//comandos para ejecutar script
//k6 run --out experimental-prometheus-rw=http://localhost:9090/api/v1/write C:\Users\Veronica\Desktop\POSGRADO\SegundoSemestre\TrabajoDeTitulacion\code\circuit-breaker-system\performance-test\baseline-load-test.js