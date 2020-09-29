import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { CycleConfigService } from 'app/entities/cycle-config/cycle-config.service';
import { ICycleConfig, CycleConfig } from 'app/shared/model/cycle-config.model';
import { CycleType } from 'app/shared/model/enumerations/cycle-type.model';

describe('Service Tests', () => {
  describe('CycleConfig Service', () => {
    let injector: TestBed;
    let service: CycleConfigService;
    let httpMock: HttpTestingController;
    let elemDefault: ICycleConfig;
    let expectedResult: ICycleConfig | ICycleConfig[] | boolean | null;
    let currentDate: moment.Moment;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(CycleConfigService);
      httpMock = injector.get(HttpTestingController);
      currentDate = moment();

      elemDefault = new CycleConfig(0, currentDate, currentDate, currentDate, CycleType.YEAR);
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign(
          {
            effectiveDate: currentDate.format(DATE_FORMAT),
            assignedDate: currentDate.format(DATE_FORMAT),
            assignedTime: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        service.find(123).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a CycleConfig', () => {
        const returnedFromService = Object.assign(
          {
            id: 0,
            effectiveDate: currentDate.format(DATE_FORMAT),
            assignedDate: currentDate.format(DATE_FORMAT),
            assignedTime: currentDate.format(DATE_FORMAT),
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            effectiveDate: currentDate,
            assignedDate: currentDate,
            assignedTime: currentDate,
          },
          returnedFromService
        );

        service.create(new CycleConfig()).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a CycleConfig', () => {
        const returnedFromService = Object.assign(
          {
            effectiveDate: currentDate.format(DATE_FORMAT),
            assignedDate: currentDate.format(DATE_FORMAT),
            assignedTime: currentDate.format(DATE_FORMAT),
            cycleEnum: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            effectiveDate: currentDate,
            assignedDate: currentDate,
            assignedTime: currentDate,
          },
          returnedFromService
        );

        service.update(expected).subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of CycleConfig', () => {
        const returnedFromService = Object.assign(
          {
            effectiveDate: currentDate.format(DATE_FORMAT),
            assignedDate: currentDate.format(DATE_FORMAT),
            assignedTime: currentDate.format(DATE_FORMAT),
            cycleEnum: 'BBBBBB',
          },
          elemDefault
        );

        const expected = Object.assign(
          {
            effectiveDate: currentDate,
            assignedDate: currentDate,
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

      it('should delete a CycleConfig', () => {
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
