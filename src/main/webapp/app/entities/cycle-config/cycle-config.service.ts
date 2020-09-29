import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICycleConfig } from 'app/shared/model/cycle-config.model';

type EntityResponseType = HttpResponse<ICycleConfig>;
type EntityArrayResponseType = HttpResponse<ICycleConfig[]>;

@Injectable({ providedIn: 'root' })
export class CycleConfigService {
  public resourceUrl = SERVER_API_URL + 'api/cycle-configs';

  constructor(protected http: HttpClient) {}

  create(cycleConfig: ICycleConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cycleConfig);
    return this.http
      .post<ICycleConfig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(cycleConfig: ICycleConfig): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cycleConfig);
    return this.http
      .put<ICycleConfig>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ICycleConfig>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ICycleConfig[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(cycleConfig: ICycleConfig): ICycleConfig {
    const copy: ICycleConfig = Object.assign({}, cycleConfig, {
      effectiveDate:
        cycleConfig.effectiveDate && cycleConfig.effectiveDate.isValid() ? cycleConfig.effectiveDate.format(DATE_FORMAT) : undefined,
      assignedDate:
        cycleConfig.assignedDate && cycleConfig.assignedDate.isValid() ? cycleConfig.assignedDate.format(DATE_FORMAT) : undefined,
      assignedTime:
        cycleConfig.assignedTime && cycleConfig.assignedTime.isValid() ? cycleConfig.assignedTime.format(DATE_FORMAT) : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.effectiveDate = res.body.effectiveDate ? moment(res.body.effectiveDate) : undefined;
      res.body.assignedDate = res.body.assignedDate ? moment(res.body.assignedDate) : undefined;
      res.body.assignedTime = res.body.assignedTime ? moment(res.body.assignedTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((cycleConfig: ICycleConfig) => {
        cycleConfig.effectiveDate = cycleConfig.effectiveDate ? moment(cycleConfig.effectiveDate) : undefined;
        cycleConfig.assignedDate = cycleConfig.assignedDate ? moment(cycleConfig.assignedDate) : undefined;
        cycleConfig.assignedTime = cycleConfig.assignedTime ? moment(cycleConfig.assignedTime) : undefined;
      });
    }
    return res;
  }
}
