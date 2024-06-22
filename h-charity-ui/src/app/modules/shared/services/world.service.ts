import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { countryApiKey, registeredEmail } from '../utilities/config.constants';
import { map, mergeMap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class WorldService {
  constructor(private readonly http: HttpClient) {}
  apiUrl = 'https://api.countrystatecity.in/v1';
  headers = new HttpHeaders().set('X-CSCAPI-KEY', countryApiKey);

  getCountries() {
    return this.http.get(`${this.apiUrl}/countries`, { headers: this.headers });
  }

  getStates(ciso: string) {
    return this.http.get(`${this.apiUrl}/countries/${ciso}/states`, {
      headers: this.headers,
    });
  }

  getCities(ciso: string, siso: string) {
    return this.http.get(
      `${this.apiUrl}/countries/${ciso}/states/${siso}/cities`,
      {
        headers: this.headers,
      }
    );
  }
}

// Country API - https://api.countrystatecity.in/play
// skip Interceptor: https://stackoverflow.com/questions/55522320/angular-interceptor-exclude-specific-urls
// auth gaurd example: https://stackoverflow.com/questions/49802163/authorization-bearer-token-angular-5
