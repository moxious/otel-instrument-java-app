import http from "k6/http";

export const options = {
  discardResponseBodies: true,
  scenarios: {
    contacts: {
      executor: 'ramping-vus',
      startVUs: 0,
      stages: [
        { duration: '10s', target: 10 },
        { duration: '20s', target: 30 },
        { duration: '10s', target: 20 },
      ],
      gracefulRampDown: '0s',
    },
  },
};

export default function () {
  const response = http.get("http://localhost:8080/");
}
