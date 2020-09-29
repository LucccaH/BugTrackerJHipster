import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAlarmConfig } from 'app/shared/model/alarm-config.model';

type EntityResponseType = HttpResponse<IAlarmConfig>;
type EntityArrayResponseType = HttpResponse<IAlarmConfig[]>;

@Injectable({ providedIn: 'root' })
export class AlarmConfigService {
  public resourceUrl = SERVER_API_URL + 'api/alarm-configs';

  constructor(protected http: HttpClient) {}

  create(alarmConfig: IAlarmConfig): Observable<EntityResponseType> {
    return this.http.post<IAlarmConfig>(this.resourceUrl, alarmConfig, { observe: 'response' });
  }

  update(alarmConfig: IAlarmConfig): Observable<EntityResponseType> {
    return this.http.put<IAlarmConfig>(this.resourceUrl, alarmConfig, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAlarmConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAlarmConfig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
