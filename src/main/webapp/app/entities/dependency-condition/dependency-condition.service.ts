import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IDependencyCondition } from 'app/shared/model/dependency-condition.model';

type EntityResponseType = HttpResponse<IDependencyCondition>;
type EntityArrayResponseType = HttpResponse<IDependencyCondition[]>;

@Injectable({ providedIn: 'root' })
export class DependencyConditionService {
  public resourceUrl = SERVER_API_URL + 'api/dependency-conditions';

  constructor(protected http: HttpClient) {}

  create(dependencyCondition: IDependencyCondition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dependencyCondition);
    return this.http
      .post<IDependencyCondition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(dependencyCondition: IDependencyCondition): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(dependencyCondition);
    return this.http
      .put<IDependencyCondition>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IDependencyCondition>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IDependencyCondition[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(dependencyCondition: IDependencyCondition): IDependencyCondition {
    const copy: IDependencyCondition = Object.assign({}, dependencyCondition, {
      assignedTime:
        dependencyCondition.assignedTime && dependencyCondition.assignedTime.isValid()
          ? dependencyCondition.assignedTime.format(DATE_FORMAT)
          : undefined,
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.assignedTime = res.body.assignedTime ? moment(res.body.assignedTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((dependencyCondition: IDependencyCondition) => {
        dependencyCondition.assignedTime = dependencyCondition.assignedTime ? moment(dependencyCondition.assignedTime) : undefined;
      });
    }
    return res;
  }
}
