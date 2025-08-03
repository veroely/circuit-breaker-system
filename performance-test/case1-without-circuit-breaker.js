import http from "k6/http";
import { check, sleep } from "k6";

export const options = {
    vus: 50,
    duration: '1200s'
}
//Verificar que el Circuit Breaker no interfiere cuando el servicio externo funciona correctamente
export default function () {
    const url = 'http://localhost:5000/api/electricity/P01/bill/123456789012';
    const headers = {
        'x-cm-client-request-id': '123',
        'x-cm-client-user-agent': '123'
    };
    const res = http.get(url, { headers });
    check(res, { 'status is 200': (r) => r.status === 200 });
    sleep(1);
}