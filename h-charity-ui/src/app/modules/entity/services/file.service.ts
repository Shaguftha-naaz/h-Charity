import { HttpClient, HttpEvent, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class FileService {
  rootURL = 'http://localhost:8080/api/v1/files';

  constructor(private http: HttpClient) {}

  upload(file: File, entityId: number): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    formData.append('file', file);

    const req = new HttpRequest(
      'POST',
      `${this.rootURL}/upload/${entityId}`,
      formData,
      {
        responseType: 'json',
      }
    );

    return this.http.request(req);
  }

  getFiles(): Observable<any> {
    return this.http.get(`${this.rootURL}/files`);
  }

  uploadQRCode(entityId: number, file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    formData.append('file', file);

    const req = new HttpRequest(
      'POST',
      `${this.rootURL}/uploadQRCode/${entityId}`,
      formData,
      {
        responseType: 'json',
      }
    );

    return this.http.request(req);
  }

  onUploadCoverPhoto(entityId: number, file: File): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();
    formData.append('file', file);

    const req = new HttpRequest(
      'POST',
      `${this.rootURL}/onUploadCoverPhoto/${entityId}`,
      formData,
      {
        responseType: 'json',
      }
    );

    return this.http.request(req);
  }
}
