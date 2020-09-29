import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IScheduleConfig } from 'app/shared/model/schedule-config.model';

type EntityResponseType = HttpResponse<IScheduleConfig>;
type EntityArrayResponseType = HttpResponse<IScheduleConfig[]>;

@Injectable({ providedIn: 'root' })
export class ScheduleConfigService {
  public resourceUrl = SERVER_API_URL + 'api/schedule-configs';

  constructor(protected http: HttpClient) {}

  create(scheduleConfig: IScheduleConfig): Observable<EntityResponseType> {
    return this.http.post<IScheduleConfig>(this.resourceUrl, scheduleConfig, { observe: 'response' });
  }

  update(scheduleConfig: IScheduleConfig): Observable<EntityResponseType> {
    return this.http.put<IScheduleConfig>(this.resourceUrl, scheduleConfig, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IScheduleConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IScheduleConfig[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
