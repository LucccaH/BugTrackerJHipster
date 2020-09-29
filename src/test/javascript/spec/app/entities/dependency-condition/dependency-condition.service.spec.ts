import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { DependencyConditionService } from 'app/entities/dependency-condition/dependency-condition.service';
import { IDependencyCondition, DependencyCondition } from 'app/shared/model/dependency-condition.model';
import { DependencyTypeEnum } from 'app/shared/model/enumerations/dependency-type-enum.model';
import { OperatorEnum } from 'app/shared/model/enumerations/operator-enum.model';

describe('Service Tests', () => {
  describe('DependencyCondition Service', () => {
    let injector: TestBed;
    let service: DependencyConditionService;
    let httpMock: HttpTestingController;
    let elemDefault: IDependencyCondition;
    let expectedResult: IDependencyCondition | IDependencyCondition[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(DependencyConditionService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new DependencyCondition(0, DependencyTypeEnum.TASK, 'AAAAAAA', currentDate, OperatorEnum.AND);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            assignedTime: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a DependencyCondition', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            assignedTime: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            assignedTime: currentDate,
          },
          returnedFromService
        );

        service.create(new DependencyCondition()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a DependencyCondition', () => {
        const returnedFromService = Object.assign(
          {
            dependencyType: 'BBBBBB',
            flagFiekdId: 'BBBBBB',
            assignedTime: currentDate.format(DATE_FORMAT),
            operator: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            assignedTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of DependencyCondition', () => {
        const returnedFromService = Object.assign(
          {
            dependencyType: 'BBBBBB',
            flagFiekdId: 'BBBBBB',
            assignedTime: currentDate.format(DATE_FORMAT),
            operator: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            assignedTime: currentDate,
          },
          returnedFromService
        );

        service.query().subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a DependencyCondition', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
