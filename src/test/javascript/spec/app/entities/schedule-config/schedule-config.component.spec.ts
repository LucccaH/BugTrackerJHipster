import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JDemoTestModule } from '../../../test.module';
import { ScheduleConfigComponent } from 'app/entities/schedule-config/schedule-config.component';
import { ScheduleConfigService } from 'app/entities/schedule-config/schedule-config.service';
import { ScheduleConfig } from 'app/shared/model/schedule-config.model';

describe('Component Tests', () => {
  describe('ScheduleConfig Management Component', () => {
    let comp: ScheduleConfigComponent;
    let fixture: ComponentFixture<ScheduleConfigComponent>;
    let service: ScheduleConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JDemoTestModule],
        declarations: [ScheduleConfigComponent],
      })
        .overrideTemplate(ScheduleConfigComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ScheduleConfigComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ScheduleConfigService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ScheduleConfig(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.scheduleConfigs && comp.scheduleConfigs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
