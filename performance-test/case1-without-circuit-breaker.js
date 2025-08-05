import http from "k6/http";
import { check, sleep } from "k6";

export const options = {
    vus: 50,
    duration: '1200s'
}
//Verificar que el Circuit Breaker no interfiere cuando el servicio externo funciona correctamente
export default function () {
    const url = 'http://localhost:9050/api/payment/getBill';
    const headers = {
        'x-cm-client-request-id': '123',
        'x-cm-client-user-agent': '123',
        'Content-Type': 'application/json'
    };
    const body = {
        "idClient": "CLI01",
        "idService": "ELECTRICIDAD01",
        "referenceNumber": "123456789"
    };
    const payload = JSON.stringify(body);
    const res = http.post(url, payload, { headers });
    check(res, { 'status is 200': (r) => r.status === 200 });
    sleep(1);
}