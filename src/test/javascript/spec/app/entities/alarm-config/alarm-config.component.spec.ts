import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JDemoTestModule } from '../../../test.module';
import { AlarmConfigComponent } from 'app/entities/alarm-config/alarm-config.component';
import { AlarmConfigService } from 'app/entities/alarm-config/alarm-config.service';
import { AlarmConfig } from 'app/shared/model/alarm-config.model';

describe('Component Tests', () => {
  describe('AlarmConfig Management Component', () => {
    let comp: AlarmConfigComponent;
    let fixture: ComponentFixture<AlarmConfigComponent>;
    let service: AlarmConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JDemoTestModule],
        declarations: [AlarmConfigComponent],
      })
        .overrideTemplate(AlarmConfigComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AlarmConfigComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(AlarmConfigService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new AlarmConfig(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.alarmConfigs && comp.alarmConfigs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
