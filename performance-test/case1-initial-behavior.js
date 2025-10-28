import http from "k6/http";
import { check } from "k6";
import { buildHeaders, buildPayload } from "./common-request.js";

export const options = {
   stages: [
    { duration: '2m', target: 100 },// Fase 1: Incremento gradual
    { duration: '5m', target: 500 }, // Fase 2: Carga media sostenida
    { duration: '2m', target: 1000 },// Fase 3: Pico de carga
    { duration: '1m', target: 0 }//Fase 4: Descenso
  ] 
};

export default function () {
  const url = 'http://localhost:9050/api/bills/query';
  const payload = buildPayload(__VU, __ITER);
  const headers = buildHeaders();
  const res = http.post(url, payload, { headers });

  check(res, {
    'status is 200': (r) => r.status === 200
  });
}