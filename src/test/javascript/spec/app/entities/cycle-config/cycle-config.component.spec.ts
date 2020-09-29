import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JDemoTestModule } from '../../../test.module';
import { CycleConfigComponent } from 'app/entities/cycle-config/cycle-config.component';
import { CycleConfigService } from 'app/entities/cycle-config/cycle-config.service';
import { CycleConfig } from 'app/shared/model/cycle-config.model';

describe('Component Tests', () => {
  describe('CycleConfig Management Component', () => {
    let comp: CycleConfigComponent;
    let fixture: ComponentFixture<CycleConfigComponent>;
    let service: CycleConfigService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [JDemoTestModule],
        declarations: [CycleConfigComponent],
      })
        .overrideTemplate(CycleConfigComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(CycleConfigComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(CycleConfigService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new CycleConfig(123)],
            headers,
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.cycleConfigs && comp.cycleConfigs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
